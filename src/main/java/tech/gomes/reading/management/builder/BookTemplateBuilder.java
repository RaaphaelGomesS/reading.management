package tech.gomes.reading.management.builder;

import tech.gomes.reading.management.domain.BookTemplate;
import tech.gomes.reading.management.domain.Category;
import tech.gomes.reading.management.domain.SuggestionTemplate;
import tech.gomes.reading.management.dto.book.BookTemplateRequestDTO;
import tech.gomes.reading.management.indicator.TemplateStatusIndicator;

import java.util.Set;

public class BookTemplateBuilder {

    public static BookTemplate from(SuggestionTemplate suggestion, Set<Category> categories) {
        return BookTemplate.builder()
                .id(suggestion.getBookTemplate() != null ? suggestion.getBookTemplate().getId() : null)
                .ISBN(suggestion.getSuggestedISBN())
                .title(suggestion.getSuggestedTitle())
                .author(suggestion.getSuggestedAuthor())
                .publisher(suggestion.getSuggestedPublisher())
                .edition(suggestion.getSuggestedEdition())
                .description(suggestion.getSuggestedDescription())
                .yearPublication(suggestion.getSuggestedYear())
                .pages(suggestion.getSuggestedPages())
                .img(suggestion.getSuggestedImg())
                .categories(categories)
                .build();
    }

    public static BookTemplate from(BookTemplateRequestDTO requestDTO, Set<Category> categories) {
        return BookTemplate.builder()
                .id(requestDTO.templateId())
                .ISBN(requestDTO.isbn())
                .title(requestDTO.title())
                .author(requestDTO.author())
                .publisher(requestDTO.publisher())
                .edition(requestDTO.edition())
                .description(requestDTO.description())
                .yearPublication(requestDTO.year())
                .pages(requestDTO.pages())
                .img("")
                .categories(categories)
                .build();
    }

    public static void updateBookTemplate(BookTemplate bookTemplate, BookTemplateRequestDTO requestDTO, Set<Category> categories) {
        bookTemplate.setISBN(requestDTO.isbn());
        bookTemplate.setTitle(requestDTO.title());
        bookTemplate.setAuthor(requestDTO.author());
        bookTemplate.setPublisher(requestDTO.publisher());
        bookTemplate.setEdition(requestDTO.edition());
        bookTemplate.setDescription(requestDTO.description());
        bookTemplate.setPages(requestDTO.pages());
        bookTemplate.setImg("");
        bookTemplate.setCategories(categories);
        bookTemplate.setStatus(TemplateStatusIndicator.VERIFIED);
    }
}
