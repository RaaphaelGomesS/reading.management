package tech.gomes.reading.management.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.book.request.*;
import tech.gomes.reading.management.dto.book.response.BookResponseDTO;
import tech.gomes.reading.management.dto.book.response.BookResponsePageDTO;
import tech.gomes.reading.management.dto.book.response.FullBookResponseDTO;
import tech.gomes.reading.management.exception.BookException;
import tech.gomes.reading.management.exception.UserException;
import tech.gomes.reading.management.indicator.ReadingStatusIndicator;
import tech.gomes.reading.management.service.AuthService;
import tech.gomes.reading.management.service.BookService;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@Tag(name = "Livros", description = "Endpoints para consultar e gerenciar livros do usuário.")
public class BookController {

    private final BookService bookService;

    private final AuthService authService;

    @Operation(summary = "Busca livros pelo status na biblioteca (id)", description = "Retorna os livros paginados da biblioteca do usuário com o status solicitado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem dos livros encontrados."),
            @ApiResponse(responseCode = "404", description = "A biblioteca não foi encontrada.")
    })
    @GetMapping("/status/{id}")
    public ResponseEntity<BookResponsePageDTO> getBooksInLibraryByStatus(@PathVariable long id,
                                                                         @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                         @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                         @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                                         @RequestParam(value = "status", required = false, defaultValue = "reading") String status,
                                                                         JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        BookResponsePageDTO responseDTO = bookService.getAllBooksByStatusInLibrary(id, user, ReadingStatusIndicator.getStatusByName(status), page, pageSize, direction);

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Cria livro e sugestão de template caso não exista", description = "Retorna as informações do livro criado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro criado."),
            @ApiResponse(responseCode = "404", description = "A biblioteca não foi encontrada.")
    })
    @PostMapping(value = "/", consumes = {"multipart/form-data"})
    public ResponseEntity<BookResponseDTO> registerBookInLibrary(@RequestPart("book") BookCreateRequestDTO requestDTO,
                                                                 @RequestPart(value = "coverImg", required = false) MultipartFile file,
                                                                 JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        BookResponseDTO responseDTO = bookService.createBook(requestDTO, user, file);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar informações do livro", description = "Retorna as informações do livro atualizado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro atualizado."),
            @ApiResponse(responseCode = "404", description = "Não foi encontrado o livro."),
            @ApiResponse(responseCode = "404", description = "A biblioteca não foi encontrada.")
    })
    @PutMapping("/")
    public ResponseEntity<BookResponseDTO> updateBook(@RequestBody BookRequestDTO requestDTO, JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        BookResponseDTO responseDTO = bookService.updateBook(requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Atualizar quantidade de páginas lidas.", description = "Retorna as informações do livro com a quantidade de páginas e status atualizado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro atualizado."),
            @ApiResponse(responseCode = "404", description = "Não foi encontrado o livro."),
            @ApiResponse(responseCode = "400", description = "Quantidade de páginas acima da quantidade total no template.")
    })
    @PostMapping("/alter/pages")
    public ResponseEntity<BookResponseDTO> updatePagesReadInBook(@RequestBody PagesUpdateRequestDTO requestDTO, JwtAuthenticationToken token) throws UserException, BookException {

        User user = authService.getUserByToken(token);

        BookResponseDTO responseDTO = bookService.updateReadPages(requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Finalizar leitura de um livro.", description = "Retorna as informações do livro com o status definido como 'READ'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro com status atualizado."),
            @ApiResponse(responseCode = "404", description = "Não foi encontrado o livro."),
    })
    @PostMapping("/finish")
    public ResponseEntity<BookResponseDTO> finishBook(@RequestBody FinishBookRequestDTO requestDTO, JwtAuthenticationToken token) throws UserException, BookException {

        User user = authService.getUserByToken(token);

        BookResponseDTO responseDTO = bookService.finishBook(requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Busca as informações complestas do livro e do template.", description = "Retorna as informações do livro e do template.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Informações do livro e do template."),
            @ApiResponse(responseCode = "404", description = "Não foi encontrado o livro."),
    })
    @GetMapping("/{id}")
    public ResponseEntity<FullBookResponseDTO> getBookInformation(@PathVariable long id, JwtAuthenticationToken token) throws UserException, BookException {

        User user = authService.getUserByToken(token);

        FullBookResponseDTO responseDTO = bookService.getFullBookById(id, user);

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Altera o livro de biblioteca.", description = "Retorna as informações do livro qual a biblioteca atualizada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro com a biblioteca atualizada."),
            @ApiResponse(responseCode = "404", description = "Não foi encontrado o livro."),
            @ApiResponse(responseCode = "404", description = "A biblioteca não foi encontrada.")
    })
    @PostMapping("/alter/library")
    public ResponseEntity<BookResponseDTO> changeBookFromLibrary(@RequestBody ChangeLibRequestDTO requestDTO, JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        BookResponseDTO responseDTO = bookService.changeBookFromLibrary(requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }

    @Operation(summary = "Deleta o livro.", description = "Deleta o livro.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Livro deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Não foi encontrado o livro."),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable long id, JwtAuthenticationToken token) throws UserException, BookException {

        User user = authService.getUserByToken(token);

        bookService.deleteBook(id, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
