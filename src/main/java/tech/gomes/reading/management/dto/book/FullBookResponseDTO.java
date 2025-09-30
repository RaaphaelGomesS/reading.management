package tech.gomes.reading.management.dto.book;

import lombok.Builder;

@Builder
public record FullBookResponseDTO(BookResponseDTO book, BookTemplateResponseDTO template) {
}
