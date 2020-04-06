package ooga.data.exceptions;

public class IncorrectPasswordException extends RuntimeException {

  private String message = "The password %s does not match your username";

  public IncorrectPasswordException(String incorrectPassword)
  {
    message = String.format(message, incorrectPassword);
  }

  @Override
  public String getMessage() {
    return message;
  }
}
