package tech.gomes.reading.management.controller.doc;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.gomes.reading.management.dto.suggestion.request.DeclineRequestDTO;
import tech.gomes.reading.management.dto.suggestion.request.SuggestionRequestDTO;
import tech.gomes.reading.management.dto.suggestion.response.SuggestionResponsePageDTO;
import tech.gomes.reading.management.dto.suggestion.response.SuggestionUpdateResponseDTO;

@RequestMapping("/suggestion")
public interface SuggestionControllerDoc {

    @PostMapping(value = "/", consumes = {"multipart/form-data"})
    ResponseEntity<Void> createUpdateSuggestion(@RequestPart("suggestion") SuggestionRequestDTO requestDTO,
                                                @RequestPart(value = "coverImg", required = false) MultipartFile file,
                                                JwtAuthenticationToken token);

    @GetMapping("/")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    ResponseEntity<SuggestionResponsePageDTO> getAllSuggestion(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                               @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                               @RequestParam(value = "status", required = false, defaultValue = "IN_ANALYZE") String status);

    @GetMapping("/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    ResponseEntity<SuggestionUpdateResponseDTO> getUpdateSuggestionWithOriginalTemplate(@PathVariable long id);

    @PostMapping("/approve/{id}")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    ResponseEntity<Void> approveSuggestion(@PathVariable long id);

    @PostMapping("/decline/")
    @PreAuthorize(value = "hasAuthority('SCOPE_ADMIN')")
    ResponseEntity<Void> declineSuggestion(@RequestBody DeclineRequestDTO requestDTO);
}
