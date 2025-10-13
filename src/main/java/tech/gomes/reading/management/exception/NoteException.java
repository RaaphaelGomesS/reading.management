package tech.gomes.reading.management.exception;

import org.springframework.http.HttpStatus;

public class NoteException extends Exception {

    private HttpStatus status;

    public NoteException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
