package tech.gomes.reading.management.dto.note;

import lombok.Builder;

import java.util.Date;
import java.util.List;

@Builder
public record NoteFullResponseDTO(long id,
                                  String title,
                                  String content,
                                  String category,
                                  String type,
                                  long bookReference,
                                  Date createdDate,
                                  List<NoteSummaryDTO> linkedNotes) {
}
