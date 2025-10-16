package tech.gomes.reading.management.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity handlerUserException(UserException e) {

        UserException exception = new UserException(e.getMessage(), e.getStatus());

        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(LibraryException.class)
    public ResponseEntity handlerLibraryException(LibraryException e) {

        LibraryException exception = new LibraryException(e.getMessage(), e.getStatus());

        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(BookException.class)
    public ResponseEntity handlerBookException(BookException e) {

        BookException exception = new BookException(e.getMessage(), e.getStatus());

        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(BookTemplateException.class)
    public ResponseEntity handlerBookTemplateException(BookTemplateException e) {

        BookTemplateException exception = new BookTemplateException(e.getMessage(), e.getStatus());

        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(NoteCategoryException.class)
    public ResponseEntity handlerNoteCategoryException(NoteCategoryException e) {

        NoteCategoryException exception = new NoteCategoryException(e.getMessage(), e.getStatus());

        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(NoteException.class)
    public ResponseEntity handlerNoteException(NoteException e) {

        NoteException exception = new NoteException(e.getMessage(), e.getStatus());

        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(SuggestionException.class)
    public ResponseEntity handlerSuggestionException(SuggestionException e) {

        SuggestionException exception = new SuggestionException(e.getMessage(), e.getStatus());

        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }
}
