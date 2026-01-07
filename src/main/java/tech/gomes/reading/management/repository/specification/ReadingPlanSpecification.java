package tech.gomes.reading.management.repository.specification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import tech.gomes.reading.management.controller.filter.ReadingPlanFilter;
import tech.gomes.reading.management.domain.ReadingPlan;

import java.util.Objects;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReadingPlanSpecification {

    private static Specification<ReadingPlan> byCategoryId(Set<Long> categoryIds) {
        return (root, query, cb) -> {
            if (categoryIds == null || categoryIds.isEmpty()) {
                return null;
            }
            Objects.requireNonNull(query).distinct(true);
            return root.join("categories").get("id").in(categoryIds);
        };
    }


    private static Specification<ReadingPlan> byTitle(String title) {
        return (root, query, cb) ->
                (title == null || title.isBlank()) ? null : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<ReadingPlan> byFilter(ReadingPlanFilter filter) {

        return Specification.allOf(
                byTitle(filter.getTitle()),
                byCategoryId(filter.getCategoryIds())
        );
    }
}
