package tech.gomes.reading.management.dto.suggestion.request;


import java.util.List;

public record SuggestionRequestDTO(Long templateId,
                                   String suggestedISBN,
                                   String suggestedTitle,
                                   String suggestedAuthor,
                                   String suggestedPublisher,
                                   String suggestedEdition,
                                   String suggestedDescription,
                                   int suggestedYear,
                                   int suggestedPages,
                                   List<String> suggestedCategories) {
}
