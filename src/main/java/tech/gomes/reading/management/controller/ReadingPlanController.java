package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tech.gomes.reading.management.controller.filter.ReadingPlanFilter;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.readingPlan.ReadingPlanPageDTO;
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
}
