package ooga.data.exceptions;

/**
 * Helps the parser give the user indication that the grid was
 * not set up properly. This will be thrown when the config grid
 * does not match the rows and columns
 */
public class GridReadingException extends RuntimeException {

  private String message = "Invalid rows/cols for %s grid";

  public GridReadingException(Exception e, String gridType)
  {
    super(e);
    message = String.format(message, gridType);
  }

  @Override
  public String getMessage()
  {
    return message;
  }



}
