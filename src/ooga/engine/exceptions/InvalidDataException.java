package ooga.engine.exceptions;

/**
 * Error thrown when data for setup is not valid for any reason.
 */
public class InvalidDataException extends  RuntimeException {
    private static final String MESSAGE = "bad data :((";

    /**
     * String representation of message.
     * @return message
     */
    public String toString() { return MESSAGE; }
}
