package tech.gomes.reading.management.controller.filter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoteFilter {

    private long categoryId;
    private long bookId;
    private long userId;
    private String type;
    private int page;
    private int pageSize;
}
