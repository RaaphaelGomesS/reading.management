package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.gomes.reading.management.builder.BookTemplateResponseDTOBuilder;
import tech.gomes.reading.management.dto.book.BookTemplateRequestDTO;
import tech.gomes.reading.management.dto.book.BookTemplateResponseDTO;
import tech.gomes.reading.management.dto.book.BookTemplateResponsePageDTO;
import tech.gomes.reading.management.service.BookTemplateService;

@RestController
@RequiredArgsConstructor
@RequestMapping(name = "/template")
public class BookTemplateController {

    private final BookTemplateService templateService;

    @GetMapping("/")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<BookTemplateResponsePageDTO> getAllTemplatesByStatus(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                               @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                                               @RequestParam(value = "status", required = false, defaultValue = "IN_ANALYZE") String status) {

        return ResponseEntity.ok(templateService.findAllTemplatesByStatus(page, pageSize, direction, status));
    }

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<BookTemplateResponseDTO> getTemplateToBeAnalyze(@PathVariable long id) throws Exception {

        return ResponseEntity.ok(BookTemplateResponseDTOBuilder.from(templateService.findTemplateById(id)));
    }

    @PostMapping("/fix")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<BookTemplateResponseDTO> updateBookTemplate(@PathVariable BookTemplateRequestDTO requestDTO) throws Exception {

        BookTemplateResponseDTO responseDTO = templateService.updateBookTemplateByAdminRequest(requestDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> inactiveInvalidTemplate(@PathVariable long id) throws Exception {

        templateService.inactiveInvalidTemplate(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

