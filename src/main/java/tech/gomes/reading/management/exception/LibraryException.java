package tech.gomes.reading.management.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class LibraryException extends ApplicationException {

    public LibraryException(String message, HttpStatus status) {
        super(message, status);
    }
}
