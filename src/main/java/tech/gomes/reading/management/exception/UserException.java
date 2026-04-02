package tech.gomes.reading.management.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserException extends ApplicationException {

    public UserException(String message, HttpStatus status) {
        super(message, status);
    }
}
