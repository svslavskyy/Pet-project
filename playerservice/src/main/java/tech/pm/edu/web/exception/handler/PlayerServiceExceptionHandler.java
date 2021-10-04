package tech.pm.edu.web.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import tech.pm.edu.web.exception.*;
import tech.pm.edu.web.exception.model.ErrorMessage;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class PlayerServiceExceptionHandler {

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorMessage> entityNotFoundException(EntityNotFoundException ex, WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage(
            HttpStatus.NOT_FOUND.value(),
            Instant.now(),
            ex.getMessage(),
            request.getDescription(false));

    return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(PageNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorMessage> pageNotFoundException(PageNotFoundException ex, WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage(
            HttpStatus.NOT_FOUND.value(),
            Instant.now(),
            ex.getMessage(),
            request.getDescription(false));

    return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(PlayerAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ResponseEntity<ErrorMessage> playerAlreadyExistsException(PlayerAlreadyExistsException ex, WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage(
            HttpStatus.UNAUTHORIZED.value(),
            Instant.now(),
            ex.getMessage(),
            request.getDescription(false));

    return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(SessionKeyMismatchException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorMessage> sessionKeyMismatchException(SessionKeyMismatchException ex, WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage(
            HttpStatus.NOT_FOUND.value(),
            Instant.now(),
            ex.getMessage(),
            request.getDescription(false));

    return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NoSuchAlgorithmException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ErrorMessage> noSuchAlgorithmException(NoSuchAlgorithmException ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            Instant.now(),
            ex.getMessage(),
            request.getDescription(false));

    return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(PasswordNotMatchException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ResponseEntity<ErrorMessage> passwordMismatchException(PasswordNotMatchException ex, WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage(
            HttpStatus.UNAUTHORIZED.value(),
            Instant.now(),
            ex.getMessage(),
            request.getDescription(false));

    return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(SessionExpiredException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ResponseEntity<ErrorMessage> sessionExpiredException(SessionExpiredException ex, WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage(
            HttpStatus.UNAUTHORIZED.value(),
            Instant.now(),
            ex.getMessage(),
            request.getDescription(false));

    return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<ErrorMessage> badRequest(ConstraintViolationException ex, WebRequest request) {

    Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

    Set<String> messages = new HashSet<>(constraintViolations.size());
    messages.addAll(constraintViolations.stream()
            .map(constraintViolation -> String.format("%s", constraintViolation.getMessage()))
            .collect(Collectors.toList()));

    ErrorMessage errorMessage = new ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            Instant.now(),
            messages.toString(),
            request.getDescription(false));

    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(PlayerBlockedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ResponseEntity<ErrorMessage> playerBlockedException(PlayerBlockedException ex, WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage(
            HttpStatus.FORBIDDEN.value(),
            Instant.now(),
            ex.getMessage(),
            request.getDescription(false));

    return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
  }


}
