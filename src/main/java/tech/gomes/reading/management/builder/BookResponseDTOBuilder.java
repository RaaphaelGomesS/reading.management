package tech.gomes.reading.management.builder;


import org.springframework.data.domain.Page;
import tech.gomes.reading.management.domain.Book;
import tech.gomes.reading.management.dto.book.response.BookResponseDTO;
import tech.gomes.reading.management.dto.book.response.BookResponsePageDTO;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BookResponseDTOBuilder {

    public static BookResponseDTO from(Book book) {
        return BookResponseDTO.builder()
                .id(book.getId())
                .pages(book.getReadPages())
                .rating(book.getRating())
                .status(book.getStatus().getValue())
                .startedDate(Date.from(book.getStartedAt()))
                .finishedDate(Date.from(book.getFinishedAt()))
                .build();
    }

    public static BookResponsePageDTO from(Page<Book> bookPage) {
        List<BookResponseDTO> responseDTOList = bookPage.getContent().isEmpty() ?
                Collections.emptyList() : bookPage.getContent().stream().map(BookResponseDTOBuilder::from).collect(Collectors.toList());

        return BookResponsePageDTO.builder()
                .page(bookPage.getNumber())
                .pageSize(bookPage.getSize())
                .totalPages(bookPage.getTotalPages())
                .totalElements(bookPage.getNumberOfElements())
                .data(responseDTOList)
                .build();
    }
}
