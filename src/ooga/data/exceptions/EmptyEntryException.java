package ooga.data.exceptions;

/**
 * Exception to notify user when they have not entered all
 * fields when creating a profile
 */
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
