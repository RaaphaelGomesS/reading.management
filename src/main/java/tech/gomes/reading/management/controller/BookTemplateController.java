package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tech.gomes.reading.management.controller.doc.BookTemplateControllerDoc;
import tech.gomes.reading.management.controller.filter.BookTemplateFilter;
import tech.gomes.reading.management.dto.book.request.BookTemplateRequestDTO;
import tech.gomes.reading.management.dto.book.response.BookTemplateResponseDTO;
import tech.gomes.reading.management.dto.book.response.BookTemplateResponsePageDTO;
import tech.gomes.reading.management.exception.ApplicationException;
import tech.gomes.reading.management.service.BookTemplateService;

@RestController
@RequiredArgsConstructor
public class BookTemplateController implements BookTemplateControllerDoc {

    private final BookTemplateService templateService;

    public ResponseEntity<BookTemplateResponsePageDTO> searchTemplateByFilter(BookTemplateFilter filter) {

        return ResponseEntity.ok(templateService.findAllTemplatesByFilter(filter));
    }

    public ResponseEntity<BookTemplateResponsePageDTO> getAllTemplatesByStatus(int page,
                                                                               int pageSize,
                                                                               String direction,
                                                                               String status) {

        return ResponseEntity.ok(templateService.findAllTemplatesByStatus(page, pageSize, direction, status));
    }

    public ResponseEntity<BookTemplateResponseDTO> getTemplateToBeAnalyze(long id) {

        BookTemplateResponseDTO responseDTO = templateService.findTemplateToBeAnalyzeAndConvertToDTO(id);

        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<BookTemplateResponseDTO> updateBookTemplate(BookTemplateRequestDTO requestDTO,
                                                                      MultipartFile file) throws ApplicationException {

        BookTemplateResponseDTO responseDTO = templateService.updateBookTemplateByAdminRequest(requestDTO, file);

        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<Void> inactiveInvalidTemplate(long id) {

        templateService.inactiveInvalidTemplate(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> approveTemplate(long id) {

        templateService.approveTemplate(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}

