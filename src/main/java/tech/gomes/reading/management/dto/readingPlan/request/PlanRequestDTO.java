package tech.gomes.reading.management.dto.readingPlan.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PlanRequestDTO(String title,
                             String description,
                             Set<BookTemplatePlanDTO> booksTemplates,
                             Set<Long> categories,
                             Boolean isPublic,
                             String goalDate) {
}
