package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;
import tech.gomes.reading.management.controller.doc.StatisticsControllerDoc;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.StatisticsResponseDTO;
import tech.gomes.reading.management.dto.book.response.BookTemplateResponseDTO;
import tech.gomes.reading.management.service.AuthService;
import tech.gomes.reading.management.service.StatisticsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StatisticsController implements StatisticsControllerDoc {

    private final AuthService authService;

    private final StatisticsService statisticsService;

    public ResponseEntity<StatisticsResponseDTO> getUserStatistics(JwtAuthenticationToken token) {
        User user = authService.getUserByToken(token);

        return ResponseEntity.ok(statisticsService.getStatisticsForUser(user));
    }

    public ResponseEntity<List<BookTemplateResponseDTO>> getReadRecommendation(JwtAuthenticationToken token) {
        User user = authService.getUserByToken(token);

        return ResponseEntity.ok(statisticsService.findReadRecommendationForUser(user));
    }
}
