package tech.gomes.reading.management.dto.category;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CategoryRequestDTO(long id, String name) {
}
