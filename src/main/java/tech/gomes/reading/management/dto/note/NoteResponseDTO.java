package tech.gomes.reading.management.dto.note;

import lombok.Builder;

@Builder
public record NoteResponseDTO(long id,
                              String title,
                              String category,
                              String type,
                              long bookReference,
                              String createdDate,
                              String updatedDate) {
}
