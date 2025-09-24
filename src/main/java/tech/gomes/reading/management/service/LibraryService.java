package tech.gomes.reading.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tech.gomes.reading.management.builder.LibraryResponseDTOBuilder;
import tech.gomes.reading.management.domain.Library;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.library.LibraryResponseDTO;
import tech.gomes.reading.management.dto.library.LibraryResponsePageDTO;
import tech.gomes.reading.management.exception.LibraryException;
import tech.gomes.reading.management.repository.LibraryRepository;

@Service
@RequiredArgsConstructor
public class LibraryService {

    private final LibraryRepository libraryRepository;

    public LibraryResponsePageDTO getALlLibraries(User user, int page, int pageSize, String direction) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.valueOf(direction), "updated_at");

        Page<Library> libraryPage = libraryRepository.findByUserId(user.getId(), pageable);

        return LibraryResponseDTOBuilder.fromPage(libraryPage);
    }

    public LibraryResponseDTO getLibraryById(long id, User user) throws Exception {

        Library library = libraryRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new LibraryException("A biblioteca não foi encontrada.", HttpStatus.NOT_FOUND));

        return LibraryResponseDTOBuilder.from(library);
    }
}
