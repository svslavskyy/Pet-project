package tech.pm.edu.web.exception.handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import tech.pm.edu.web.exception.*;
import tech.pm.edu.web.exception.model.ErrorMessage;

import java.time.Instant;

@ControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(NonExistentSessionException.class)
  public ResponseEntity<ErrorMessage> nonExistentSessionException(NonExistentSessionException ex, WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage(
        HttpStatus.NOT_FOUND.value(),
        Instant.now(),
        ex.getMessage(),
        request.getDescription(false));

    return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(SessionExpiredException.class)
  public ResponseEntity<ErrorMessage> sessionExpiredException(SessionExpiredException ex, WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage(
        HttpStatus.FORBIDDEN.value(),
        Instant.now(),
        ex.getMessage(),
        request.getDescription(false));

    return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(GameBlockedException.class)
  public ResponseEntity<ErrorMessage> gameBlockedException(GameBlockedException ex, WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage(
        HttpStatus.FORBIDDEN.value(),
        Instant.now(),
        ex.getMessage(),
        request.getDescription(false));

    return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
  }


  @ExceptionHandler(ProviderBlockedException.class)
  public ResponseEntity<ErrorMessage> providerBlockedException(ProviderBlockedException ex, WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage(
        HttpStatus.FORBIDDEN.value(),
        Instant.now(),
        ex.getMessage(),
        request.getDescription(false));

    return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);

  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException ex, WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage(
        HttpStatus.NOT_FOUND.value(),
        Instant.now(),
        ex.getMessage(),
        request.getDescription(false));

    return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
  }


}
