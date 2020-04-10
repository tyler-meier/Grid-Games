package ooga.data.exceptions;

/**
 * Exception to help log users in
 */
public class IncorrectPasswordException extends RuntimeException {

  private String message = "Incorrect Password %s ";

  public IncorrectPasswordException(String incorrectPassword)
  {
    message = String.format(message, incorrectPassword);
  }

  @Override
  public String getMessage() {
    return message;
  }
}
