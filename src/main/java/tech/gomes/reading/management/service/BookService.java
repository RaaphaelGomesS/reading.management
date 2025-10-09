package tech.gomes.reading.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tech.gomes.reading.management.builder.BookBuilder;
import tech.gomes.reading.management.builder.BookResponseDTOBuilder;
import tech.gomes.reading.management.builder.BookTemplateResponseDTOBuilder;
import tech.gomes.reading.management.domain.Book;
import tech.gomes.reading.management.domain.BookTemplate;
import tech.gomes.reading.management.domain.Library;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.book.request.BookCreateRequestDTO;
import tech.gomes.reading.management.dto.book.request.BookRequestDTO;
import tech.gomes.reading.management.dto.book.request.BookTemplateRequestDTO;
import tech.gomes.reading.management.dto.book.response.BookResponseDTO;
import tech.gomes.reading.management.dto.book.response.BookTemplateResponseDTO;
import tech.gomes.reading.management.dto.book.response.FullBookResponseDTO;
import tech.gomes.reading.management.exception.BookException;
import tech.gomes.reading.management.exception.BookTemplateException;
import tech.gomes.reading.management.repository.BookRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final LibraryService libraryService;

    private final BookTemplateService templateService;

    public BookResponseDTO createBook(BookCreateRequestDTO requestDTO, User user, MultipartFile file) throws Exception {

        verifyBookAlreadyRegister(requestDTO.template(), user);

        Library library = libraryService.getLibraryById(requestDTO.book().libraryId(), user);

        BookTemplate template = templateService.getOrcreateBookTemplate(requestDTO.template(), file);

        Book newBook = BookBuilder.from(requestDTO.book(), template, library);

        return BookResponseDTOBuilder.from(bookRepository.save(newBook));
    }

    public BookResponseDTO updateBookStatus(BookRequestDTO requestDTO, User user) throws Exception {
        Book book = findBookById(requestDTO.id(), user.getId());

        BookBuilder.updateBookFromRequest(book, requestDTO);

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

    private Book findBookById(long id, long userId) throws Exception {
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
