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
}
