package tech.gomes.reading.management.dto.suggestion.response;

import java.util.List;

public record SuggestionResponsePageDTO(int page, int pageSize, int totalPages, int totalElements,
                                        List<SuggestionResponseDTO> data) {


}
