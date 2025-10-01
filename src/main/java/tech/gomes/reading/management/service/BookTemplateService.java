package tech.gomes.reading.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tech.gomes.reading.management.builder.BookTemplateBuilder;
import tech.gomes.reading.management.builder.BookTemplateResponseDTOBuilder;
import tech.gomes.reading.management.domain.BookTemplate;
import tech.gomes.reading.management.domain.Category;
import tech.gomes.reading.management.domain.SuggestionTemplate;
import tech.gomes.reading.management.dto.book.BookTemplateRequestDTO;
import tech.gomes.reading.management.dto.book.BookTemplateResponseDTO;
import tech.gomes.reading.management.dto.book.BookTemplateResponsePageDTO;
import tech.gomes.reading.management.exception.BookTemplateException;
import tech.gomes.reading.management.indicator.TemplateStatusIndicator;
import tech.gomes.reading.management.repository.BookTemplateRepository;
import tech.gomes.reading.management.repository.CategoryRepository;
import tech.gomes.reading.management.utils.ConvertUtils;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookTemplateService {

    private final BookTemplateRepository bookTemplateRepository;

    private final CategoryRepository categoryRepository;

    public BookTemplate getOrcreateBookTemplate(BookTemplateRequestDTO requestDTO) {

        String identifier = ConvertUtils.getIdentifierByRequestDTO(requestDTO);

        Optional<BookTemplate> existentTemplate = bookTemplateRepository.findByIdentifierAndStatus(identifier, TemplateStatusIndicator.VERIFIED.name());

        if (existentTemplate.isPresent()) {
            return existentTemplate.get();
        }

        Set<Category> categories = getCategoriesOrCreateIfNotExist(requestDTO.categories());

        BookTemplate template = BookTemplateBuilder.from(requestDTO, categories);

        return bookTemplateRepository.save(template);
    }

    public BookTemplateResponseDTO updateBookTemplateByAdminRequest(BookTemplateRequestDTO requestDTO) throws Exception {

        BookTemplate bookTemplate = findTemplateById(requestDTO.templateId());

        Set<Category> categories = getCategoriesOrCreateIfNotExist(requestDTO.categories());

        BookTemplateBuilder.updateBookTemplate(bookTemplate, requestDTO, categories);

        BookTemplate updateTemplate = bookTemplateRepository.save(bookTemplate);

        return BookTemplateResponseDTOBuilder.from(updateTemplate);
    }

    public void inactiveInvalidTemplate(long id) throws Exception {
        BookTemplate bookTemplate = findTemplateById(id);

        bookTemplate.setStatus(TemplateStatusIndicator.INACTIVE);

        bookTemplateRepository.save(bookTemplate);
    }

    public void updateBookTemplateBySuggestion(SuggestionTemplate suggestion) throws Exception {

        String identifier = ConvertUtils.getIdentifierBySuggestion(suggestion);

        verifyBookTemplateAlreadyExist(identifier);

        Set<Category> categories = getCategoriesOrCreateIfNotExist(suggestion.getSuggestedCategories());

        BookTemplate template = BookTemplateBuilder.from(suggestion, categories);

        bookTemplateRepository.save(template);
    }

    public BookTemplateResponsePageDTO findAllTemplatesByStatus(int page, int pageSize, String direction, String status) {

        Pageable pageable = PageRequest.of(page, pageSize, Sort.Direction.valueOf(direction), "createdAt");

        Page<BookTemplate> bookTemplatePage = bookTemplateRepository.findByStatus(status, pageable);

        return BookTemplateResponseDTOBuilder.fromPage(bookTemplatePage);
    }

    public BookTemplate findTemplateById(long id) throws Exception {
        return bookTemplateRepository.findByIdAndStatus(id, TemplateStatusIndicator.VERIFIED.name())
                .orElseThrow(() -> new BookTemplateException("O template não foi encontrado.", HttpStatus.NOT_FOUND));
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

    private void verifyBookTemplateAlreadyExist(String identifier) throws Exception {

        Optional<BookTemplate> bookTemplate = bookTemplateRepository.findByIdentifierAndStatus(identifier, TemplateStatusIndicator.VERIFIED.name());

        if (bookTemplate.isPresent()) {
            throw new BookTemplateException("Já existe um template com esse ISBN ou combinação de titulo + autor", HttpStatus.BAD_REQUEST);
        }
    }
}
