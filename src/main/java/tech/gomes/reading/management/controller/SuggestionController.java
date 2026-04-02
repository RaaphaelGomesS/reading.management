package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tech.gomes.reading.management.controller.doc.SuggestionControllerDoc;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.suggestion.request.DeclineRequestDTO;
import tech.gomes.reading.management.dto.suggestion.request.SuggestionRequestDTO;
import tech.gomes.reading.management.dto.suggestion.response.SuggestionResponsePageDTO;
import tech.gomes.reading.management.dto.suggestion.response.SuggestionUpdateResponseDTO;
import tech.gomes.reading.management.service.AuthService;
import tech.gomes.reading.management.service.SuggestionService;

@RestController
@RequiredArgsConstructor
public class SuggestionController implements SuggestionControllerDoc {

    private final SuggestionService suggestionService;

    private final AuthService authService;

    public ResponseEntity<Void> createUpdateSuggestion(SuggestionRequestDTO requestDTO,
                                                       MultipartFile file,
                                                       JwtAuthenticationToken token) {

        User user = authService.getUserByToken(token);

        suggestionService.createUpdateSuggestion(requestDTO, user, file);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<SuggestionResponsePageDTO> getAllSuggestion(int page,
                                                                      int pageSize,
                                                                      String direction,
                                                                      String status) {

        return ResponseEntity.ok(suggestionService.findAllUpdateSuggestion(page, pageSize, direction, status));
    }

    public ResponseEntity<SuggestionUpdateResponseDTO> getUpdateSuggestionWithOriginalTemplate(long id) {

        return ResponseEntity.ok(suggestionService.findUpdateSuggestion(id));
    }

    public ResponseEntity<Void> approveSuggestion(long id) {

        suggestionService.approveSuggestion(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> declineSuggestion(DeclineRequestDTO requestDTO) {

        suggestionService.declineSuggestion(requestDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
