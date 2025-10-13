package tech.gomes.reading.management.builder;

import tech.gomes.reading.management.domain.Note;
import tech.gomes.reading.management.dto.note.NoteFullResponseDTO;
import tech.gomes.reading.management.dto.note.NoteSummaryDTO;

import java.util.Date;
import java.util.List;

public class NoteResponseDTOBuilder {

    public static NoteFullResponseDTO from(Note note, List<NoteSummaryDTO> linkedNotes) {
        return NoteFullResponseDTO.builder()
                .id(note.getId())
                .title(note.getTitle())
                .type(note.getType().name())
                .category(note.getCategory().getName())
                .bookReference(note.getBook().getId())
                .content(note.getContent())
                .createdDate(Date.from(note.getCreatedAt()))
                .linkedNotes(linkedNotes)
                .build();
    }
}
