package tech.gomes.reading.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import tech.gomes.reading.management.builder.ReadingPlanResponseDTOBuilder;
import tech.gomes.reading.management.controller.filter.ReadingPlanFilter;
import tech.gomes.reading.management.domain.ReadingPlan;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.readingPlan.ReadingPlanPageDTO;
import tech.gomes.reading.management.repository.ReadingPlanRepository;
import tech.gomes.reading.management.repository.specification.ReadingPlanSpecification;

@Service
@RequiredArgsConstructor
public class ReadingPlanService {

    private final ReadingPlanRepository repository;

    public ReadingPlanPageDTO findAllPlans(User user, ReadingPlanFilter filter) {

        Specification<ReadingPlan> spec = ReadingPlanSpecification.byFilter(user, filter);

        Page<ReadingPlan> plans = repository.findAll(spec, getPageable(filter));

        return ReadingPlanResponseDTOBuilder.fromPageReadingPlan(plans);
    }

    private Pageable getPageable(ReadingPlanFilter filter) {
        Sort sort = Sort.by(Sort.Direction.valueOf(filter.getDirection()), filter.getType().getValue());

        return PageRequest.of(filter.getPage(), filter.getPageSize(), sort);
    }
}