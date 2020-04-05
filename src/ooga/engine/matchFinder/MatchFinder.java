package ooga.engine.matchFinder;

import ooga.engine.Cell;
import java.util.List;

public abstract class MatchFinder {
    /**
     * This method determines whether or not there are matches in the given grid.
     * For games that use are pair-based, the method will determine if, of the
     * 'open' cells, there is a match. For games that are match-based, this
     * method will check the whole grid and identify if there are any existing matches.
     * @param grid
     * @return boolean
     */
    public abstract boolean matchesExist(Cell[][] grid);

    public abstract List<int[]> identifyMatches(Cell[][] grid);
}
