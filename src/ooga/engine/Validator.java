package ooga.engine;

public abstract class Validator {
    /**
     * This method determines if the user's input (the tile(s) that they selected)
     * is valid for the specific game they are playing. How the validity is determined
     * is based on if the game is Pair- oriented, or Match - oriented.
     * @param grid
     */
    public abstract boolean checkIsValid(EngineBuilder.Cell[][] grid);
}
