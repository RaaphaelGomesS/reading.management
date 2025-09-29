package tech.gomes.reading.management.dto.library;

import lombok.*;

@Builder
public record LibraryRequestDTO (String name, String description) {
}
