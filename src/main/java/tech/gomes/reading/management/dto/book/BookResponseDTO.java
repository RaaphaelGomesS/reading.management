package tech.gomes.reading.management.dto.book;

import lombok.Builder;

import java.util.Date;

@Builder
public record BookResponseDTO(long id,
                              String status,
                              int pages,
                              int rating,
                              Date startedDate,
                              Date finishedDate) {
}
