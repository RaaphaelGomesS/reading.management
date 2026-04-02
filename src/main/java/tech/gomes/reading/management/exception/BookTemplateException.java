package tech.gomes.reading.management.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BookTemplateException extends ApplicationException {

    public BookTemplateException(String message, HttpStatus status) {
        super(message, status);
    }
}
