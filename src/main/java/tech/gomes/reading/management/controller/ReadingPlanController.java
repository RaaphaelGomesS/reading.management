package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;
import tech.gomes.reading.management.controller.doc.ReadingPlanControllerDoc;
import tech.gomes.reading.management.controller.filter.ReadingPlanFilter;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.readingPlan.ReadingPlanPageDTO;
import tech.gomes.reading.management.dto.readingPlan.request.PlanRequestDTO;
import tech.gomes.reading.management.dto.readingPlan.request.PrivacyPlanDTO;
import tech.gomes.reading.management.dto.readingPlan.response.PlanResponseDTO;
import tech.gomes.reading.management.service.AuthService;
import tech.gomes.reading.management.service.ReadingPlanService;

@RestController
@RequiredArgsConstructor
public class ReadingPlanController implements ReadingPlanControllerDoc {

    private final AuthService authService;

    private final ReadingPlanService planService;

    public ResponseEntity<ReadingPlanPageDTO> getUserPlans(ReadingPlanFilter filter,
                                                           JwtAuthenticationToken token) {

        User user = authService.getUserByToken(token);

        ReadingPlanPageDTO pageDTO = planService.findAllPlans(user, filter);

        return ResponseEntity.ok(pageDTO);
    }

    public ResponseEntity<ReadingPlanPageDTO> getPublicPlans(ReadingPlanFilter filter) {

        ReadingPlanPageDTO pageDTO = planService.findAllPlans(null, filter);

        return ResponseEntity.ok(pageDTO);
    }

    public ResponseEntity<PlanResponseDTO> createPlan(PlanRequestDTO requestDTO, JwtAuthenticationToken token) {

        User user = authService.getUserByToken(token);

        PlanResponseDTO responseDTO = planService.createReadingPlan(requestDTO, user);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    public ResponseEntity<PlanResponseDTO> updatePrivacyOfPlan(PrivacyPlanDTO requestDTO, JwtAuthenticationToken token) {

        User user = authService.getUserByToken(token);

        PlanResponseDTO responseDTO = planService.updatePrivacyFromPlan(requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<PlanResponseDTO> updatePlan(long id, PlanRequestDTO requestDTO, JwtAuthenticationToken token) {

        User user = authService.getUserByToken(token);

        PlanResponseDTO responseDTO = planService.updateReadingPlan(id, requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<Void> deletePlan(long id, JwtAuthenticationToken token) {

        User user = authService.getUserByToken(token);

        planService.deletePlan(id, user);

        return ResponseEntity.ok(null);
    }

    public ResponseEntity<PlanResponseDTO> duplicatePlan(long id, JwtAuthenticationToken token) {

        User user = authService.getUserByToken(token);

        PlanResponseDTO responseDTO = planService.duplicatePlanToUser(id, user);

        return ResponseEntity.ok(responseDTO);
    }
}
