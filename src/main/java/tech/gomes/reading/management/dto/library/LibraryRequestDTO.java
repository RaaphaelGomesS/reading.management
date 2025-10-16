package tech.gomes.reading.management.dto.library;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibraryRequestDTO(long id, String name, String description) {
}
