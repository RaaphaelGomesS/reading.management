package tech.gomes.reading.management.controller.doc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tech.gomes.reading.management.controller.filter.ReadingPlanFilter;
import tech.gomes.reading.management.dto.readingPlan.ReadingPlanPageDTO;
import tech.gomes.reading.management.dto.readingPlan.request.PlanRequestDTO;
import tech.gomes.reading.management.dto.readingPlan.request.PrivacyPlanDTO;
import tech.gomes.reading.management.dto.readingPlan.response.PlanResponseDTO;

@RequestMapping("/plan")
@Tag(name = "Planos de leituras", description = "Endpoints para gerenciamento dos planos de leituras.")
public interface ReadingPlanControllerDoc {

    @GetMapping("/")
    @Operation(summary = "Busca todos os planos de leituras do usuário.", description = "Busca pelos planos do usuário filtrando por título e/ou categorias; Ordena entre relevancia e mais recente.")
    @ApiResponse(responseCode = "200", description = "Lista paginada dos planos de leituras encontrados.")
    ResponseEntity<ReadingPlanPageDTO> getUserPlans(ReadingPlanFilter filter,
                                                    JwtAuthenticationToken token);

    @GetMapping("/public")
    @Operation(summary = "Busca todos os planos de leituras públicos.", description = "Busca pelos planos públicos filtrando por título e/ou categorias; Ordena entre relevancia e mais recente.")
    @ApiResponse(responseCode = "200", description = "Lista paginada dos planos de leituras encontrados.")
    ResponseEntity<ReadingPlanPageDTO> getPublicPlans(ReadingPlanFilter filter);

    @PostMapping("/")
    @Operation(summary = "Cria um novo plano de leituras.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plano criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Já existe um plano com esse título."),
            @ApiResponse(responseCode = "400", description = "Categorias não foram encontradas.")
    })
    ResponseEntity<PlanResponseDTO> createPlan(@RequestBody PlanRequestDTO requestDTO, JwtAuthenticationToken token);

    @PostMapping("/privacy/update")
    @Operation(summary = "Altera a privacidade de um plano.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Privacidade alterada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Nenhuma plano foi encontrado.")
    })
    ResponseEntity<PlanResponseDTO> updatePrivacyOfPlan(@RequestBody @Valid PrivacyPlanDTO requestDTO, JwtAuthenticationToken token);

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um plano de leituras.", description = "Somente o admin pode criar uma categoria.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plano atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Categoria não foi encontrada."),
            @ApiResponse(responseCode = "404", description = "Nenhuma plano foi encontrado.")
    })
    ResponseEntity<PlanResponseDTO> updatePlan(@PathVariable long id, @RequestBody PlanRequestDTO requestDTO, JwtAuthenticationToken token);

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um plano de leituras.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Plano deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Categoria não foi encontrada.")
    })
    ResponseEntity<Void> deletePlan(@PathVariable long id, JwtAuthenticationToken token);

    @PostMapping("/clone/{id}")
    @Operation(summary = "Cria um novo plano para o usuário baseado em um plano público.", description = "Cada clone criado a partir de um plano público aumenta sua relevancia.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna o novo plano para o usuário."),
            @ApiResponse(responseCode = "404", description = "Nenhuma plano foi encontrado.")
    })
    ResponseEntity<PlanResponseDTO> duplicatePlan(@PathVariable long id, JwtAuthenticationToken token);
}
