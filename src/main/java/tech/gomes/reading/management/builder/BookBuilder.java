package tech.gomes.reading.management.builder;

import tech.gomes.reading.management.domain.Book;
import tech.gomes.reading.management.domain.BookTemplate;
import tech.gomes.reading.management.domain.Library;
import tech.gomes.reading.management.dto.book.BookRequestDTO;
import tech.gomes.reading.management.indicator.ReadingStatusIndicator;

public class BookBuilder {

    public static Book from(BookRequestDTO requestDTO, BookTemplate template, Library library) {
        return Book.builder()
                .status(ReadingStatusIndicator.valueOf(requestDTO.status()))
                .readPages(requestDTO.pages())
                .rating(requestDTO.rating())
                .finishedAt(requestDTO.finishedDate().toInstant())
                .startedAt(requestDTO.startedDate().toInstant())
                .bookTemplate(template)
                .library(library)
                .user(library.getUser())
                .build();
    }
}
