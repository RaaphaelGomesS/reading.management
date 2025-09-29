package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tech.gomes.reading.management.dto.suggestion.request.DeclineRequestDTO;
import tech.gomes.reading.management.dto.suggestion.response.SuggestionResponseDTO;
import tech.gomes.reading.management.dto.suggestion.response.SuggestionResponsePageDTO;
import tech.gomes.reading.management.dto.suggestion.response.SuggestionUpdateResponseDTO;
import tech.gomes.reading.management.service.SuggestionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/suggestion")
public class SuggestionController {

    private final SuggestionService suggestionService;

    @GetMapping("/")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<SuggestionResponsePageDTO> getAllCreationSuggestion(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                              @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                                              @RequestParam(value = "status", required = false, defaultValue = "IN_ANALYZE") String status) {

        return ResponseEntity.ok(suggestionService.findAllCreationSuggestion(page, pageSize, direction, status));
    }

    @GetMapping("/update")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<SuggestionResponsePageDTO> getAllUpdateSuggestion(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                            @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                                            @RequestParam(value = "status", required = false, defaultValue = "IN_ANALYZE") String status) {

        return ResponseEntity.ok(suggestionService.findAllUpdateSuggestion(page, pageSize, direction, status));
    }

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<SuggestionResponseDTO> getCreateSuggestion(@PathVariable long id) throws Exception {

        return ResponseEntity.ok(suggestionService.findCreateSuggestionById(id));
    }

    @GetMapping("/update/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<SuggestionUpdateResponseDTO> getUpdateSuggestionWithOriginalTemplate(@PathVariable long id) throws Exception {

        return ResponseEntity.ok(suggestionService.findUpdateSuggestionWithTemplate(id));
    }

    @GetMapping("/approve/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> approveSuggestion(@PathVariable long id) throws Exception {

        suggestionService.approveSuggestion(id);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/decline/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Void> declineSuggestion(@RequestBody DeclineRequestDTO requestDTO) throws Exception {

        suggestionService.declineSuggestion(requestDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
