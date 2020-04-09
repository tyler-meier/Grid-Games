package ooga.engine.matchFinder;

import ooga.engine.Cell;
import ooga.engine.grid.Grid;

import java.util.ArrayList;
import java.util.List;

public class FlippedFinder extends MatchFinder {

    @Override
    public List<Cell> makeMatches(List<Cell> selected, Grid grid) {
        return null;
    }

    @Override
    public List<Cell> makeMatches(Grid grid){
        List<Cell> cellsToOpen = new ArrayList<>();
        int rows = grid.getRows();
        int cols = grid.getCols();
        Cell cell;
        for (int r = 0; r<rows; r++){
            for (int c=0; c<cols; c++){
                cell = grid.getCell(r, c);
                if (cell.getMyState()==0) cellsToOpen.addAll(getUnopenedNeighbors(cell, grid));
            }
        }
        return cellsToOpen;
    }

    private List<Cell> getUnopenedNeighbors(Cell cell, Grid grid){
        List<Cell> neighbors = new ArrayList<>();
        int row = cell.getRow();
        int col = cell.getColumn();
        Cell next = grid.getCell(row-1, col);
        if (next!=null && !next.isOpen().get()) neighbors.add(next);
        next = grid.getCell(row+1, col);
        if (next!=null && !next.isOpen().get()) neighbors.add(next);
        for (int r = row-1; r<=row+1; r++){
            next = grid.getCell(r, col+1);
            if (next!=null && !next.isOpen().get()) neighbors.add(next);
            next = grid.getCell(r, col-1);
            if (next!=null && !next.isOpen().get()) neighbors.add(next);
        }
        return neighbors;
    }
}
