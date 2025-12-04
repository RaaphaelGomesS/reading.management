package tech.gomes.reading.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tech.gomes.reading.management.builder.ReadingPlanResponseDTOBuilder;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.readingPlan.ReadingPlanPageDTO;
import tech.gomes.reading.management.repository.ReadingPlanRepository;
import tech.gomes.reading.management.repository.projections.PlanSummary;

@Service
@RequiredArgsConstructor
public class ReadingPlanService {

    private final ReadingPlanRepository repository;

    public ReadingPlanPageDTO findAllUserPlans(User user, int page, int pageSize, String direction) {

        Sort sort = Sort.by(Sort.Direction.valueOf(direction), "createdAt");

        Pageable pageable = PageRequest.of(page, pageSize, sort);

        Page<PlanSummary> plans = repository.findAllSummaryUserPlans(user.getId(), pageable);

        return ReadingPlanResponseDTOBuilder.fromPageSummary(plans);
    }
}
