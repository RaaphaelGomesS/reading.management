package tech.gomes.reading.management.builder;

import tech.gomes.reading.management.domain.Book;
import tech.gomes.reading.management.domain.BookTemplate;
import tech.gomes.reading.management.domain.Library;
import tech.gomes.reading.management.dto.book.request.BookRequestDTO;
import tech.gomes.reading.management.indicator.ReadingStatusIndicator;

public class BookBuilder {

    public static Book from(BookRequestDTO book, BookTemplate template, Library library) {
        return Book.builder()
                .status(ReadingStatusIndicator.valueOf(book.status()))
                .readPages(book.pages())
                .rating(book.rating())
                .finishedAt(book.finishedDate().toInstant())
                .startedAt(book.startedDate().toInstant())
                .bookTemplate(template)
                .library(library)
                .user(library.getUser())
                .build();
    }

    public static void updateBookFromRequest(Book book, BookRequestDTO requestDTO) {
        book.setStatus(ReadingStatusIndicator.valueOf(requestDTO.status()));
        book.setReadPages(requestDTO.status());
        book.setRating(requestDTO.status());
        book.setStartedAt(requestDTO.status());
        book.setFinishedAt(requestDTO.status());
    }
}
