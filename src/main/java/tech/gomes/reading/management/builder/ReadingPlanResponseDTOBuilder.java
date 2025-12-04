package tech.gomes.reading.management.builder;

import org.springframework.data.domain.Page;
import tech.gomes.reading.management.dto.readingPlan.PlanSummaryDTO;
import tech.gomes.reading.management.dto.readingPlan.ReadingPlanPageDTO;
import tech.gomes.reading.management.repository.projections.PlanSummary;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ReadingPlanResponseDTOBuilder {

    public static ReadingPlanPageDTO fromPageSummary(Page<PlanSummary> summaryPage) {

        List<PlanSummaryDTO> summaryDTOList = summaryPage.getContent().isEmpty() ? Collections.emptyList()
                : summaryPage.getContent().stream().map(ReadingPlanResponseDTOBuilder::fromProjection).collect(Collectors.toList());

        return ReadingPlanPageDTO.builder()
                .page(summaryPage.getNumber())
                .pageSize(summaryPage.getSize())
                .totalPages(summaryPage.getTotalPages())
                .totalElements(summaryPage.getNumberOfElements())
                .data(summaryDTOList)
                .build();
    }

    private static PlanSummaryDTO fromProjection(PlanSummary summary) {
        return new PlanSummaryDTO(summary.getId(), summary.getTitle(), summary.getDescription());
    }
}
