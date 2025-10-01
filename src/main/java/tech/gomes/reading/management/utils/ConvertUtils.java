package tech.gomes.reading.management.utils;

import org.springframework.stereotype.Component;
import tech.gomes.reading.management.domain.SuggestionTemplate;
import tech.gomes.reading.management.dto.book.BookTemplateRequestDTO;

@Component
public class ConvertUtils {

    public static String getIdentifierByRequestDTO(BookTemplateRequestDTO requestDTO) {
        return requestDTO.isbn() != null ? requestDTO.isbn() : (requestDTO.title() + requestDTO.author()).toLowerCase();
    }

    public static String getIdentifierBySuggestion(SuggestionTemplate suggestion) {
        return suggestion.getSuggestedISBN() != null ? suggestion.getSuggestedISBN()
                : (suggestion.getSuggestedTitle() + suggestion.getSuggestedAuthor()).toLowerCase();
    }
}
