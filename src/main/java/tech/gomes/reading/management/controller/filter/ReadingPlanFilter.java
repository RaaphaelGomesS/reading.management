package tech.gomes.reading.management.controller.filter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import tech.gomes.reading.management.indicator.FilterTypeIndicator;

import java.util.Set;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReadingPlanFilter {

    private String title;
    private Set<Long> categoryIds;

    @Builder.Default
    private int page = 0;
    @Builder.Default
    private int pageSize = 10;
    @Builder.Default
    private String direction = "ASC";
    @Builder.Default
    private FilterTypeIndicator type = FilterTypeIndicator.DATE;
}
