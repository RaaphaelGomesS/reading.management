package tech.gomes.reading.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tech.gomes.reading.management.builder.BookTemplateBuilder;
import tech.gomes.reading.management.domain.BookTemplate;
import tech.gomes.reading.management.domain.Category;
import tech.gomes.reading.management.domain.SuggestionTemplate;
import tech.gomes.reading.management.exception.BookTemplateException;
import tech.gomes.reading.management.repository.BookTemplateRepository;
import tech.gomes.reading.management.repository.CategoryRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookTemplateService {

    private final BookTemplateRepository bookTemplateRepository;

    private final CategoryRepository categoryRepository;

    public void createBookTemplateBySuggestion(SuggestionTemplate suggestion) throws Exception {

        verifyBookTemplateAlreadyExist(suggestion);

        Set<Category> categories = getCategoriesOrCreateIfNotExist(suggestion.getSuggestedCategories());

        BookTemplate template = BookTemplateBuilder.from(suggestion, categories);

        bookTemplateRepository.save(template);
    }

    public void updateBookTemplateBySuggestion(SuggestionTemplate suggestion) throws Exception {

        verifyBookTemplateAlreadyExist(suggestion);

        Set<Category> categories = getCategoriesOrCreateIfNotExist(suggestion.getSuggestedCategories());

        BookTemplate template = BookTemplateBuilder.from(suggestion, categories);

        bookTemplateRepository.save(template);
    }


    private Set<Category> getCategoriesOrCreateIfNotExist(Set<String> categoriesName) {
        Set<Category> existentCategories = categoryRepository.findByNameIn(categoriesName);

        Set<String> existentCategoriesNames = existentCategories.stream().map(Category::getName).collect(Collectors.toSet());

        Set<Category> newCategories = categoriesName.stream().filter(category -> !existentCategoriesNames.contains(category))
                .map(category -> Category.builder().name(category).build())
                .collect(Collectors.toSet());

        if (!newCategories.isEmpty()) {
            categoryRepository.saveAll(newCategories);
            existentCategories.addAll(newCategories);
        }

        return existentCategories;
    }

    private void verifyBookTemplateAlreadyExist(SuggestionTemplate suggestion) throws Exception {

        String identifier = suggestion.getSuggestedISBN() != null ? suggestion.getSuggestedISBN() :
                (suggestion.getSuggestedTitle() + suggestion.getSuggestedAuthor()).toLowerCase();

        Optional<BookTemplate> bookTemplate = bookTemplateRepository.findByIdentifier(identifier);

        if (bookTemplate.isPresent()) {
            throw new BookTemplateException("Já existe um template com esse ISBN ou combinação de titulo + autor", HttpStatus.BAD_REQUEST);
        }
    }
}
