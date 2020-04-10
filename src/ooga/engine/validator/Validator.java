package ooga.engine.validator;


import ooga.engine.Cell;
import ooga.engine.GameProgressManager;
import ooga.engine.matchFinder.MatchFinder;

import java.util.List;

public abstract class Validator {

    public Validator(){
    }
    /**
     * This method determines if the user's input (the tile(s) that they selected)
     * is valid for the specific game they are playing. How the validity is determined
     * is based on if the game is Pair- oriented, or Match - oriented.
     */
    public abstract boolean checkIsValid(List<Cell> selected, GameProgressManager myProgressManager);
}
