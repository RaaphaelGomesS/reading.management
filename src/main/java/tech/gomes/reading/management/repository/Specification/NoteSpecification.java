package tech.gomes.reading.management.repository.Specification;

import org.springframework.data.jpa.domain.Specification;
import tech.gomes.reading.management.controller.filter.NoteFilter;
import tech.gomes.reading.management.domain.Note;
import tech.gomes.reading.management.indicator.NoteTypeIndicator;

public class NoteSpecification {

    private static Specification<Note> byCategoryId(Long categoryId) {
        return (root, query, cb) -> categoryId == null ? null : cb.equal(root.get("category").get("id"), categoryId);
    }

    private static Specification<Note> byType(NoteTypeIndicator type) {
        return (root, query, cb) -> type == null ? null : cb.equal(root.get("type"), type);
    }

    private static Specification<Note> byBookId(Long bookId) {
        return (root, query, cb) -> bookId == null ? null : cb.equal(root.get("book").get("id"), bookId);
    }

    private static Specification<Note> byUserId(Long userId) {
        return (root, query, cb) -> userId == null ? null : cb.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Note> byFilter(NoteFilter filter) {

        NoteTypeIndicator type = NoteTypeIndicator.getTypeByName(filter.getType());

        return Specification
                .where(byUserId(filter.getUserId()))
                .and(byType(type))
                .and(byBookId(filter.getBookId()))
                .and(byCategoryId(filter.getCategoryId()));
    }
}
