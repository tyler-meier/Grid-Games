package ooga.engine.validator;

import javafx.css.Match;
import ooga.engine.Cell;
import ooga.engine.EngineBuilder;
import ooga.engine.matchFinder.MatchFinder;

public abstract class Validator {

    public Validator(){

    }
    /**
     * This method determines if the user's input (the tile(s) that they selected)
     * is valid for the specific game they are playing. How the validity is determined
     * is based on if the game is Pair- oriented, or Match - oriented.
     * @param grid
     */
    public abstract boolean checkIsValid(Cell[][] grid, MatchFinder myMatchFinder);
}
