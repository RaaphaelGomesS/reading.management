package tech.gomes.reading.management.indicator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ReadingStatusIndicator {
    WANT_TO_READ ("aguardando"),
    READING ("lendo"),
    READ ("finalizado"),
    DROPPED ("parado");

    private String value;

    public static ReadingStatusIndicator getStatusByName(String name) {

        for (ReadingStatusIndicator indicator : ReadingStatusIndicator.values()) {
            if (indicator.getValue().equals(name)) {
                return indicator;
            }
            return WANT_TO_READ;
        }
        return null;
    }
}
