package tech.gomes.reading.management.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tech.gomes.reading.management.controller.filter.ReadingPlanFilter;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.library.LibraryResponseDTO;
import tech.gomes.reading.management.dto.readingPlan.ReadingPlanPageDTO;
import tech.gomes.reading.management.dto.readingPlan.request.PlanRequestDTO;
import tech.gomes.reading.management.dto.readingPlan.request.PrivacyPlanDTO;
import tech.gomes.reading.management.dto.readingPlan.response.PlanResponseDTO;
import tech.gomes.reading.management.service.AuthService;
import tech.gomes.reading.management.service.ReadingPlanService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/plan")
public class ReadingPlanController {

    private final AuthService authService;

    private final ReadingPlanService planService;

    @GetMapping("/")
    public ResponseEntity<ReadingPlanPageDTO> getUserPlans(ReadingPlanFilter filter,
                                                           JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        ReadingPlanPageDTO pageDTO = planService.findAllPlans(user, filter);

        return ResponseEntity.ok(pageDTO);
    }

    @GetMapping("/public")
    public ResponseEntity<ReadingPlanPageDTO> getPublicPlans(ReadingPlanFilter filter) {

        ReadingPlanPageDTO pageDTO = planService.findAllPlans(null, filter);

        return ResponseEntity.ok(pageDTO);
    }

    @PostMapping("/")
    public ResponseEntity<PlanResponseDTO> createPlan(@RequestBody PlanRequestDTO requestDTO, JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        PlanResponseDTO responseDTO = planService.createReadingPlan(requestDTO, user);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @PostMapping("/privacy/update")
    public ResponseEntity<PlanResponseDTO> updatePrivacyOfPlan(@RequestBody @Valid PrivacyPlanDTO requestDTO, JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        PlanResponseDTO responseDTO = planService.updatePrivacyFromPlan(requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanResponseDTO> updatePlan(@PathVariable long id, @RequestBody PlanRequestDTO requestDTO, JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        PlanResponseDTO responseDTO = planService.updateReadingPlan(id, requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable long id, JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        planService.deletePlan(id, user);

        return ResponseEntity.ok(null);
    }

    @PostMapping("/clone/{id}")
    ResponseEntity<PlanResponseDTO> duplicatePlan(@PathVariable long id, JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        PlanResponseDTO responseDTO = planService.duplicatePlanToUser(id, user);

        return ResponseEntity.ok(responseDTO);
    }
}
