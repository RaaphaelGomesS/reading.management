package tech.gomes.reading.management.dto.readingPlan.response;

import lombok.Builder;

@Builder
public record BookPlanResponseDTO(Long templateId, String description) {
}
