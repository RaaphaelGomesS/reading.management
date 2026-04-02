package tech.gomes.reading.management.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ReadingPlanException extends ApplicationException {

    public ReadingPlanException(String message, HttpStatus status) {
        super(message, status);
    }
}
