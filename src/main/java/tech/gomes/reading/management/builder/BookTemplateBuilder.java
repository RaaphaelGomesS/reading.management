package tech.gomes.reading.management.builder;

import tech.gomes.reading.management.domain.BookTemplate;
import tech.gomes.reading.management.domain.Category;
import tech.gomes.reading.management.domain.SuggestionTemplate;

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
}
