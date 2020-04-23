package ooga.engine.matchFinder;

import ooga.engine.Cell;
import ooga.engine.Grid;

import java.util.ArrayList;
import java.util.List;

/**
 * This class finds matches by checking the cells that are
 * neighbors of the cell that is currently open.
 * @author Natalie Novitsky and Tanvi Pabby.
 */
public class FlippedFinder extends MatchFinder {

    /**
     * Finds matches originating at selected cells. Not used for FLippedFinder.
     * @param selected cells
     * @param grid to find other matches
     * @return null for this subclass
     */
    @Override
    public List<Cell> makeMatches(List<Cell> selected, Grid grid) {
        return null;
    }

    /**
     * Finds matches originating anywhere on the Grid. Used to open neighbors of cells in 0 state for Minesweeper.
     * @param grid to find other matches
     * @return cells included in match
     */
    @Override
    public List<Cell> makeMatches(Grid grid){
        List<Cell> cellsToOpen = new ArrayList<>();
        int rows = grid.getRows();
        int cols = grid.getCols();
        Cell cell;
        for (int r = 0; r<rows; r++){
            for (int c=0; c<cols; c++){
                cell = grid.getCell(r, c);
                if (cell.isOpen().get() && cell.getMyState()==0) getUnopenedNeighbors(cell, grid, cellsToOpen);
            }
        }
        return cellsToOpen;
    }

    private List<Cell> getUnopenedNeighbors(Cell cell, Grid grid, List<Cell> cellsToOpen){
        List<Cell> neighbors = new ArrayList<>();
        int row = cell.getRow();
        int col = cell.getColumn();
        Cell next = grid.getCell(row-1, col);
        addCellToList(next, cellsToOpen);
        next = grid.getCell(row+1, col);
        addCellToList(next, cellsToOpen);
        for (int r = row-1; r<=row+1; r++){
            next = grid.getCell(r, col+1);
            addCellToList(next, cellsToOpen);
            next = grid.getCell(r, col-1);
            addCellToList(next, cellsToOpen);        }
        return neighbors;
    }

    private void addCellToList(Cell cell, List<Cell> list){
        if (cell!=null && !cell.isOpen().get() && !list.contains(cell)) list.add(cell);
    }
}
