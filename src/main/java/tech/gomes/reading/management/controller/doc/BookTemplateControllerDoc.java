package tech.gomes.reading.management.controller.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.gomes.reading.management.controller.filter.BookTemplateFilter;
import tech.gomes.reading.management.dto.book.request.BookTemplateRequestDTO;
import tech.gomes.reading.management.dto.book.response.BookTemplateResponseDTO;
import tech.gomes.reading.management.dto.book.response.BookTemplateResponsePageDTO;

@RequestMapping("/template")
@Tag(name = "Templates dos livros", description = "Endpoints para consultar e gerenciar templates dos livros.")
public interface BookTemplateControllerDoc {

    @Operation(summary = "Busca todos os templates", description = "Busca todos os templates verificados por título, nome do autor ou ISBN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem paginada dos templates.")
    })
    @GetMapping("/search")
    ResponseEntity<BookTemplateResponsePageDTO> searchTemplateByFilter(BookTemplateFilter filter);

    @Operation(summary = "Busca todos os templates filtrados por status", description = "Apenas o admin pode listar os templates de diferente status.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem paginada dos templates por status.")
    })
    @GetMapping("/")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    ResponseEntity<BookTemplateResponsePageDTO> getAllTemplatesByStatus(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                        @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                                        @RequestParam(value = "status", required = false, defaultValue = "Analisando") String status);

    @Operation(summary = "Busca o template pelo id", description = "Apenas o admin pode consultar um template diretamente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem paginada dos templates."),
            @ApiResponse(responseCode = "404", description = "O template não foi encontrado.")
    })
    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    ResponseEntity<BookTemplateResponseDTO> getTemplateToBeAnalyze(@PathVariable long id);

    @Operation(summary = "Atualiza as informações do template", description = "Apenas o admin pode alterar as informações de um template, o ISBN, ou chave de título/autor quando ISBN não for presente, deve ser única.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem paginada dos templates."),
            @ApiResponse(responseCode = "404", description = "O template não foi encontrado."),
            @ApiResponse(responseCode = "400", description = "Já existe um template com o identificador."),
            @ApiResponse(responseCode = "400", description = "O nome do arquivo de imagem contém uma sequência de caminho inválida."),
            @ApiResponse(responseCode = "400", description = "Não foi possível armazenar o arquivo.")
    })
    @PutMapping(value = "/fix", consumes = {"multipart/form-data"})
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    ResponseEntity<BookTemplateResponseDTO> updateBookTemplate(@RequestPart("template") BookTemplateRequestDTO requestDTO,
                                                               @RequestPart("coverImg") MultipartFile file);

    @Operation(summary = "Desativa o template", description = "Apenas o admin pode desativar um template, tornando impossivel de instanciar por outros usuários que não aquele que criou.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem paginada dos templates."),
            @ApiResponse(responseCode = "404", description = "O template não foi encontrado.")
    })
    @PostMapping("/inactive/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    ResponseEntity<Void> inactiveInvalidTemplate(@PathVariable long id);

    @Operation(summary = "Aprova o template", description = "Apenas o admin poder aprovar um template e torna-lo disponível para os demais usuários, possibilitando a criação de instancias.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listagem paginada dos templates."),
            @ApiResponse(responseCode = "404", description = "O template não foi encontrado.")
    })
    @PostMapping("/approve/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    ResponseEntity<Void> approveTemplate(@PathVariable long id);
}
