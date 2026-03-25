package tech.gomes.reading.management.repository.specification;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import tech.gomes.reading.management.controller.filter.BookTemplateFilter;
import tech.gomes.reading.management.domain.BookTemplate;
import tech.gomes.reading.management.indicator.TemplateStatusIndicator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookTemplateSpecification {

    private static Specification<BookTemplate> byIsbn(String isbn) {
        return (root, query, cb) ->
                isbn == null ? null : cb.equal(root.get("isbn"), isbn);
    }

    private static Specification<BookTemplate> byAuthor(String author) {
        return (root, query, cb) ->
                author == null ? null : cb.like(cb.lower(root.get("author")), "%" + author.toLowerCase() + "%");
    }

    private static Specification<BookTemplate> byTitle(String title) {
        return (root, query, cb) ->
                title == null ? null : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    private static Specification<BookTemplate> byStatus() {
        return (root, query, cb) -> cb.equal(root.get("status"), TemplateStatusIndicator.VERIFIED);
    }

    public static Specification<BookTemplate> byFilter(BookTemplateFilter filter) {

        return Specification.allOf(byStatus(),
                Specification.anyOf(
                        byTitle(filter.getTitle()),
                        byAuthor(filter.getAuthor()),
                        byIsbn(filter.getIsbn())
                ));
    }
}
