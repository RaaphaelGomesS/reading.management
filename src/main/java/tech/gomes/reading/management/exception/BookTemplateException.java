package tech.gomes.reading.management.exception;

import org.springframework.http.HttpStatus;

public class BookTemplateException extends Exception {

    private HttpStatus status;

    public BookTemplateException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
