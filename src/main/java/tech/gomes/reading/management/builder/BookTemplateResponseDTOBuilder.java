package tech.gomes.reading.management.builder;

import org.springframework.data.domain.Page;
import tech.gomes.reading.management.domain.BookTemplate;
import tech.gomes.reading.management.domain.Category;
import tech.gomes.reading.management.dto.book.BookTemplateRequestDTO;
import tech.gomes.reading.management.dto.book.BookTemplateResponseDTO;
import tech.gomes.reading.management.dto.book.BookTemplateResponsePageDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class BookTemplateResponseDTOBuilder {

    public static BookTemplateResponsePageDTO fromPage(Page<BookTemplate> templatePage) {
        List<BookTemplateResponseDTO> responseDTOList = templatePage.getContent().isEmpty() ?
                templatePage.getContent().stream().map(BookTemplateResponseDTOBuilder::from).collect(Collectors.toList()) : Collections.emptyList();

        return BookTemplateResponsePageDTO.builder()
                .page(templatePage.getNumber())
                .pageSize(templatePage.getSize())
                .totalPages(templatePage.getTotalPages())
                .totalElements(templatePage.getNumberOfElements())
                .data(responseDTOList)
                .build();
    }

    public static BookTemplateResponseDTO from(BookTemplate template) {
        return BookTemplateResponseDTO.builder()
                .isbn(template.getISBN())
                .title(template.getTitle())
                .author(template.getAuthor())
                .publisher(template.getPublisher())
                .edition(template.getEdition())
                .description(template.getDescription())
                .year(template.getYearPublication())
                .pages(template.getPages())
                .img(template.getImg())
                .status(template.getStatus().name())
                .categories(template.getCategories().stream().map(Category::getName).collect(Collectors.toSet()))
                .build();
    }
}
