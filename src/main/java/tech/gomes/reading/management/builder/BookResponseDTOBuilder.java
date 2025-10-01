package tech.gomes.reading.management.builder;

import tech.gomes.reading.management.domain.Book;
import tech.gomes.reading.management.dto.book.BookResponseDTO;

import java.util.Date;

public class BookResponseDTOBuilder {

    public static BookResponseDTO from(Book book) {
        return BookResponseDTO.builder()
                .id(book.getId())
                .pages(book.getReadPages())
                .rating(book.getRating())
                .startedDate(Date.from(book.getStartedAt()))
                .finishedDate(Date.from(book.getFinishedAt()))
                .build();
    }
}
