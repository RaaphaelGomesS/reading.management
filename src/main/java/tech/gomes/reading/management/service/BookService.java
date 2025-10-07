package tech.gomes.reading.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tech.gomes.reading.management.builder.BookBuilder;
import tech.gomes.reading.management.builder.BookResponseDTOBuilder;
import tech.gomes.reading.management.domain.Book;
import tech.gomes.reading.management.domain.BookTemplate;
import tech.gomes.reading.management.domain.Library;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.book.BookRequestDTO;
import tech.gomes.reading.management.dto.book.BookResponseDTO;
import tech.gomes.reading.management.exception.BookTemplateException;
import tech.gomes.reading.management.repository.BookRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    private final LibraryService libraryService;

    private final BookTemplateService templateService;

    public BookResponseDTO createBook(BookRequestDTO requestDTO, User user, MultipartFile file) throws Exception {

        verifyBookAlreadyRegister(requestDTO, user);

        Library library = libraryService.getLibraryById(requestDTO.libraryId(), user);

        BookTemplate template = templateService.getOrcreateBookTemplate(requestDTO.template(), file);

        Book newBook = BookBuilder.from(requestDTO, template, library);

        return BookResponseDTOBuilder.from(bookRepository.save(newBook));
    }

    private void verifyBookAlreadyRegister(BookRequestDTO requestDTO, User user) throws Exception {

        Optional<Book> book = bookRepository.findByBookTemplateIdAndUserId(requestDTO.template().templateId(), user.getId());

        if (book.isPresent()) {
            throw new BookTemplateException("Já existe um cadastro deste livro.", HttpStatus.BAD_REQUEST);
        }
    }
}
