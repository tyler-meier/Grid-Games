package ooga.engine.matchFinder;

import ooga.engine.Cell;
import ooga.engine.grid.Grid;

import java.util.List;

public abstract class MatchFinder {
    protected int matchLength;

    public MatchFinder(){

    }


    /**
     * This method determines whether or not there are matches in the given grid.
     * For games that use are pair-based, the method will determine if, of the
     * 'open' cells, there is a match. For games that are match-based, this
     * method will check the whole grid and identify if there are any existing matches.
     * @return boolean
     */
    public abstract List<Cell> makeMatches(List<Cell> selected, Grid grid);

    public abstract List<Cell> makeMatches(Grid grid);

    public void setMatchLength(int length) { matchLength = length; }
}
