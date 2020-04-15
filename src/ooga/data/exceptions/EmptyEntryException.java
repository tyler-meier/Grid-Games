package ooga.data.exceptions;

public class EmptyEntryException extends RuntimeException{

  private String message = "Please enter all fields";

  public EmptyEntryException()
  {

  }
  @Override
  public String getMessage() {
    return message;
  }

}
