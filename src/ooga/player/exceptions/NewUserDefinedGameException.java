package ooga.player.exceptions;

public class NewUserDefinedGameException extends RuntimeException {
    private String message = "Incorrect input for making user defined game.";

    public NewUserDefinedGameException() { }
    @Override
    public String getMessage() {
        return message;
    }

}

