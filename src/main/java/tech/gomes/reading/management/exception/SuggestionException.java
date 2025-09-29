package tech.gomes.reading.management.exception;

import org.springframework.http.HttpStatus;

public class SuggestionException extends Exception {

    private HttpStatus status;

    public SuggestionException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
