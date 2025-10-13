package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.note.NoteFullResponseDTO;
import tech.gomes.reading.management.dto.note.NoteRequestDTO;
import tech.gomes.reading.management.dto.note.NoteResponsePageDTO;
import tech.gomes.reading.management.service.AuthService;
import tech.gomes.reading.management.service.NoteService;

@RestController
@RequestMapping("/note")
@RequiredArgsConstructor
public class NoteController {

    private final AuthService authService;

    private final NoteService noteService;

    @GetMapping("/")
    public ResponseEntity<NoteResponsePageDTO> getAllNotesByFilter(JwtAuthenticationToken token) throws Exception {
        User user = authService.getUserByToken(token);

        return ResponseEntity.ok(null);
    }

    @GetMapping("/linked/{id}")
    public ResponseEntity<NoteResponsePageDTO> getAllLinkedNotes(@RequestParam long id, JwtAuthenticationToken token) throws Exception {
        User user = authService.getUserByToken(token);

        return ResponseEntity.ok(null);
    }

    @GetMapping("/reversed/{id}")
    public ResponseEntity<NoteResponsePageDTO> getAllReversedLinked(@RequestParam long id, JwtAuthenticationToken token) throws Exception {
        User user = authService.getUserByToken(token);

        return ResponseEntity.ok(null);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteFullResponseDTO> getNoteById(@RequestParam long id, JwtAuthenticationToken token) throws Exception {
        User user = authService.getUserByToken(token);

        NoteFullResponseDTO responseDTO = noteService.findNoteByIdWithSummaryLinkedNotes(id, user);

        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/")
    public ResponseEntity<?> createOrUpdateNote(@RequestBody NoteRequestDTO requestDTO, JwtAuthenticationToken token) throws Exception {
        User user = authService.getUserByToken(token);

        if (requestDTO.id() == null) {
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        }

        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNote(@RequestParam long id, JwtAuthenticationToken token) throws Exception {
        User user = authService.getUserByToken(token);

        return ResponseEntity.ok(null);
    }
}
