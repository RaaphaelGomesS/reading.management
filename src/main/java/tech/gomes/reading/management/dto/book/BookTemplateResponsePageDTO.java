package tech.gomes.reading.management.dto.book;

import lombok.Builder;

import java.util.List;

@Builder
public record BookTemplateResponsePageDTO(int page, int pageSize, int totalPages, int totalElements,
                                          List<BookTemplateResponseDTO> data) {
}
