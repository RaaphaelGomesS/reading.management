package tech.gomes.reading.management.controller.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tech.gomes.reading.management.dto.library.LibraryRequestDTO;
import tech.gomes.reading.management.dto.library.LibraryResponseDTO;
import tech.gomes.reading.management.dto.library.LibraryResponsePageDTO;
import tech.gomes.reading.management.exception.ApplicationException;

@RequestMapping("/library")
@Tag(name = "Bibliotecas", description = "Endpoints para consultar e gerenciar bibliotecas.")
public interface LibraryControllerDoc {

    @GetMapping("/")
    @Operation(summary = "Busca todas bibliotecas do usuário.")
    @ApiResponse(responseCode = "200", description = "Listagem das bibliotecas do usuário de forma paginada.")
    ResponseEntity<LibraryResponsePageDTO> getAllLibrariesByUserId(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                   @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                                   JwtAuthenticationToken token) throws ApplicationException;

    @GetMapping("/{id}")
    @Operation(summary = "Busca biblioteca pelo id.")
    @ApiResponse(responseCode = "200", description = "Biblioteca encontrada.")
    ResponseEntity<LibraryResponseDTO> findLibraryById(@PathVariable Long id, JwtAuthenticationToken token) throws ApplicationException;

    @PostMapping("/")
    @Operation(summary = "Cria nova biblioteca do usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Biblioteca criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Já existe uma biblioteca com esse nome.")
    })
    ResponseEntity<LibraryResponseDTO> createNewLibrary(@RequestBody LibraryRequestDTO requestDTO, JwtAuthenticationToken token) throws ApplicationException;

    @PutMapping("/")
    @Operation(summary = "Atualiza as informações da biblioteca.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Biblioteca atualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Já existe uma biblioteca com esse nome.")
    })
    ResponseEntity<LibraryResponseDTO> updateLibraryById(@RequestBody LibraryRequestDTO requestDTO, JwtAuthenticationToken token) throws ApplicationException;

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta a biblioteca do usuário.")
    @ApiResponse(responseCode = "200", description = "Biblioteca deletada com sucesso.")
    ResponseEntity<Void> deleteLibraryById(@PathVariable Long id, JwtAuthenticationToken token) throws ApplicationException;

    @PostMapping("/new/library/{id}")
    @Operation(summary = "Cria nova biblioteca a partir do plano de leitura.", description = "Cria uma nova biblioteca com todas instancias dos templates presentes no plano de leitura.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem paginada dos templates."),
            @ApiResponse(responseCode = "404", description = "Nenhuma plano foi encontrado."),
            @ApiResponse(responseCode = "400", description = "Já existe uma biblioteca com esse nome.")
    })
    ResponseEntity<LibraryResponseDTO> createLibraryByPlan(@PathVariable Long id, JwtAuthenticationToken token) throws ApplicationException;
}
