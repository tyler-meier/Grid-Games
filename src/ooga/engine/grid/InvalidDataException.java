package ooga.engine.grid;

public class InvalidDataException extends  RuntimeException {
    private static final String MESSAGE = "bad data :((";
    public String toString() { return MESSAGE; }
}
