package tech.gomes.reading.management.repository.specification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import tech.gomes.reading.management.controller.filter.NoteFilter;
import tech.gomes.reading.management.domain.Note;
import tech.gomes.reading.management.indicator.NoteTypeIndicator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NoteSpecification {

    private static Specification<Note> byUserId(Long userId) {
        return (root, query, cb) -> userId == null ? null : cb.equal(root.get("user").get("id"), userId);
    }

    private static Specification<Note> byCategoryId(Long categoryId) {
        return (root, query, cb) -> categoryId == null ? null : cb.equal(root.get("category").get("id"), categoryId);
    }

    private static Specification<Note> byType(NoteTypeIndicator type) {
        return (root, query, cb) -> type == null ? null : cb.equal(root.get("type"), type);
    }

    private static Specification<Note> byTitle(String title) {
        return (root, query, cb) ->
                (title == null || title.isBlank()) ? null : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Note> byFilter(NoteFilter filter) {

        NoteTypeIndicator type = NoteTypeIndicator.getTypeByName(filter.getType());

        return Specification.allOf(
                byUserId(filter.getUserId()),
                byTitle(filter.getTitle()),
                byType(type),
                byCategoryId(filter.getCategoryId())
        );
    }
}
