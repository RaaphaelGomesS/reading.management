package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.suggestion.response.SuggestionResponsePageDTO;
import tech.gomes.reading.management.dto.suggestion.response.SuggestionUpdatedResponsePageDTO;
import tech.gomes.reading.management.service.AuthService;
import tech.gomes.reading.management.service.SuggestionService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/suggestion")
public class SuggestionController {

    private final AuthService authService;

    private final SuggestionService suggestionService;

    @GetMapping("/")
    public ResponseEntity<SuggestionResponsePageDTO> getAllCreationSuggestion(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                              @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                                              @RequestParam(value = "status", required = false, defaultValue = "IN_ANALYZE") String status,
                                                                              JwtAuthenticationToken token) throws Exception {
        User user = authService.getUserByToken(token);

        return ResponseEntity.ok(null);
    }

    @GetMapping("/update")
    public ResponseEntity<SuggestionUpdatedResponsePageDTO> getAllUpdateSuggestionWithOriginalTemplate(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                                                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                                                       @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                                                                       @RequestParam(value = "status", required = false, defaultValue = "IN_ANALYZE") String status,
                                                                                                       JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        return ResponseEntity.ok(null);
    }
}
