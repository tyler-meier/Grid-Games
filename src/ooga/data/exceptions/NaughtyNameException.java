package ooga.data.exceptions;

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
