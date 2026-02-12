package tech.gomes.reading.management.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import tech.gomes.reading.management.domain.JoinPlanTemplate;
import tech.gomes.reading.management.domain.PlanCategory;
import tech.gomes.reading.management.domain.ReadingPlan;
import tech.gomes.reading.management.domain.User;
import tech.gomes.reading.management.dto.readingPlan.request.PlanRequestDTO;
import tech.gomes.reading.management.utils.DateUtils;

import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReadingPlanBuilder {

    public static ReadingPlan fromRequestDTO(PlanRequestDTO requestDTO, User user, Set<JoinPlanTemplate> joinPlans, Set<PlanCategory> planCategories) {
        return ReadingPlan
                .builder()
                .title(requestDTO.title())
                .description(requestDTO.description())
                .isPublic(requestDTO.isPublic())
                .goalDate(requestDTO.goalDate() != null ? DateUtils.formatStringToLocalDate(requestDTO.goalDate()) : null)
                .copyCount(0)
                .categories(planCategories)
                .bookTemplatesPlan(joinPlans)
                .user(user)
                .build();
    }
}
