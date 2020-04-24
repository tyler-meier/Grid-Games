package ooga.data.exceptions;

/**
 * Thrown when the path for a given level does not exist, used to
 * notify when you have beat the game
 */
public class LevelNotFoundException extends RuntimeException {

  private String message = "Level %d for %s doesn't exist";

  public LevelNotFoundException(Exception e, int level, String game)
  {
    super(e);
    String.format(message, level, game);
  }
  @Override
  public String getMessage() {
    return message;
  }

}
