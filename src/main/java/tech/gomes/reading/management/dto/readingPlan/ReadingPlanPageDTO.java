package tech.gomes.reading.management.dto.readingPlan;

import lombok.Builder;

import java.util.List;

@Builder
public record ReadingPlanPageDTO(int page,
                                 int pageSize,
                                 int totalPages,
                                 int totalElements,
                                 List<PlanSummaryDTO> data) {
}
