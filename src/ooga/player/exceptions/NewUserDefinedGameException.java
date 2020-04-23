package ooga.player.exceptions;

public class NewUserDefinedGameException extends NumberFormatException {
    private static final String message = "Incorrect input for making user defined game.";

    @Override
    public String getMessage() {
        return message;
    }
}

