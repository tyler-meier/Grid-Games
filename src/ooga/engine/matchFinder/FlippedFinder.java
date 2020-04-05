package ooga.engine.matchFinder;

import ooga.engine.Cell;

import java.util.List;

public class FlippedFinder extends MatchFinder {

    public FlippedFinder(){
        super();
    }

    @Override
    public boolean matchesExist(Cell[][] grid) {
        // of the open cells in the grid, check if they match
        // return cellOne.getMyState() == cellTwo.getMyState()
        return false;
    }

    @Override
    public List<int[]> identifyMatches(Cell[][] grid) {
        return null;
    }
}
