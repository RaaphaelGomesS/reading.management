package tech.gomes.reading.management.builder;

import org.springframework.data.domain.Page;
import tech.gomes.reading.management.domain.Note;
import tech.gomes.reading.management.dto.note.NoteFullResponseDTO;
import tech.gomes.reading.management.dto.note.NoteResponseDTO;
import tech.gomes.reading.management.dto.note.NoteResponsePageDTO;
import tech.gomes.reading.management.dto.note.NoteSummaryDTO;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
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

    public static NoteResponseDTO from(Note note) {
        return NoteResponseDTO.builder()
                .id(note.getId())
                .title(note.getTitle())
                .bookReference(note.getBook().getId())
                .category(note.getCategory().getName())
                .createdDate(LocalDateTime.ofInstant(note.getCreatedAt(), ZoneId.of("America/Sao_Paulo")))
                .updatedDate(LocalDateTime.ofInstant(note.getUpdatedAt(), ZoneId.of("America/Sao_Paulo")))
                .build();
    }

    public static NoteResponsePageDTO from(Page<NoteResponseDTO> notePage) {
        return NoteResponsePageDTO.builder()
                .page(notePage.getNumber())
                .pageSize(notePage.getSize())
                .totalPages(notePage.getTotalPages())
                .totalElements(notePage.getNumberOfElements())
                .data(notePage.getContent())
                .build();
    }

    public static NoteResponsePageDTO fromPage(Page<Note> notePage) {

        List<NoteResponseDTO> responseDTOList = notePage.getContent().isEmpty() ?
                notePage.getContent().stream().map(NoteResponseDTOBuilder::from).toList() : Collections.emptyList();

        return NoteResponsePageDTO.builder()
                .page(notePage.getNumber())
                .pageSize(notePage.getSize())
                .totalPages(notePage.getTotalPages())
                .totalElements(notePage.getNumberOfElements())
                .data(responseDTOList)
                .build();
    }
}
