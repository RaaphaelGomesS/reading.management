package tech.gomes.reading.management.dto.book.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import java.time.Instant;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record BookRequestDTO(long id,
                             String status,
                             int pages,
                             int rating,
                             Instant startedDate,
                             Instant finishedDate,
                             long libraryId) {
}
