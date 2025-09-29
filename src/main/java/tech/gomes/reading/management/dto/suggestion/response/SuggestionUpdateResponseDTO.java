package tech.gomes.reading.management.dto.suggestion.response;

import lombok.Builder;

@Builder
public record SuggestionUpdateResponseDTO (SuggestionResponseDTO updated, SuggestionResponseDTO template) {
}
