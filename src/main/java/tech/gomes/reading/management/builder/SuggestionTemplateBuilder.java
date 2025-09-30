package tech.gomes.reading.management.builder;

import tech.gomes.reading.management.domain.BookTemplate;
import tech.gomes.reading.management.domain.SuggestionTemplate;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.suggestion.request.SuggestionRequestDTO;

public class SuggestionTemplateBuilder {

    public static SuggestionTemplate from(SuggestionRequestDTO requestDTO, User user, BookTemplate template) {
        return SuggestionTemplate.builder()
                .suggestedISBN(requestDTO.suggestedISBN())
                .suggestedTitle(requestDTO.suggestedTitle())
                .suggestedAuthor(requestDTO.suggestedAuthor())
                .suggestedPublisher(requestDTO.suggestedPublisher())
                .suggestedEdition(requestDTO.suggestedEdition())
                .suggestedDescription(requestDTO.suggestedDescription())
                .suggestedYear(requestDTO.suggestedYear())
                .suggestedPages(requestDTO.suggestedPages())
                .suggestedImg(requestDTO.suggestedReason())
                .reason(requestDTO.suggestedReason())
                .user(user)
                .bookTemplate(template)
                .build();
    }
}
