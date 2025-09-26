package tech.gomes.reading.management.dto.suggestion.response;

import java.util.List;

public record SuggestionUpdatedResponsePageDTO (int page, int pageSize, int totalPages, int totalElements,
                                                List<SuggestionUpdateResponseDTO> data) {
}
