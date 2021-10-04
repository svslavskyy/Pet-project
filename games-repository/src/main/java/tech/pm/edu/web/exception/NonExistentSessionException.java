package tech.pm.edu.web.exception;


public class NonExistentSessionException extends RuntimeException {

  public NonExistentSessionException(String message) {
    super(message);
  }


}