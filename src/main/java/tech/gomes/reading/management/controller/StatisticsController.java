package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.StatisticsResponseDTO;
import tech.gomes.reading.management.service.AuthService;
import tech.gomes.reading.management.service.StatisticsService;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
public class StatisticsController {

    private final AuthService authService;

    private final StatisticsService statisticsService;

    @PostMapping("/")
    public ResponseEntity<StatisticsResponseDTO> getUserStatistics(JwtAuthenticationToken token) throws Exception {
        User user = authService.getUserByToken(token);

        return ResponseEntity.ok(statisticsService.getStatisticsForUser(user));
    }
}
