package tech.pm.edu.web.exception;

public class SessionExpiredException extends RuntimeException {

  public SessionExpiredException(String message) {
    super(message);
  }


}
