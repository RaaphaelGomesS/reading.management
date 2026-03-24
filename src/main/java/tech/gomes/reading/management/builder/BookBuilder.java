package tech.gomes.reading.management.builder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import tech.gomes.reading.management.domain.Book;
import tech.gomes.reading.management.domain.BookTemplate;
import tech.gomes.reading.management.domain.Library;
import tech.gomes.reading.management.dto.book.request.BookRequestDTO;
import tech.gomes.reading.management.indicator.ReadingStatusIndicator;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BookBuilder {

    public static Book from(BookRequestDTO book, BookTemplate template, Library library) {
        return Book.builder()
                .status(ReadingStatusIndicator.getStatusByName(book.status()))
                .readPages(book.pages())
                .rating(book.rating())
                .finishedAt(book.finishedDate() == null ? null : book.finishedDate())
                .startedAt(book.startedDate() == null ? null : book.startedDate())
                .bookTemplate(template)
                .library(library)
                .user(library.getUser())
                .build();
    }

    public static Book fromPlan(BookTemplate template, Library library) {
        return Book.builder()
                .status(ReadingStatusIndicator.WANT_TO_READ)
                .readPages(0)
                .rating(0.0)
                .finishedAt(null)
                .startedAt(null)
                .bookTemplate(template)
                .library(library)
                .user(library.getUser())
                .build();
    }
}
