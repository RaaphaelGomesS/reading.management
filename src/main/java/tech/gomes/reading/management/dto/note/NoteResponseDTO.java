package tech.gomes.reading.management.dto.note;

import lombok.Builder;

import java.time.Instant;

@Builder
public record NoteResponseDTO(String title, String category, String type, long bookReference, Instant createdDate, Instant updatedDate) {
}
