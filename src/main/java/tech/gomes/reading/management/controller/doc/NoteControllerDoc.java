package tech.gomes.reading.management.controller.doc;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tech.gomes.reading.management.controller.filter.NoteFilter;
import tech.gomes.reading.management.dto.note.NoteFullResponseDTO;
import tech.gomes.reading.management.dto.note.NoteRequestDTO;
import tech.gomes.reading.management.dto.note.NoteResponseDTO;
import tech.gomes.reading.management.dto.note.NoteResponsePageDTO;

@RequestMapping("/note")
public interface NoteControllerDoc {

    @GetMapping("/")
    ResponseEntity<NoteResponsePageDTO> getAllNotesByFilter(NoteFilter filter, JwtAuthenticationToken token);

    @GetMapping("/reversed/")
    ResponseEntity<NoteResponsePageDTO> getAllLinksToNote(@RequestParam(value = "id") long id,
                                                                 @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                 @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                 @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                                 JwtAuthenticationToken token);

    @GetMapping("/linked/")
    ResponseEntity<NoteResponsePageDTO> getAllLinksFromNote(@RequestParam(value = "id") long id,
                                                                   @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                   @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                                   JwtAuthenticationToken token);

    @GetMapping("/{id}")
    ResponseEntity<NoteFullResponseDTO> getNoteById(@PathVariable long id, JwtAuthenticationToken token);

    @PostMapping("/")
    ResponseEntity<NoteResponseDTO> createNote(JwtAuthenticationToken token);

    @PutMapping("/")
    ResponseEntity<NoteResponseDTO> updateNote(@RequestBody NoteRequestDTO requestDTO, JwtAuthenticationToken token);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteNote(@PathVariable long id, JwtAuthenticationToken token);
}
