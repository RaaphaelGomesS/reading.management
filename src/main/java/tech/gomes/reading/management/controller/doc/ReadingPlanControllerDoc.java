package tech.gomes.reading.management.controller.doc;

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
public interface ReadingPlanControllerDoc {

    @GetMapping("/")
    ResponseEntity<ReadingPlanPageDTO> getUserPlans(ReadingPlanFilter filter,
                                                    JwtAuthenticationToken token);

    @GetMapping("/public")
    ResponseEntity<ReadingPlanPageDTO> getPublicPlans(ReadingPlanFilter filter);

    @PostMapping("/")
    ResponseEntity<PlanResponseDTO> createPlan(@RequestBody PlanRequestDTO requestDTO, JwtAuthenticationToken token);

    @PostMapping("/privacy/update")
    ResponseEntity<PlanResponseDTO> updatePrivacyOfPlan(@RequestBody @Valid PrivacyPlanDTO requestDTO, JwtAuthenticationToken token);

    @PutMapping("/{id}")
    ResponseEntity<PlanResponseDTO> updatePlan(@PathVariable long id, @RequestBody PlanRequestDTO requestDTO, JwtAuthenticationToken token);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deletePlan(@PathVariable long id, JwtAuthenticationToken token);

    @PostMapping("/clone/{id}")
    ResponseEntity<PlanResponseDTO> duplicatePlan(@PathVariable long id, JwtAuthenticationToken token);
}
