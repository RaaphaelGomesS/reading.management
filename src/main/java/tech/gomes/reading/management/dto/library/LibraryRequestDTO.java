package tech.gomes.reading.management.dto.library;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record LibraryRequestDTO (String name, String description) {
}
