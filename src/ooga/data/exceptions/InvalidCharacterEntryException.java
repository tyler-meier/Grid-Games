package ooga.data.exceptions;

/**
 * If the user enters a character we do not accept to create a profile,
 * this error will help them
 */
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
