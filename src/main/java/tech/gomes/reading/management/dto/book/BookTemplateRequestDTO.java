package tech.gomes.reading.management.dto.book;

import jakarta.persistence.Column;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookTemplateRequestDTO {

    private Long id;

    private String ISBN;

    private String title;

    private String author;

    private String publisher;

    private String edition;

    private String description;

    private int pages;

    private String img;
}
