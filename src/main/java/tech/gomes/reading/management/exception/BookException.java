package tech.gomes.reading.management.exception;

import org.springframework.http.HttpStatus;

public class BookException extends Exception {

    private HttpStatus status;

    public BookException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
