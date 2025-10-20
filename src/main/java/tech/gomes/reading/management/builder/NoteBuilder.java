package tech.gomes.reading.management.builder;

import tech.gomes.reading.management.domain.Book;
import tech.gomes.reading.management.domain.Note;
import tech.gomes.reading.management.domain.NoteCategory;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.note.NoteRequestDTO;
import tech.gomes.reading.management.indicator.NoteTypeIndicator;

import java.time.Instant;
import java.util.List;
import java.util.Set;

public class NoteBuilder {

    public static Note from(NoteRequestDTO requestDTO, User user, Book book, NoteCategory category) {
        return Note.builder()
                .title(requestDTO.title())
                .content(requestDTO.content())
                .type(NoteTypeIndicator.getTypeByName(requestDTO.type()))
                .createdAt(requestDTO.createDate() == null ? Instant.now() : requestDTO.createDate().toInstant())
                .category(category)
                .book(book)
                .user(user)
                .build();
    }

    public static void from(Note note, NoteRequestDTO requestDTO, Book book, NoteCategory category, Set<Note> linkedNotes) {
        note.setBook(book);
        note.setCategory(category);
        note.setTitle(requestDTO.title());
        note.setContent(requestDTO.content());
        note.setType(NoteTypeIndicator.getTypeByName(requestDTO.type()));
        note.getLinkedNotes().clear();
        note.getLinkedNotes().addAll(linkedNotes);
        note.setUpdatedAt(Instant.now());
    }
}
