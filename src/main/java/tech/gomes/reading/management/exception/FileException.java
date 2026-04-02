package tech.gomes.reading.management.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FileException extends ApplicationException {
    public FileException(String message, HttpStatus status) {
        super(message, status);
    }
}
