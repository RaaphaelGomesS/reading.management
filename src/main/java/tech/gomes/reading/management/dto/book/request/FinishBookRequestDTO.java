package tech.gomes.reading.management.dto.book.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FinishBookRequestDTO(@NotNull
                                   long bookId,
                                   @NotNull
                                   @Min(1) @Max(5)
                                   int rating) {
}
