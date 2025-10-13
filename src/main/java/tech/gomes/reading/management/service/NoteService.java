package tech.gomes.reading.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tech.gomes.reading.management.builder.NoteResponseDTOBuilder;
import tech.gomes.reading.management.domain.Note;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.note.NoteFullResponseDTO;
import tech.gomes.reading.management.dto.note.NoteRequestDTO;
import tech.gomes.reading.management.dto.note.NoteResponseDTO;
import tech.gomes.reading.management.dto.note.NoteSummaryDTO;
import tech.gomes.reading.management.exception.NoteException;
import tech.gomes.reading.management.repository.NoteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteFullResponseDTO findNoteByIdWithSummaryLinkedNotes(long id, User user) throws Exception {

        Note note = noteRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new NoteException("Não foi encontrado a anotação.", HttpStatus.NOT_FOUND));

        List<NoteSummaryDTO> linkedSummaryNotes = noteRepository.findAllLinkedNotes(id);

        return NoteResponseDTOBuilder.from(note, linkedSummaryNotes);
    }

    public NoteResponseDTO createOrUpdateNoteAndSetLinks(NoteRequestDTO requestDTO, User user) {
        return null;
    }

    private List<String> extractLinks(String content) {
        List<String> links = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\[\\[(.*?)\\]\\]");
        Matcher matcher = pattern.matcher(content);

        while (matcher.find()) {
            links.add(matcher.group(1));
        }

        return links;
    }
}
