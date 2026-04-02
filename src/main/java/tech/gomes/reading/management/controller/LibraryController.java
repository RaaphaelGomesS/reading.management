package tech.gomes.reading.management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.RestController;
import tech.gomes.reading.management.builder.LibraryResponseDTOBuilder;
import tech.gomes.reading.management.controller.doc.LibraryControllerDoc;
import tech.gomes.reading.management.domain.Library;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.library.LibraryRequestDTO;
import tech.gomes.reading.management.dto.library.LibraryResponseDTO;
import tech.gomes.reading.management.dto.library.LibraryResponsePageDTO;
import tech.gomes.reading.management.exception.ApplicationException;
import tech.gomes.reading.management.service.AuthService;
import tech.gomes.reading.management.service.BookService;
import tech.gomes.reading.management.service.LibraryService;

@RestController
@RequiredArgsConstructor
public class LibraryController implements LibraryControllerDoc {

    private final LibraryService libraryService;

    private final AuthService authService;

    private final BookService bookService;

    public ResponseEntity<LibraryResponsePageDTO> getAllLibrariesByUserId(int page,
                                                                          int pageSize,
                                                                          String direction,
                                                                          JwtAuthenticationToken token) throws ApplicationException {

        User user = authService.getUserByToken(token);

        return ResponseEntity.ok(libraryService.getALlLibraries(user, page, pageSize, direction));
    }

    public ResponseEntity<LibraryResponseDTO> findLibraryById(Long id, JwtAuthenticationToken token) throws ApplicationException {

        User user = authService.getUserByToken(token);

        Library library = libraryService.getLibraryById(id, user);

        return ResponseEntity.ok(LibraryResponseDTOBuilder.from(library));
    }

    public ResponseEntity<LibraryResponseDTO> createNewLibrary(LibraryRequestDTO requestDTO, JwtAuthenticationToken token) throws ApplicationException {

        User user = authService.getUserByToken(token);

        LibraryResponseDTO responseDTO = LibraryResponseDTOBuilder.from(libraryService.createLibrary(requestDTO, user));

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    public ResponseEntity<LibraryResponseDTO> updateLibraryById(LibraryRequestDTO requestDTO, JwtAuthenticationToken token) throws ApplicationException {

        User user = authService.getUserByToken(token);

        return ResponseEntity.ok(libraryService.updateLibrary(requestDTO, user));
    }

    public ResponseEntity<Void> deleteLibraryById(Long id, JwtAuthenticationToken token) throws ApplicationException {

        User user = authService.getUserByToken(token);

        libraryService.deleteLibrary(id, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<LibraryResponseDTO> createLibraryByPlan(Long id, JwtAuthenticationToken token) throws ApplicationException {

        User user = authService.getUserByToken(token);

        LibraryResponseDTO responseDTO = bookService.createLibraryAndIndexBookFromPlan(id, user);

        return ResponseEntity.ok(responseDTO);
    }
}
