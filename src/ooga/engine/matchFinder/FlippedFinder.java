package ooga.engine.matchFinder;

import ooga.engine.Cell;

public class FlippedFinder extends MatchFinder {

    @Override
    public boolean matchesExist(Cell[][] grid) {
        // of the open cells in the grid, check if they match
        // return cellOne.getMyState() == cellTwo.getMyState()
        return false;
    }
}
