package tech.gomes.reading.management.exception;

import org.springframework.http.HttpStatus;

public class LibraryException extends Exception {

    private HttpStatus status;

    public LibraryException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
