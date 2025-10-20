package tech.gomes.reading.management.dto.book.response;

import lombok.Builder;

@Builder
public record BookResponseDTO(long id,
                              String img,
                              String title,
                              String author,
                              String status,
                              int pages,
                              int rating,
                              String startedDate,
                              String finishedDate) {
}
