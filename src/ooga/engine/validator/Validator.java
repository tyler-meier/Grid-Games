package ooga.engine.validator;


import ooga.engine.Cell;
import ooga.engine.GameProgressManager;
import ooga.engine.matchFinder.MatchFinder;

import java.util.List;

/**
 * This class represents the general Validator, which checks if a move
 * made by the player is considered to be valid for the current game.
 * @author Tanvi Pabby and Natalie Novitsky.
 */
public abstract class Validator {
    protected GameProgressManager myProgressManager;
    protected int time = 1000;

    public Validator(){
    }
    /**
     * This method determines if the user's input (the tile(s) that they selected)
     * is valid for the specific game they are playing. How the validity is determined
     * is based on if the game is Pair- oriented, or Match - oriented.
     */
    public abstract boolean checkIsValid(List<Cell> selected);

    /**
     * This method sets the time for which a cell will be flipped over, if the current game
     * calls for that feature.
     * @param seconds
     */
    public void setTime(double seconds) { time = (int) (1000*seconds); }

    /**
     * This method sets the progress manager of the current game.
     * @param manager
     */
    public void setMyProgressManager(GameProgressManager manager) { myProgressManager = manager;}
}
