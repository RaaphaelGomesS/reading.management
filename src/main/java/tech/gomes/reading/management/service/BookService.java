package tech.gomes.reading.management.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tech.gomes.reading.management.builder.BookBuilder;
import tech.gomes.reading.management.builder.BookResponseDTOBuilder;
import tech.gomes.reading.management.builder.BookTemplateResponseDTOBuilder;
import tech.gomes.reading.management.domain.*;
import tech.gomes.reading.management.dto.book.request.*;
import tech.gomes.reading.management.dto.book.response.BookResponseDTO;
import tech.gomes.reading.management.dto.book.response.BookResponsePageDTO;
import tech.gomes.reading.management.dto.book.response.BookTemplateResponseDTO;
import tech.gomes.reading.management.dto.book.response.FullBookResponseDTO;
import tech.gomes.reading.management.exception.BookException;
import tech.gomes.reading.management.exception.BookTemplateException;
import tech.gomes.reading.management.exception.LibraryException;
import tech.gomes.reading.management.indicator.ReadingStatusIndicator;
import tech.gomes.reading.management.repository.BookRepository;
import tech.gomes.reading.management.repository.BookTemplateRepository;
import tech.gomes.reading.management.utils.BookUtils;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final LibraryService libraryService;

    private final BookTemplateService templateService;

    private final BookTemplateRepository bookTemplateRepository;

    public BookResponsePageDTO getAllBooksByStatusInLibrary(long id, User user, ReadingStatusIndicator status, int page, int pageSize, String direction) throws LibraryException {

        Library library = libraryService.getLibraryById(id, user);

        Sort sort = Sort.by(Sort.Direction.valueOf(direction), "updatedAt");

        Pageable pageable = PageRequest.of(page, pageSize, sort);

        Page<Book> pageBook = bookRepository.findAllByLibraryIdAndStatus(library.getId(), status, pageable);

        return BookResponseDTOBuilder.from(pageBook);
    }

    @Transactional
    public BookResponseDTO createBook(BookCreateRequestDTO requestDTO, User user, MultipartFile file) throws Exception {

        log.info("Livro: {}", requestDTO.book());

        if (requestDTO.template().templateId() != null) {
            verifyBookAlreadyRegister(requestDTO.template().templateId(), user.getId());
        }

        Library library = libraryService.getLibraryById(requestDTO.book().libraryId(), user);

        BookTemplate template = templateService.getOrCreateBookTemplate(requestDTO.template(), file);

        verifyBookAlreadyRegister(template.getId(), user.getId());

        Book newBook = BookBuilder.from(requestDTO.book(), template, library);

        return BookResponseDTOBuilder.from(bookRepository.save(newBook));
    }

    public BookResponseDTO updateBook(BookRequestDTO requestDTO, User user) throws Exception {
        Book book = findBookById(requestDTO.id(), user.getId());

        log.info("Request data check: {}", requestDTO);
        BookUtils.setValuesToBookByStatusAndRequest(book, requestDTO);

        if (requestDTO.libraryId() != null && !book.getLibrary().getId().equals(requestDTO.libraryId())) {
            Library library = libraryService.getLibraryById(requestDTO.libraryId(), user);

            book.setLibrary(library);
        }

        Book updatedBook = bookRepository.save(book);

        return BookResponseDTOBuilder.from(updatedBook);
    }

    public BookResponseDTO updateReadPages(PagesUpdateRequestDTO requestDTO, User user) throws BookException {

        Book book = findBookById(requestDTO.bookId(), user.getId());

        if (requestDTO.pages() > book.getBookTemplate().getPages()) {
            throw new BookException("A quantidade de páginas é inválida.", HttpStatus.BAD_REQUEST);
        }

        ReadingStatusIndicator status = requestDTO.pages() == book.getBookTemplate().getPages() ? ReadingStatusIndicator.READ : ReadingStatusIndicator.READING;

        book.setReadPages(requestDTO.pages());
        book.setStatus(status);

        Book updatedBook = bookRepository.save(book);

        return BookResponseDTOBuilder.from(updatedBook);
    }

    public BookResponseDTO finishBook(FinishBookRequestDTO requestDTO, User user) throws BookException {

        Book book = findBookById(requestDTO.bookId(), user.getId());

        book.setFinishedAt(Instant.now());
        book.setRating(requestDTO.rating());
        book.setStatus(ReadingStatusIndicator.READ);
        book.setReadPages(book.getBookTemplate().getPages());

        Book updatedBook = bookRepository.save(book);

        return BookResponseDTOBuilder.from(updatedBook);
    }

    public FullBookResponseDTO getFullBookById(long id, User user) throws BookException {
        Book book = findBookById(id, user.getId());

        BookResponseDTO bookResponse = BookResponseDTOBuilder.from(book);
        BookTemplateResponseDTO templateResponse = BookTemplateResponseDTOBuilder.from(book.getBookTemplate());

        return FullBookResponseDTO.builder()
                .book(bookResponse)
                .template(templateResponse)
                .build();
    }

    public BookResponseDTO changeBookFromLibrary(ChangeLibRequestDTO requestDTO, User user) throws LibraryException, BookException {

        Library library = libraryService.getLibraryById(requestDTO.libraryId(), user);

        Book book = findBookById(requestDTO.bookId(), user.getId());

        if (book.getLibrary().getId() == requestDTO.libraryId()) {
            return BookResponseDTOBuilder.from(book);
        }

        book.setLibrary(library);

        Book updatedBook = bookRepository.save(book);

        return BookResponseDTOBuilder.from(updatedBook);
    }

    public void deleteBook(long id, User user) throws BookException {
        Book book = findBookById(id, user.getId());

        bookRepository.delete(book);
    }

    public Book findBookById(long id, long userId) throws BookException {
        return bookRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new BookException("Não foi encontrado o livro.", HttpStatus.NOT_FOUND));
    }

    public void createBooksAndIndexInLibrary(ReadingPlan plan, Library library) {

        Set<Long> templatesIdsFromPlan = plan.getBookTemplatesPlan().stream().map(JoinPlanTemplate::getTemplateId).collect(Collectors.toSet());

        Set<BookTemplate> templatesFromPlan = bookTemplateRepository.findAllByIdIn(templatesIdsFromPlan);

        Set<Book> books = templatesFromPlan.stream().map(template -> BookBuilder.fromPlan(template, library)).collect(Collectors.toSet());

        bookRepository.saveAll(books);
    }

    private void verifyBookAlreadyRegister(Long templateId, Long userId) throws BookTemplateException {

        Optional<Book> book = bookRepository.findByBookTemplateIdAndUserId(templateId, userId);

        if (book.isPresent()) {
            String errorMessage = String.format("ALREADY_EXISTS|%d|%s",
                    book.get().getId(),
                    book.get().getLibrary().getName());

            throw new BookTemplateException(errorMessage, HttpStatus.BAD_REQUEST);
        }
    }
}
