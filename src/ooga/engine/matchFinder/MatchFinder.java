package ooga.engine.matchFinder;

import ooga.engine.Cell;
import ooga.engine.grid.Grid;

import java.util.List;

public abstract class MatchFinder {
    protected int matchLength;

    public MatchFinder(){
    }


    /**
     * This method determines whether or not the movement of the selected cells
     * (whether it be switching their place, or flipping them over), results in
     * matches being made on the board. Returns a list of the cells that are considered
     * to be matching.
     * @param selected
     * @param grid
     * @return
     */
    public abstract List<Cell> makeMatches(List<Cell> selected, Grid grid);

    /**
     * This method checks the entire game grid and determines if there
     * are existing matches. Returns a list of the cells that are considered to be
     * matching.
     * @param grid
     * @return
     */
    public abstract List<Cell> makeMatches(Grid grid);

    public void setMatchLength(int length) { matchLength = length; }
}
