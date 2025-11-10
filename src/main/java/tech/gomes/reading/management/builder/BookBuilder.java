package tech.gomes.reading.management.builder;

import tech.gomes.reading.management.domain.Book;
import tech.gomes.reading.management.domain.BookTemplate;
import tech.gomes.reading.management.domain.Library;
import tech.gomes.reading.management.dto.book.request.BookRequestDTO;
import tech.gomes.reading.management.indicator.ReadingStatusIndicator;

import java.time.Instant;

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

    public static void updateBookFromRequest(Book book, BookRequestDTO requestDTO) {

        if (book.getBookTemplate().getPages() == requestDTO.pages()) {
            book.setStatus(ReadingStatusIndicator.READ);
            book.setReadPages(requestDTO.pages());

            if (requestDTO.finishedDate() == null) {
                book.setFinishedAt(Instant.now());
            }

            if (book.getStartedAt() == null) {
                book.setStartedAt(Instant.now());
            }
        }

        ReadingStatusIndicator status = ReadingStatusIndicator.getStatusByName(requestDTO.status());

        book.setStatus(status);

        if (status == ReadingStatusIndicator.READ) {
            book.setFinishedAt(Instant.now());
            book.setRating(requestDTO.rating());
            book.setReadPages(book.getBookTemplate().getPages());
        } else if (status == ReadingStatusIndicator.READING) {
            book.setStartedAt(Instant.now());
            book.setReadPages(requestDTO.pages());
        } else {
            if (requestDTO.startedDate() != null) {
                book.setStartedAt(requestDTO.startedDate());
            }
        }
    }
}
