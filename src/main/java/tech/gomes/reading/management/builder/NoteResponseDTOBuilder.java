package tech.gomes.reading.management.builder;

import org.springframework.data.domain.Page;
import tech.gomes.reading.management.domain.Note;
import tech.gomes.reading.management.dto.note.*;
import tech.gomes.reading.management.utils.DateUtils;

import java.util.Collections;
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
                .createdDate(DateUtils.formatInstantToDateTime(note.getCreatedAt()))
                .linkedNotes(linkedNotes)
                .build();
    }

    public static NoteResponseDTO from(Note note) {
        return NoteResponseDTO.builder()
                .id(note.getId())
                .title(note.getTitle())
                .bookReference(note.getBook().getId())
                .category(note.getCategory().getName())
                .createdDate(DateUtils.formatInstantToDateTime(note.getCreatedAt()))
                .updatedDate(DateUtils.formatInstantToDateTime(note.getUpdatedAt()))
                .build();
    }

    public static NoteResponseDTO from(NoteDTO note) {
        return NoteResponseDTO.builder()
                .id(note.id())
                .title(note.title())
                .bookReference(note.bookReference())
                .category(note.category())
                .createdDate(DateUtils.formatInstantToDateTime(note.createdDate()))
                .updatedDate(DateUtils.formatInstantToDateTime(note.updatedDate()))
                .build();
    }

    public static NoteResponsePageDTO from(Page<NoteDTO> notePage) {

        List<NoteResponseDTO> responseDTOList = notePage.getContent().isEmpty() ?
                Collections.emptyList() : notePage.getContent().stream().map(NoteResponseDTOBuilder::from).toList();

        return NoteResponsePageDTO.builder()
                .page(notePage.getNumber())
                .pageSize(notePage.getSize())
                .totalPages(notePage.getTotalPages())
                .totalElements(notePage.getNumberOfElements())
                .data(responseDTOList)
                .build();
    }

    public static NoteResponsePageDTO fromPage(Page<Note> notePage) {

        List<NoteResponseDTO> responseDTOList = notePage.getContent().isEmpty() ?
                Collections.emptyList() : notePage.getContent().stream().map(NoteResponseDTOBuilder::from).toList();

        return NoteResponsePageDTO.builder()
                .page(notePage.getNumber())
                .pageSize(notePage.getSize())
                .totalPages(notePage.getTotalPages())
                .totalElements(notePage.getNumberOfElements())
                .data(responseDTOList)
                .build();
    }
}
