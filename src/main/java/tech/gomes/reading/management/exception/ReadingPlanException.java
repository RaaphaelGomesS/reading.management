package tech.gomes.reading.management.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ReadingPlanException extends Exception {

    private final HttpStatus status;

    public ReadingPlanException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
