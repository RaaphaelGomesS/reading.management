package tech.gomes.reading.management.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tech.gomes.reading.management.exception.*;

@Slf4j
@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<String> handlerApplicationException(ApplicationException e) {

        ApplicationException exception = new ApplicationException(e.getMessage(), e.getStatus());

        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(UserException.class)
    public ResponseEntity<String> handlerUserException(UserException e) {

        UserException exception = new UserException(e.getMessage(), e.getStatus());

        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(LibraryException.class)
    public ResponseEntity<String> handlerLibraryException(LibraryException e) {

        LibraryException exception = new LibraryException(e.getMessage(), e.getStatus());

        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(BookException.class)
    public ResponseEntity<String> handlerBookException(BookException e) {

        BookException exception = new BookException(e.getMessage(), e.getStatus());

        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(BookTemplateException.class)
    public ResponseEntity<String> handlerBookTemplateException(BookTemplateException e) {

        BookTemplateException exception = new BookTemplateException(e.getMessage(), e.getStatus());

        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(NoteCategoryException.class)
    public ResponseEntity<String> handlerNoteCategoryException(NoteCategoryException e) {

        NoteCategoryException exception = new NoteCategoryException(e.getMessage(), e.getStatus());

        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(NoteException.class)
    public ResponseEntity<String> handlerNoteException(NoteException e) {

        NoteException exception = new NoteException(e.getMessage(), e.getStatus());

        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(SuggestionException.class)
    public ResponseEntity<String> handlerSuggestionException(SuggestionException e) {

        SuggestionException exception = new SuggestionException(e.getMessage(), e.getStatus());

        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }

    @ExceptionHandler(FileException.class)
    public ResponseEntity<String> handlerFileException(FileException e) {

        FileException exception = new FileException(e.getMessage(), e.getStatus());

        return ResponseEntity.status(exception.getStatus()).body(exception.getMessage());
    }
}
