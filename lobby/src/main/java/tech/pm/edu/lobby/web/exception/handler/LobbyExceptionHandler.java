package tech.pm.edu.lobby.web.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import tech.pm.edu.lobby.web.exception.EntityBlockedException;
import tech.pm.edu.lobby.web.exception.EntityNotFoundException;
import tech.pm.edu.lobby.web.exception.GameAlreadyExistsException;
import tech.pm.edu.lobby.web.exception.NonExistentSessionException;
import tech.pm.edu.lobby.web.exception.PageNotFoundException;
import tech.pm.edu.lobby.web.exception.SessionExpiredException;
import tech.pm.edu.lobby.web.exception.UnavailableGameException;
import tech.pm.edu.lobby.web.exception.model.ErrorMessage;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class LobbyExceptionHandler {

  @ExceptionHandler(NonExistentSessionException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorMessage> nonExistentSessionException(NonExistentSessionException ex, WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage(
      HttpStatus.NOT_FOUND.value(),
      Instant.now(),
      ex.getMessage(),
      request.getDescription(false));

    return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(SessionExpiredException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ResponseEntity<ErrorMessage> sessionExpiredException(SessionExpiredException ex, WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage(
      HttpStatus.FORBIDDEN.value(),
      Instant.now(),
      ex.getMessage(),
      request.getDescription(false));

    return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(EntityBlockedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ResponseEntity<ErrorMessage> entityBlockedException(EntityBlockedException ex, WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage(
      HttpStatus.FORBIDDEN.value(),
      Instant.now(),
      ex.getMessage(),
      request.getDescription(false));

    return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(UnavailableGameException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ResponseEntity<ErrorMessage> unavailableGameException(UnavailableGameException ex, WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage(
      HttpStatus.FORBIDDEN.value(),
      Instant.now(),
      ex.getMessage(),
      request.getDescription(false));

    return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
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

  @ExceptionHandler(GameAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ResponseEntity<ErrorMessage> gameAlreadyExistsException(GameAlreadyExistsException ex, WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage(
      HttpStatus.UNAUTHORIZED.value(),
      Instant.now(),
      ex.getMessage(),
      request.getDescription(false));

    return new ResponseEntity<>(errorMessage, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<ErrorMessage> gameAlreadyExistsException(EntityNotFoundException ex, WebRequest request) {
    ErrorMessage errorMessage = new ErrorMessage(
      HttpStatus.NOT_FOUND.value(),
      Instant.now(),
      ex.getMessage(),
      request.getDescription(false));

    return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
  }


}
