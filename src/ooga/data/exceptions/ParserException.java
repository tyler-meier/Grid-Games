package ooga.data.exceptions;

/**
 * Thrown when we can't read part of a data file
 */
public class ParserException extends RuntimeException {
  private String message = "Data file could not be read: %s";

  public ParserException(Exception e, String fileName)
  {
    super(e);
    message = String.format(message, fileName);
  }

  @Override
  public String getMessage() {
    return message;
  }

}
