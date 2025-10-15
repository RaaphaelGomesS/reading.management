package tech.gomes.reading.management.dto.note;

import lombok.Builder;
import tech.gomes.reading.management.indicator.NoteTypeIndicator;

import java.time.Instant;

@Builder
public record NoteDTO (long id, String title, String category, NoteTypeIndicator type, long bookReference, Instant createdDate, Instant updatedDate) {
}
