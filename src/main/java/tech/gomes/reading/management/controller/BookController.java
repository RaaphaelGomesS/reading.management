package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.gomes.reading.management.dto.book.BookResponseDTO;
import tech.gomes.reading.management.service.BookService;


@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping("/")
    public ResponseEntity<BookResponseDTO> registerBookInLibrary() {
        return ResponseEntity.ok(null);
    }
}
