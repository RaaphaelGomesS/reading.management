package tech.gomes.reading.management.dto.book;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Date;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record BookRequestDTO(@NotBlank
                             String status,
                             int pages,
                             int rating,
                             Date startedDate,
                             Date finishedDate,
                             @NotNull
                             BookTemplateRequestDTO template) {
}
