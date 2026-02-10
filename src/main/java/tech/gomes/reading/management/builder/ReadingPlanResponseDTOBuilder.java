package tech.gomes.reading.management.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import tech.gomes.reading.management.domain.ReadingPlan;
import tech.gomes.reading.management.dto.readingPlan.PlanSummaryDTO;
import tech.gomes.reading.management.dto.readingPlan.ReadingPlanPageDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReadingPlanResponseDTOBuilder {

    public static ReadingPlanPageDTO fromPageReadingPlan(Page<ReadingPlan> plans) {

        List<PlanSummaryDTO> summaryDTOList = plans.getContent().isEmpty() ? Collections.emptyList()
                : plans.getContent().stream().map(ReadingPlanResponseDTOBuilder::fromReadingPlan).collect(Collectors.toList());

        return ReadingPlanPageDTO.builder()
                .page(plans.getNumber())
                .pageSize(plans.getSize())
                .totalPages(plans.getTotalPages())
                .totalElements(plans.getNumberOfElements())
                .data(summaryDTOList)
                .build();
    }

    private static PlanSummaryDTO fromReadingPlan(ReadingPlan plan) {
        return new PlanSummaryDTO(plan.getId(), plan.getTitle(), plan.getDescription());
    }
}
