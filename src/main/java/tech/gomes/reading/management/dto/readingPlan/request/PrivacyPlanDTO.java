package tech.gomes.reading.management.dto.readingPlan.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PrivacyPlanDTO(@NotNull long id,
                             @NotNull boolean privacyStatus) {
}
