package tech.gomes.reading.management.repository.Specification;

import org.springframework.data.jpa.domain.Specification;
import tech.gomes.reading.management.controller.filter.BookTemplateFilter;
import tech.gomes.reading.management.domain.BookTemplate;

public class BookTemplateSpecification {

    private static Specification<BookTemplate> byIsbn(String isbn) {
        return (root, query, cb) ->
                isbn == null ? null : cb.equal(root.get("ISBN"), isbn);
    }

    private static Specification<BookTemplate> byAuthor(String author) {
        return (root, query, cb) ->
                author == null ? null : cb.like(cb.lower(root.get("author")), "%" + author.toLowerCase() + "%");
    }

    private static Specification<BookTemplate> byTitle(String title) {
        return (root, query, cb) ->
                title == null ? null : cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<BookTemplate> byFilter(BookTemplateFilter filter) {

        return Specification.anyOf(
                byTitle(filter.getTitle()))
                .or(byAuthor(filter.getAuthor()))
                .or(byIsbn(filter.getISBN())
                );
    }
}
