package tech.gomes.reading.management.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tech.gomes.reading.management.builder.BookTemplateResponseDTOBuilder;
import tech.gomes.reading.management.domain.Book;
import tech.gomes.reading.management.domain.BookCategory;
import tech.gomes.reading.management.domain.BookTemplate;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.StatisticsResponseDTO;
import tech.gomes.reading.management.dto.book.response.BookStatusCountDTO;
import tech.gomes.reading.management.dto.book.response.BookTemplateResponseDTO;
import tech.gomes.reading.management.dto.book.response.CategoryFinishCountDTO;
import tech.gomes.reading.management.indicator.ReadingStatusIndicator;
import tech.gomes.reading.management.repository.BookRepository;
import tech.gomes.reading.management.repository.BookTemplateRepository;
import tech.gomes.reading.management.repository.projections.BookStatusCountProjection;
import tech.gomes.reading.management.repository.projections.CategoryFinishCountProjection;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final BookRepository bookRepository;

    private final BookTemplateRepository templateRepository;

    public StatisticsResponseDTO getStatisticsForUser(User user) {

        Double avgReadPagesPerDay = bookRepository.getAveragePagesPerDayByUserId(user.getId());

        log.info("Média de páginas lidas por dia: {}", avgReadPagesPerDay);

        Double avgDaysToFinish = bookRepository.getAverageReadingTimeInDaysByUserId(user.getId());

        log.info("Média de dias para terminar uma obra: {}", avgDaysToFinish);

        List<BookStatusCountProjection> statusCountProjections = bookRepository.countBooksByStatusByUserId(user.getId());
        List<CategoryFinishCountProjection> finishCountProjections = bookRepository.countFinishedBooksByCategoryByUserId(user.getId());

        List<BookStatusCountDTO> statusCountList = statusCountProjections.stream()
                .map(p -> new BookStatusCountDTO(p.getStatus().getValue(), p.getCount()))
                .toList();

        log.info("Quantidade de livros por status: {}", statusCountList);

        List<CategoryFinishCountDTO> categoryFinishCountList = finishCountProjections.stream()
                .map(p -> new CategoryFinishCountDTO(p.getCategory(), p.getCount()))
                .toList();

        log.info("Quantidade de livros finalizados por categoria: {}", categoryFinishCountList);

        return StatisticsResponseDTO.builder()
                .averagePagesReadInDay(avgReadPagesPerDay == null ? 0L : Math.round(avgReadPagesPerDay))
                .averageReadingTimeInDays(avgDaysToFinish == null ? 0L : Math.round(avgDaysToFinish))
                .finishedBooksByCategory(categoryFinishCountList)
                .statusCounts(statusCountList)
                .build();
    }

    public List<BookTemplateResponseDTO> findReadRecommendationForUser(User user) {

        Book recentFinishedBook = bookRepository.findFirstByUserIdAndStatusOrderByFinishedAtDesc(user.getId(), ReadingStatusIndicator.READ).orElse(null);

        log.info("Último livro finalizado: {}", recentFinishedBook);

        if (recentFinishedBook == null) {
            return Collections.emptyList();
        }

        Set<Long> categories = recentFinishedBook.getBookTemplate().getCategories().stream().map(BookCategory::getId).collect(Collectors.toSet());

        Pageable pageable = PageRequest.of(0, 5);

        Page<BookTemplate> similarTemplatesByCategories = templateRepository.findSimilarTemplatesByCategories(categories, recentFinishedBook.getBookTemplate().getId(), pageable);

        List<BookTemplateResponseDTO> recommendations = similarTemplatesByCategories.stream().map(BookTemplateResponseDTOBuilder::from).toList();

        log.info("Obras recomendadas baseadas nas categorias: {}", recommendations);

        return recommendations;
    }
}
