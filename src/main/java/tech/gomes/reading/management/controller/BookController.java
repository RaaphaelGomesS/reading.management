package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.book.BookRequestDTO;
import tech.gomes.reading.management.dto.book.BookResponseDTO;
import tech.gomes.reading.management.dto.book.ChangeLibRequestDTO;
import tech.gomes.reading.management.dto.book.FullBookResponseDTO;
import tech.gomes.reading.management.service.AuthService;
import tech.gomes.reading.management.service.BookService;


@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthService authService;


    @PostMapping("/")
    public ResponseEntity<BookResponseDTO> registerBookInLibrary(@RequestBody BookRequestDTO requestDTO, JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        BookResponseDTO responseDTO = bookService.createBook(requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/update")
    public ResponseEntity<BookResponseDTO> updateBook(@RequestBody BookRequestDTO requestDTO, JwtAuthenticationToken token) throws Exception {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FullBookResponseDTO> getBookInformation(@PathVariable long id) throws Exception {
        return ResponseEntity.ok(null);
    }

    @PostMapping("/{id}")
    public ResponseEntity<BookResponseDTO> changeBookFromLibrary(@RequestBody ChangeLibRequestDTO requestDTO) throws Exception {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable long id) throws Exception {
        return ResponseEntity.ok(null);
    }
}
