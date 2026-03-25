package tech.gomes.reading.management.indicator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum FilterTypeIndicator {
    DATE("updatedAt"), RELEVANCE("copyCount");

    private String value;
}
