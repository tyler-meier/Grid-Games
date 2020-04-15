package ooga.data.exceptions;

public class InvalidCharacterEntryException extends RuntimeException {

  private String message = "The character (%s) is invalid";

  public InvalidCharacterEntryException(String invalidCharacter)
  {
        message = String.format(message, invalidCharacter);
  }
  @Override
  public String getMessage() {
    return message;
  }

}
