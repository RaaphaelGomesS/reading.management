package tech.gomes.reading.management.controller.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tech.gomes.reading.management.dto.StatisticsResponseDTO;
import tech.gomes.reading.management.dto.book.response.BookTemplateResponseDTO;

import java.util.List;

@RequestMapping("/stats")
@Tag(name = "Estatísticas e recomendações", description = "Endpoints para consultar estatísticas do usuário e recomendações personalizadas.")
public interface StatisticsControllerDoc {

    @GetMapping("/")
    @Operation(summary = "Busca as estatísticas de leitura do usuário.", description = "É retornado a média de páginas lídas e dias para terminar uma obra, qtd de livros por status e qtd de livros finalizados por categoria.")
    @ApiResponse(responseCode = "200", description = "Listagem das categorias encontradas.")
    ResponseEntity<StatisticsResponseDTO> getUserStatistics(JwtAuthenticationToken token);

    @GetMapping("/recommendation")
    @Operation(summary = "Busca recomendações de obras baseado na última leitura finalizada.", description = "A recomendação é feita com base nas categorias da ultima obra.")
    @ApiResponse(responseCode = "200", description = "Listagem das recomendações.")
    ResponseEntity<List<BookTemplateResponseDTO>> getReadRecommendation(JwtAuthenticationToken token);
}
