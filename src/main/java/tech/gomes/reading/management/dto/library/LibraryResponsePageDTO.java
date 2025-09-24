package tech.gomes.reading.management.dto.library;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibraryResponsePageDTO {

    private int page;

    private int pageSize;

    private int totalPages;

    private int totalElements;

    private List<LibraryResponseDTO> libraries;
}
