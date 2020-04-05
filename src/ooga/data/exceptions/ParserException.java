package ooga.data.exceptions;

import javafx.beans.property.StringProperty;

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
