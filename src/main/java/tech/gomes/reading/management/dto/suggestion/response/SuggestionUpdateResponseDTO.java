package tech.gomes.reading.management.dto.suggestion.response;

import tech.gomes.reading.management.dto.suggestion.request.SuggestionRequestDTO;

public record SuggestionUpdateResponseDTO (SuggestionResponseDTO updated, SuggestionRequestDTO template) {
}
