package tech.gomes.reading.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tech.gomes.reading.management.builder.NoteBuilder;
import tech.gomes.reading.management.builder.NoteResponseDTOBuilder;
import tech.gomes.reading.management.domain.Book;
import tech.gomes.reading.management.domain.Note;
import tech.gomes.reading.management.domain.NoteCategory;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.note.*;
import tech.gomes.reading.management.exception.NoteException;
import tech.gomes.reading.management.repository.NoteCategoryRepository;
import tech.gomes.reading.management.repository.NoteRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    private final BookService bookService;

    private final NoteCategoryRepository categoryRepository;

    public NoteFullResponseDTO findNoteByIdWithSummaryLinkedNotes(long id, User user) throws Exception {

        Note note = noteRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new NoteException("Não foi encontrado a anotação.", HttpStatus.NOT_FOUND));

        List<NoteSummaryDTO> linkedSummaryNotes = noteRepository.findAllSummaryTargetNotes(id);

        return NoteResponseDTOBuilder.from(note, linkedSummaryNotes);
    }

    public NoteResponsePageDTO findAllLinksFromNote(long id, User user, int page, int pageSize, String direction) throws Exception {

        if (noteRepository.existsByIdAndUserId(id, user.getId())) {
            throw new NoteException("Não foi encontrada a anotação.", HttpStatus.NOT_FOUND);
        }

        Sort sort = Sort.by(Sort.Direction.valueOf(direction), "title");

        Pageable pageable = PageRequest.of(page, pageSize, sort);

        Page<NoteResponseDTO> responseDTOPage = noteRepository.findAllTargetNotesFromSource(id, pageable);

        return NoteResponseDTOBuilder.from(responseDTOPage);
    }

    public NoteResponsePageDTO findAllLinksToNote(long id, User user, int page, int pageSize, String direction) throws Exception {
        if (noteRepository.existsByIdAndUserId(id, user.getId())) {
            throw new NoteException("Não foi encontrada a anotação.", HttpStatus.NOT_FOUND);
        }

        Sort sort = Sort.by(Sort.Direction.valueOf(direction), "title");

        Pageable pageable = PageRequest.of(page, pageSize, sort);

        Page<NoteResponseDTO> responseDTOPage = noteRepository.findAllSourceNotesFromTarget(id, pageable);

        return NoteResponseDTOBuilder.from(responseDTOPage);
    }

    public NoteResponseDTO createNoteAndSetLinks(NoteRequestDTO requestDTO, User user) throws Exception {

        if (noteRepository.existsByTitleAndUserId(requestDTO.title(), user.getId())) {
            throw new NoteException("Já existe uma anotação com esse título.", HttpStatus.BAD_REQUEST);
        }

        Set<String> linksTitles = extractLinks(requestDTO.content());

        List<Note> linkedNotes = noteRepository.findAllByTitleInAndUserId(linksTitles, user.getId());

        Book book = requestDTO.reference() == null ? null : bookService.findBookById(requestDTO.reference(), user.getId());

        NoteCategory category = takeCategoryOrCreateIfNotExists(requestDTO.category(), user);

        Note newNote = NoteBuilder.from(requestDTO, user, book, category);

        Note note = noteRepository.save(newNote);

        note.getLinkedNotes().addAll(linkedNotes);

        return NoteResponseDTOBuilder.from(note);
    }

    public NoteResponseDTO updateNoteAndLinks(NoteRequestDTO requestDTO, User user) throws Exception {
        if (noteRepository.existsByTitleAndUserId(requestDTO.title(), user.getId())) {
            throw new NoteException("Já existe uma anotação com esse título.", HttpStatus.BAD_REQUEST);
        }

        Note note = findNoteById(requestDTO.id(), user.getId());

        Set<String> linksTitles = extractLinks(requestDTO.content());

        List<Note> linkedNotes = noteRepository.findAllByTitleInAndUserId(linksTitles, user.getId());

        Book book = requestDTO.reference() == null ? null : bookService.findBookById(requestDTO.reference(), user.getId());

        NoteCategory category = takeCategoryOrCreateIfNotExists(requestDTO.category(), user);

        NoteBuilder.from(note, requestDTO, book, category, linkedNotes);

        Note updatedNote = noteRepository.save(note);

        return NoteResponseDTOBuilder.from(updatedNote);
    }

    private NoteCategory takeCategoryOrCreateIfNotExists(String categoryName, User user) {
        NoteCategory category = categoryRepository.findByNameAndUserId(categoryName, user.getId()).orElse(null);

        if (category == null) {
            NoteCategory newCategory = NoteCategory.builder().name(categoryName).user(user).build();
            return categoryRepository.save(newCategory);
        }

        return category;
    }

    public void DeleteNoteById(long id, User user) throws Exception {
        Note note = findNoteById(id, user.getId());

        noteRepository.delete(note);
    }

    private Note findNoteById(long id, long userId) throws Exception {
        return noteRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new NoteException("A anotação não foi encontrada.", HttpStatus.NOT_FOUND));
    }

    private Set<String> extractLinks(String content) {
        Set<String> links = new HashSet<>();
        Pattern pattern = Pattern.compile("\\[\\[(.*?)\\]\\]");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            links.add(matcher.group(1));
        }

        return links;
    }
}
