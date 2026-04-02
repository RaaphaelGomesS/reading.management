package tech.gomes.reading.management.controller.doc;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import tech.gomes.reading.management.dto.library.LibraryRequestDTO;
import tech.gomes.reading.management.dto.library.LibraryResponseDTO;
import tech.gomes.reading.management.dto.library.LibraryResponsePageDTO;
import tech.gomes.reading.management.exception.ApplicationException;

@RequestMapping("/library")
public interface LibraryControllerDoc {

    @GetMapping("/")
    ResponseEntity<LibraryResponsePageDTO> getAllLibrariesByUserId(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                                                   @RequestParam(value = "direction", required = false, defaultValue = "DESC") String direction,
                                                                   JwtAuthenticationToken token) throws ApplicationException;

    @GetMapping("/{id}")
    ResponseEntity<LibraryResponseDTO> findLibraryById(@PathVariable Long id, JwtAuthenticationToken token) throws ApplicationException;

    @PostMapping("/")
    ResponseEntity<LibraryResponseDTO> createNewLibrary(@RequestBody LibraryRequestDTO requestDTO, JwtAuthenticationToken token) throws ApplicationException;

    @PutMapping("/")
    ResponseEntity<LibraryResponseDTO> updateLibraryById(@RequestBody LibraryRequestDTO requestDTO, JwtAuthenticationToken token) throws ApplicationException;

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteLibraryById(@PathVariable Long id, JwtAuthenticationToken token) throws ApplicationException;

    @PostMapping("/new/library/{id}")
    ResponseEntity<LibraryResponseDTO> createLibraryByPlan(@PathVariable Long id, JwtAuthenticationToken token) throws ApplicationException;
}
