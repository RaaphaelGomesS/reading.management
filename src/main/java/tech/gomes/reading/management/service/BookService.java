package tech.gomes.reading.management.service;

import lombok.RequiredArgsConstructor;
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
import tech.gomes.reading.management.domain.Book;
import tech.gomes.reading.management.domain.BookTemplate;
import tech.gomes.reading.management.domain.Library;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.book.request.*;
import tech.gomes.reading.management.dto.book.response.BookResponseDTO;
import tech.gomes.reading.management.dto.book.response.BookResponsePageDTO;
import tech.gomes.reading.management.dto.book.response.BookTemplateResponseDTO;
import tech.gomes.reading.management.dto.book.response.FullBookResponseDTO;
import tech.gomes.reading.management.exception.BookException;
import tech.gomes.reading.management.exception.BookTemplateException;
import tech.gomes.reading.management.indicator.ReadingStatusIndicator;
import tech.gomes.reading.management.repository.BookRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final LibraryService libraryService;

    private final BookTemplateService templateService;

    public BookResponsePageDTO getAllBooksByStatus(long id, User user, ReadingStatusIndicator status, int page, int pageSize, String direction) throws Exception {

        Library library = libraryService.getLibraryById(id, user);

        Sort sort = Sort.by(Sort.Direction.valueOf(direction), "updatedAt");

        Pageable pageable = PageRequest.of(page, pageSize, sort);

        Page<Book> pageBook = bookRepository.findAllByLibraryIdAndStatus(library.getId(), status, pageable);

        return BookResponseDTOBuilder.from(pageBook);
    }

    @Transactional
    public BookResponseDTO createBook(BookCreateRequestDTO requestDTO, User user, MultipartFile file) throws Exception {

        if (requestDTO.template().templateId() != null) {
            verifyBookAlreadyRegister(requestDTO.template(), user);
        }

        Library library = libraryService.getLibraryById(requestDTO.book().libraryId(), user);

        BookTemplate template = templateService.getOrCreateBookTemplate(requestDTO.template(), file);

        Book newBook = BookBuilder.from(requestDTO.book(), template, library);

        return BookResponseDTOBuilder.from(bookRepository.save(newBook));
    }

    public BookResponseDTO updateBookStatus(BookRequestDTO requestDTO, User user) throws Exception {
        Book book = findBookById(requestDTO.id(), user.getId());

        BookBuilder.updateBookFromRequest(book, requestDTO);

        Book updatedBook = bookRepository.save(book);

        return BookResponseDTOBuilder.from(updatedBook);
    }

    public BookResponseDTO updateReadPages(PagesUpdateRequestDTO requestDTO, User user) throws Exception {

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

    public BookResponseDTO finishBook(FinishBookRequestDTO requestDTO, User user) throws Exception {

        Book book = findBookById(requestDTO.bookId(), user.getId());

        book.setRating(requestDTO.rating());
        book.setStatus(ReadingStatusIndicator.READ);
        book.setReadPages(book.getBookTemplate().getPages());

        Book updatedBook = bookRepository.save(book);

        return BookResponseDTOBuilder.from(updatedBook);
    }

    public FullBookResponseDTO getFullBookById(long id, User user) throws Exception {
        Book book = findBookById(id, user.getId());

        BookResponseDTO bookResponse = BookResponseDTOBuilder.from(book);
        BookTemplateResponseDTO templateResponse = BookTemplateResponseDTOBuilder.from(book.getBookTemplate());

        return FullBookResponseDTO.builder()
                .book(bookResponse)
                .template(templateResponse)
                .build();
    }

    public BookResponseDTO changeBookFromLibrary(ChangeLibRequestDTO requestDTO, User user) throws Exception {

        Library library = libraryService.getLibraryById(requestDTO.libraryId(), user);

        Book book = findBookById(requestDTO.bookId(), user.getId());

        book.setLibrary(library);

        Book updatedBook = bookRepository.save(book);

        return BookResponseDTOBuilder.from(updatedBook);
    }

    public void deleteBook(long id, User user) throws Exception {
        Book book = findBookById(id, user.getId());

        bookRepository.delete(book);
    }

    public Book findBookById(long id, long userId) throws Exception {
        return bookRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new BookException("Não foi encontrado o livro.", HttpStatus.NOT_FOUND));
    }

    private void verifyBookAlreadyRegister(BookTemplateRequestDTO requestDTO, User user) throws Exception {

        Optional<Book> book = bookRepository.findByBookTemplateIdAndUserId(requestDTO.templateId(), user.getId());

        if (book.isPresent()) {
            throw new BookTemplateException("Já existe um cadastro deste livro.", HttpStatus.BAD_REQUEST);
        }
    }
}
