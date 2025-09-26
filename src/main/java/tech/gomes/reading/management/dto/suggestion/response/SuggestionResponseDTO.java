package tech.gomes.reading.management.dto.suggestion.response;

import java.util.List;

public record SuggestionResponseDTO(String isbn,
                                    String title,
                                    String author,
                                    String publisher,
                                    String edition,
                                    String description,
                                    int year,
                                    int pages,
                                    String img,
                                    String reason,
                                    String justification,
                                    String status,
                                    List<String> categories) {
}
