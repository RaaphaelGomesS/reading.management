package tech.gomes.reading.management.controller.doc;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.gomes.reading.management.dto.StatisticsResponseDTO;
import tech.gomes.reading.management.dto.book.response.BookTemplateResponseDTO;

import java.util.List;

@RequestMapping("/stats")
public interface StatisticsControllerDoc {

    @GetMapping("/")
    ResponseEntity<StatisticsResponseDTO> getUserStatistics(JwtAuthenticationToken token);

    @GetMapping("/recommendation")
    ResponseEntity<List<BookTemplateResponseDTO>> getReadRecommendation(JwtAuthenticationToken token);
}
