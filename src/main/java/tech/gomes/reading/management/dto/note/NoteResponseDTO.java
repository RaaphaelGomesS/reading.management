package tech.gomes.reading.management.dto.note;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NoteResponseDTO(long id, String title, String category, String type, long bookReference, LocalDateTime createdDate, LocalDateTime updatedDate) {
}
