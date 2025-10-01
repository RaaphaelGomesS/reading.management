package tech.gomes.reading.management.dto.book;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Date;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record BookRequestDTO(String status,
                             int pages,
                             int rating,
                             Date startedDate,
                             Date finishedDate,
                             long libraryId,
                             @NotNull
                             BookTemplateRequestDTO template) {
}
