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
    public ResponseEntity<BookTemplateResponsePageDTO> searchTemplateByFilter(BookTemplateFilter filter);

    @GetMapping("/")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<BookTemplateResponsePageDTO> getAllTemplatesByStatus(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                               @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                                               @RequestParam(value = "status", required = false, defaultValue = "IN_ANALYZE") String status);

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<BookTemplateResponseDTO> getTemplateToBeAnalyze(@PathVariable long id);

    @PutMapping(value = "/fix", consumes = {"multipart/form-data"})
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<BookTemplateResponseDTO> updateBookTemplate(@RequestPart("template") BookTemplateRequestDTO requestDTO,
                                                                      @RequestPart("coverImg") MultipartFile file);

    @PostMapping("/inactive/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> inactiveInvalidTemplate(@PathVariable long id);

    @PostMapping("/approve/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> approveTemplate(@PathVariable long id);
}
