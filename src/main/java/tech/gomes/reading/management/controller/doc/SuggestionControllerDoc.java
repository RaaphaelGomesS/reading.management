package tech.gomes.reading.management.controller.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.gomes.reading.management.dto.suggestion.request.DeclineRequestDTO;
import tech.gomes.reading.management.dto.suggestion.request.SuggestionRequestDTO;
import tech.gomes.reading.management.dto.suggestion.response.SuggestionResponsePageDTO;
import tech.gomes.reading.management.dto.suggestion.response.SuggestionUpdateResponseDTO;
import tech.gomes.reading.management.exception.FileException;

@RequestMapping("/suggestion")
@Tag(name = "Sugestões de alteração de templates", description = "Endpoints para gerenciamento das sugestões de alterações nos templates.")
public interface SuggestionControllerDoc {

    @PostMapping(value = "/", consumes = {"multipart/form-data"})
    @Operation(summary = "Cria nova sugestão de alteração de template.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sugestão criada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Deve justificar a alteração."),
            @ApiResponse(responseCode = "404", description = "O template não foi encontrado."),
            @ApiResponse(responseCode = "400", description = "Já existe sugestão de alteração para esse template em análise."),
            @ApiResponse(responseCode = "400", description = "O nome do arquivo contém uma sequência de caminho inválida.")
    })
    ResponseEntity<Void> createUpdateSuggestion(@RequestPart("suggestion") SuggestionRequestDTO requestDTO,
                                                @RequestPart(value = "coverImg", required = false) MultipartFile file,
                                                JwtAuthenticationToken token) throws FileException;

    @GetMapping("/")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    @Operation(summary = "Busca todas as sugestões.")
    @ApiResponse(responseCode = "200", description = "Listagem das sugestões encontradas.")
    ResponseEntity<SuggestionResponsePageDTO> getAllSuggestion(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                               @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                               @RequestParam(value = "status", required = false, defaultValue = "Analisando") String status);

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    @Operation(summary = "Busca sugestão junto com o template original.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem das categorias encontradas."),
            @ApiResponse(responseCode = "404", description = "Não foi encontrado nenhuma sugestão com esse id.")
    })
    ResponseEntity<SuggestionUpdateResponseDTO> getUpdateSuggestionWithOriginalTemplate(@PathVariable long id);

    @PatchMapping("/approve/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    @Operation(summary = "Busca todas as categórias para plano de leituras.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem das categorias encontradas."),
            @ApiResponse(responseCode = "404", description = "Não foi encontrado nenhuma sugestão com esse id."),
            @ApiResponse(responseCode = "400", description = "A sugestão de atualização precisa estar vinculada a um template.")
    })
    ResponseEntity<Void> approveSuggestion(@PathVariable long id);

    @PatchMapping("/decline/")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    @Operation(summary = "Busca todas as categórias para plano de leituras.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem das categorias encontradas."),
            @ApiResponse(responseCode = "404", description = "Não foi encontrado nenhuma sugestão com esse id.")
    })
    ResponseEntity<Void> declineSuggestion(@RequestBody DeclineRequestDTO requestDTO);
}
