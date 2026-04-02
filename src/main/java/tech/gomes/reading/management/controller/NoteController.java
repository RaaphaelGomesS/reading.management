package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;
import tech.gomes.reading.management.controller.doc.NoteControllerDoc;
import tech.gomes.reading.management.controller.filter.NoteFilter;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.note.NoteFullResponseDTO;
import tech.gomes.reading.management.dto.note.NoteRequestDTO;
import tech.gomes.reading.management.dto.note.NoteResponseDTO;
import tech.gomes.reading.management.dto.note.NoteResponsePageDTO;
import tech.gomes.reading.management.service.AuthService;
import tech.gomes.reading.management.service.NoteService;

@RestController
@RequiredArgsConstructor
public class NoteController implements NoteControllerDoc {

    private final AuthService authService;

    private final NoteService noteService;

    public ResponseEntity<NoteResponsePageDTO> getAllNotesByFilter(NoteFilter filter, JwtAuthenticationToken token) {
        User user = authService.getUserByToken(token);

        filter.setUserId(user.getId());

        NoteResponsePageDTO responseDTO = noteService.findAllNotesByFilter(filter);

        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<NoteResponsePageDTO> getAllLinksToNote(long id,
                                                                 int page,
                                                                 int pageSize,
                                                                 String direction,
                                                                 JwtAuthenticationToken token) {
        User user = authService.getUserByToken(token);

        NoteResponsePageDTO responseDTOS = noteService.findAllNotesThatCallTheCurrent(id, user, page, pageSize, direction);

        return ResponseEntity.ok(responseDTOS);
    }

    public ResponseEntity<NoteResponsePageDTO> getAllLinksFromNote(long id,
                                                                   int page,
                                                                   int pageSize,
                                                                   String direction,
                                                                   JwtAuthenticationToken token) {
        User user = authService.getUserByToken(token);

        NoteResponsePageDTO responseDTOS = noteService.findAllNotesThatAreCalledByTheCurrent(id, user, page, pageSize, direction);

        return ResponseEntity.ok(responseDTOS);
    }

    public ResponseEntity<NoteFullResponseDTO> getNoteById(long id, JwtAuthenticationToken token) {
        User user = authService.getUserByToken(token);

        NoteFullResponseDTO responseDTO = noteService.findNoteByIdWithSummaryLinkedNotes(id, user);

        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<NoteResponseDTO> createNote(JwtAuthenticationToken token) {
        User user = authService.getUserByToken(token);

        NoteResponseDTO responseDTO = noteService.createEmptyNote(user);

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    public ResponseEntity<NoteResponseDTO> updateNote(NoteRequestDTO requestDTO, JwtAuthenticationToken token) {
        User user = authService.getUserByToken(token);

        NoteResponseDTO responseDTO = noteService.updateNoteAndLinks(requestDTO, user);

        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<Void> deleteNote(long id, JwtAuthenticationToken token) {
        User user = authService.getUserByToken(token);

        noteService.deleteNoteById(id, user);

        return ResponseEntity.ok(null);
    }
}
