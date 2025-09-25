package tech.gomes.reading.management.dto.library;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LibraryRequestDTO {

    String name;

    String description;
}
