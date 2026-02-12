package tech.gomes.reading.management.dto.readingPlan.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookTemplatePlanDTO(@NotNull Long templateId,
                                  @NotNull Integer position,
                                  String description) {

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookTemplatePlanDTO that)) return false;
        return java.util.Objects.equals(templateId, that.templateId);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(templateId);
    }
}
