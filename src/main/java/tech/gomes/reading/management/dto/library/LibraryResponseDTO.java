package tech.gomes.reading.management.dto.library;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibraryResponseDTO {

    private Long id;

    private String name;

    private String description;
}
