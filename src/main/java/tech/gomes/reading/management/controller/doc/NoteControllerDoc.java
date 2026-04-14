package tech.gomes.reading.management.controller.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tech.gomes.reading.management.controller.filter.NoteFilter;
import tech.gomes.reading.management.dto.note.NoteFullResponseDTO;
import tech.gomes.reading.management.dto.note.NoteRequestDTO;
import tech.gomes.reading.management.dto.note.NoteResponseDTO;
import tech.gomes.reading.management.dto.note.NoteResponsePageDTO;

@RequestMapping("/note")
@Tag(name = "Anotações", description = "Endpoints para gerenciamento das anotações do usuário.")
public interface NoteControllerDoc {

    @GetMapping("/")
    @Operation(summary = "Busca todas as anotações.", description = "Busca as anotações filtradas por categoria, título e livro de referencia (id).")
    @ApiResponse(responseCode = "200", description = "Listagem paginada das anotações encontradas.")
    ResponseEntity<NoteResponsePageDTO> getAllNotesByFilter(NoteFilter filter, JwtAuthenticationToken token);

    @GetMapping("/reversed/")
    @Operation(summary = "Busca todas anotações que possuem um link para a anotação atual (id).",
            description = "As anotações são retornadas apenas com as informações principais, o corpo de texto não está presente.")
    @ApiResponse(responseCode = "200", description = "Listagem paginada das anotações encontradas.")
    ResponseEntity<NoteResponsePageDTO> getAllLinksToNote(@RequestParam(value = "id") long id,
                                                          @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                          @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                          JwtAuthenticationToken token);

    @GetMapping("/linked/")
    @Operation(summary = "Busca todas anotações que são linkadas no corpo da anotação atual (id).",
            description = "As anotações são retornadas apenas com as informações principais, o corpo de texto não está presente.")
    @ApiResponse(responseCode = "200", description = "Listagem paginada das anotações encontradas.")
    ResponseEntity<NoteResponsePageDTO> getAllLinksFromNote(@RequestParam(value = "id") long id,
                                                            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                            @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                            JwtAuthenticationToken token);

    @GetMapping("/{id}")
    @Operation(summary = "Busca anotação.", description = "Busca anotação com o corpo de texto e a lista de todos as anotações linkadas de forma resumida.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anotação encontrada."),
            @ApiResponse(responseCode = "404", description = "A anotação não foi encontrada.")
    })
    ResponseEntity<NoteFullResponseDTO> getNoteById(@PathVariable long id, JwtAuthenticationToken token);

    @PostMapping("/")
    @Operation(summary = "Cria uma nova anotação vazia.", description = "Cria uma nova anotação com um título fixo e incrementável (Sem título (1)) para ser editada posteriormente.")
    @ApiResponse(responseCode = "201", description = "Anotação criada com sucesso.")
    ResponseEntity<NoteResponseDTO> createNote(JwtAuthenticationToken token);

    @PutMapping("/")
    @Operation(summary = "Atualiza a anotação e os links para outras anotações.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anotação atualizada com sucesso."),
            @ApiResponse(responseCode = "404", description = "A anotação não foi encontrada."),
            @ApiResponse(responseCode = "400", description = "Já existe uma anotação com esse título.")
    })
    ResponseEntity<NoteResponseDTO> updateNote(@RequestBody NoteRequestDTO requestDTO, JwtAuthenticationToken token);

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta a anotação.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Anotação deletada com sucesso."),
            @ApiResponse(responseCode = "404", description = "A anotação não foi encontrada.")
    })
    ResponseEntity<Void> deleteNote(@PathVariable long id, JwtAuthenticationToken token);
}
