package tech.gomes.reading.management.dto.book.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;

import java.util.Date;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record BookRequestDTO(long id,
                             String status,
                             int pages,
                             int rating,
                             Date startedDate,
                             Date finishedDate,
                             long libraryId) {
}
