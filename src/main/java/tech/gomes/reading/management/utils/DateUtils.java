package tech.gomes.reading.management.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@UtilityClass
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {

    private static final ZoneId ZONE = ZoneId.of("America/Sao_Paulo");

    public static String formatInstantToDate(Instant instant) {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy")
                .withZone(ZONE)
                .format(instant);
    }

    public static String formatInstantToDateTime(Instant instant) {
        return DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
                .withZone(ZONE)
                .format(instant);
    }

    public static LocalDate formatStringToLocalDate(String date) {
        try {
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e) {
            return null;
        }
    }
}
