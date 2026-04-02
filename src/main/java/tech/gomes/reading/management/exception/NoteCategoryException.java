package tech.gomes.reading.management.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NoteCategoryException extends ApplicationException {

    public NoteCategoryException(String message, HttpStatus status) {
        super(message, status);
    }
}
