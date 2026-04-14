package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tech.gomes.reading.management.controller.doc.BookControllerDoc;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.book.request.*;
import tech.gomes.reading.management.dto.book.response.BookResponseDTO;
import tech.gomes.reading.management.dto.book.response.BookResponsePageDTO;
import tech.gomes.reading.management.dto.book.response.FullBookResponseDTO;
import tech.gomes.reading.management.exception.FileException;
import tech.gomes.reading.management.indicator.ReadingStatusIndicator;
import tech.gomes.reading.management.service.AuthService;
import tech.gomes.reading.management.service.BookService;

@RestController
@RequiredArgsConstructor
public class BookController implements BookControllerDoc {

    private final BookService bookService;

    private final AuthService authService;

    public ResponseEntity<BookResponsePageDTO> getBooksInLibraryByStatus(long id,
                                                                         int page,
                                                                         int pageSize,
                                                                         String direction,
                                                                         String status,
                                                                         JwtAuthenticationToken token) {

        User user = authService.getUserByToken(token);

        BookResponsePageDTO responseDTO = bookService.getAllBooksByStatusInLibrary(id, user, ReadingStatusIndicator.getStatusByName(status), page, pageSize, direction);

        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<BookResponseDTO> registerBookInLibrary(BookCreateRequestDTO requestDTO,
                                                                 MultipartFile file,
                                                                 JwtAuthenticationToken token) throws FileException {

        User user = authService.getUserByToken(token);

        BookResponseDTO responseDTO = bookService.createBook(requestDTO, user, file);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    public ResponseEntity<BookResponseDTO> updateBook(BookRequestDTO requestDTO, JwtAuthenticationToken token) {

        User user = authService.getUserByToken(token);

        BookResponseDTO responseDTO = bookService.updateBook(requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<BookResponseDTO> updatePagesReadInBook(PagesUpdateRequestDTO requestDTO, JwtAuthenticationToken token) {

        User user = authService.getUserByToken(token);

        BookResponseDTO responseDTO = bookService.updateReadPages(requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<BookResponseDTO> finishBook(FinishBookRequestDTO requestDTO, JwtAuthenticationToken token) {

        User user = authService.getUserByToken(token);

        BookResponseDTO responseDTO = bookService.finishBook(requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<FullBookResponseDTO> getBookInformation(long id, JwtAuthenticationToken token) {

        User user = authService.getUserByToken(token);

        FullBookResponseDTO responseDTO = bookService.getFullBookById(id, user);

        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<BookResponseDTO> changeBookFromLibrary(ChangeLibRequestDTO requestDTO, JwtAuthenticationToken token) {

        User user = authService.getUserByToken(token);

        BookResponseDTO responseDTO = bookService.changeBookFromLibrary(requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<Void> deleteBook(long id, JwtAuthenticationToken token) {

        User user = authService.getUserByToken(token);

        bookService.deleteBook(id, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
