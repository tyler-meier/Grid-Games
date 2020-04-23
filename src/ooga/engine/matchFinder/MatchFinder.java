package ooga.engine.matchFinder;

import ooga.engine.Cell;
import ooga.engine.Grid;

import java.util.List;

/**
 * This class represents the general class for finding matches in a grid.
 * @author Tanvi Pabby and Natalie Novitsky.
 */
public abstract class MatchFinder {
    protected static int DEFAULT_MATCH_LENGTH = 3;

    /**
     * This method determines whether or not the movement of the selected cells
     * (whether it be switching their place, or flipping them over), results in
     * matches being made on the board.
     * @param selected cells
     * @param grid to find other matches
     * @return list of cells included in matches found
     */
    public abstract List<Cell> makeMatches(List<Cell> selected, Grid grid);

    /**
     * This method checks the entire game grid and determines if there
     * are existing matches. Returns a .
     * @param grid to find other matches
     * @return list of the cells that are considered to be matching
     */
    public abstract List<Cell> makeMatches(Grid grid);
}
