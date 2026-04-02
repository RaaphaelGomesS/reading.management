package tech.gomes.reading.management.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NoteException extends ApplicationException {

    public NoteException(String message, HttpStatus status) {
        super(message, status);
    }
}
