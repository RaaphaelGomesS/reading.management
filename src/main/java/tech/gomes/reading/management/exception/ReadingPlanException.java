package tech.gomes.reading.management.exception;

import org.springframework.http.HttpStatus;

public class ReadingPlanException extends Exception {
    private HttpStatus status;

    public ReadingPlanException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
