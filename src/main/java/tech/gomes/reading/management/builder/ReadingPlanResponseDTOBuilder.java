package tech.gomes.reading.management.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import tech.gomes.reading.management.domain.JoinPlanTemplate;
import tech.gomes.reading.management.domain.PlanCategory;
import tech.gomes.reading.management.domain.ReadingPlan;
import tech.gomes.reading.management.dto.readingPlan.PlanSummaryDTO;
import tech.gomes.reading.management.dto.readingPlan.ReadingPlanPageDTO;
import tech.gomes.reading.management.dto.readingPlan.response.BookPlanResponseDTO;
import tech.gomes.reading.management.dto.readingPlan.response.PlanResponseDTO;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReadingPlanResponseDTOBuilder {

    public static PlanResponseDTO fromReadingPlan(ReadingPlan plan) {

        Map<Integer, BookPlanResponseDTO> positionBookMap = plan.getBookTemplatesPlan().stream()
                .collect(Collectors.toMap(JoinPlanTemplate::getOrder,
                        bookPlan -> new BookPlanResponseDTO(bookPlan.getTemplateId(), bookPlan.getDescription()),
                        (alreadyInsert, actual) -> alreadyInsert,
                        TreeMap::new
                ));

        Set<Long> categoriesIds = plan.getCategories().stream().map(PlanCategory::getId).collect(Collectors.toSet());

        return PlanResponseDTO.builder()
                .title(plan.getTitle())
                .description(plan.getDescription())
                .positionTemplate(positionBookMap)
                .categoriesIds(categoriesIds)
                .isPublic(plan.getIsPublic())
                .goalDate(plan.getGoalDate().toString())
                .build();
    }

    public static ReadingPlanPageDTO fromPageReadingPlan(Page<ReadingPlan> plans) {

        List<PlanSummaryDTO> summaryDTOList = plans.getContent().isEmpty() ? Collections.emptyList()
                : plans.getContent().stream().map(ReadingPlanResponseDTOBuilder::fromReadingPlanToSummary).collect(Collectors.toList());

        return ReadingPlanPageDTO.builder()
                .page(plans.getNumber())
                .pageSize(plans.getSize())
                .totalPages(plans.getTotalPages())
                .totalElements(plans.getNumberOfElements())
                .data(summaryDTOList)
                .build();
    }

    private static PlanSummaryDTO fromReadingPlanToSummary(ReadingPlan plan) {
        return new PlanSummaryDTO(plan.getId(), plan.getTitle(), plan.getDescription());
    }
}
