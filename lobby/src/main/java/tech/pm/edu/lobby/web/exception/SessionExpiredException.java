package tech.pm.edu.lobby.web.exception;

public class SessionExpiredException extends RuntimeException {

  public SessionExpiredException(String message) {
    super(message);
  }


}
