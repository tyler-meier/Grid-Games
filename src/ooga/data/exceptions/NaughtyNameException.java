package ooga.data.exceptions;

/**
 * Thrown when the user tries to create a profile with a word we
 * do not allow
 */
public class NaughtyNameException extends RuntimeException {

  private String message = "Your username or password is naughty";

  public NaughtyNameException()
  {

  }
  @Override
  public String getMessage() {
    return message;
  }

}
