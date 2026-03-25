package tech.gomes.reading.management.controller.filter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookTemplateFilter {

    @Schema(description = "Nome do autor/a: ", example = "Dostoiesvski")
    private String author;
    @Schema(description = "ISBN do livro: ", example = "9788573261851")
    private String isbn;
    @Schema(description = "Título da obra: ", example = "Memórias do subsolo")
    private String title;
    @Builder.Default
    private int page = 0;
    @Builder.Default
    private int pageSize = 10;
}
