package tech.gomes.reading.management.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.gomes.reading.management.domain.SuggestionTemplate;
import tech.gomes.reading.management.dto.book.BookTemplateRequestDTO;

@Component
public class ConvertUtils {

    @Value("${file.upload.dir}")
    private static String dir;

    public static String getIdentifierByRequestDTO(BookTemplateRequestDTO requestDTO) {
        return requestDTO.isbn() != null ? requestDTO.isbn() : (requestDTO.title() + requestDTO.author()).toLowerCase();
    }

    public static String getIdentifierBySuggestion(SuggestionTemplate suggestion) {
        return suggestion.getSuggestedISBN() != null ? suggestion.getSuggestedISBN()
                : (suggestion.getSuggestedTitle() + suggestion.getSuggestedAuthor()).toLowerCase();
    }

    public static String uriCoverImg(String imgName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(dir).path(imgName).toUriString();
    }
}
