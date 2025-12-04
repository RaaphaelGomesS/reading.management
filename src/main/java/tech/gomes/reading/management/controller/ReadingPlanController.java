package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public ResponseEntity<ReadingPlanPageDTO> getUserPlans(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                           @RequestParam(value = "direction", required = false, defaultValue = "ASC") String direction,
                                                           JwtAuthenticationToken token) throws Exception {

        User user = authService.getUserByToken(token);

        ReadingPlanPageDTO pageDTO = planService.findAllUserPlans(user, page, pageSize, direction);

        return ResponseEntity.ok(pageDTO);
    }
}
