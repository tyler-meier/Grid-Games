package ooga.data.exceptions;

/**
 * Exception to help log users in
 */

public class NoUserExistsException extends RuntimeException{
  private String message = "The username %s does not exist";

  public NoUserExistsException(String unknownUsername)
  {
    message = String.format(message, unknownUsername);
  }

  @Override
  public String getMessage() {
    return message;
  }

}
