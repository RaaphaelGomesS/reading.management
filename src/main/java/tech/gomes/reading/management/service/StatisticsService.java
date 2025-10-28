package tech.gomes.reading.management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.StatisticsResponseDTO;
import tech.gomes.reading.management.dto.book.response.BookStatusCountDTO;
import tech.gomes.reading.management.dto.book.response.CategoryFinishCountDTO;
import tech.gomes.reading.management.repository.BookRepository;
import tech.gomes.reading.management.repository.projections.BookStatusCountProjection;
import tech.gomes.reading.management.repository.projections.CategoryFinishCountProjection;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final BookRepository bookRepository;

    public StatisticsResponseDTO getStatisticsForUser(User user) {

        Double avgReadPagesPerDay = bookRepository.getAveragePagesPerDayByUserId(user.getId());
        Double avgDaysToFinish = bookRepository.getAverageReadingTimeInDaysByUserId(user.getId());
        List<BookStatusCountProjection> statusCountProjections = bookRepository.countBooksByStatusByUserId(user.getId());
        List<CategoryFinishCountProjection> finishCountProjections = bookRepository.countFinishedBooksByCategoryByUserId(user.getId());

        List<BookStatusCountDTO> statusCountList = statusCountProjections.stream()
                .map(p -> new BookStatusCountDTO(p.getStatus().getValue(), p.getCount()))
                .toList();

        List<CategoryFinishCountDTO> CategoryFinishCountList = finishCountProjections.stream()
                .map(p -> new CategoryFinishCountDTO(p.getCategory(), p.getCount()))
                .toList();

        return StatisticsResponseDTO.builder()
                .averagePagesReadForDay(avgReadPagesPerDay == null ? 0.0 : avgReadPagesPerDay)
                .averageReadingTimeInDays(avgDaysToFinish == null ? 0.0 : avgDaysToFinish)
                .finishedBooksByCategory(CategoryFinishCountList)
                .statusCounts(statusCountList)
                .build();
    }
}
