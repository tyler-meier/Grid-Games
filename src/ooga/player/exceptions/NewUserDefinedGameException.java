package ooga.player.exceptions;

public class NewUserDefinedGameException extends NumberFormatException {
    private String message = "Incorrect input for making user defined game.";

    @Override
    public String getMessage() {
        return message;
    }

}

