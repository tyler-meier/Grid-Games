package ooga.player.exceptions;

public class ImageNotFoundException extends Exception {
    private static final String MESSAGE = "Images are invalid.";
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
