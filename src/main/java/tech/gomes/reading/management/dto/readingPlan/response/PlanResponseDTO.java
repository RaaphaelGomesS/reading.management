package tech.gomes.reading.management.dto.readingPlan.response;

import lombok.Builder;

import java.util.Map;
import java.util.Set;

@Builder
public record PlanResponseDTO(String title,
                              String description,
                              Map<Integer, BookPlanResponseDTO> positionTemplate,
                              Set<Long> categoriesIds,
                              Boolean isPublic,
                              String goalDate) {
}
