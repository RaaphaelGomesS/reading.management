package tech.gomes.reading.management.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NoteException extends Exception {

    private final HttpStatus status;

    public NoteException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
