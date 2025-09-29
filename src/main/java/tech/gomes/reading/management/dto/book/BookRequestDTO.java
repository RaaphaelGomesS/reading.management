package tech.gomes.reading.management.dto.book;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import tech.gomes.reading.management.dto.suggestion.request.SuggestionRequestDTO;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestDTO {

    @NotBlank
    private String status;

    private int pages;

    private int rating;

    private Date startedDate;

    private Date finishedDate;

    @NotNull
    @Valid
    private SuggestionRequestDTO template;
}
