package ooga.data.exceptions;

/**
 * Exception to help create users that don't already exist
 */
public class UserAlreadyExistsException extends RuntimeException {

  private String message = "The username %s already exists";

  public UserAlreadyExistsException(String unknownUsername)
  {
    message = String.format(message, unknownUsername);
  }

  @Override
  public String getMessage() {
    return message;
  }

}
