package tech.pm.edu.lobby.web.exception;

public class NonExistentSessionException extends RuntimeException {

  public NonExistentSessionException(String message) {
    super(message);
  }


}
