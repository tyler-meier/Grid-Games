package ooga.engine.matchFinder;


import ooga.engine.Cell;
import ooga.engine.Grid;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class finds matches based on whether or not there are at least
 * three cells next to each other of the same state.
 * @author Natalie Novitsky and Tanvi Pabby.
 */
public class OpenFinder extends MatchFinder {
    private static final int SECOND = 1000;

    /**
     * Finds matches across entire grid.
     * @param grid to find other matches
     * @return cells included in matches
     */
    @Override
    public List<Cell> makeMatches(Grid grid) {
        List<Cell> matchedCells = new ArrayList<>();
        int rows = grid.getRows();
        int cols = grid.getCols();
        Cell cell;
        for (int r = 0; r<rows; r++){
            for (int c=0; c<cols; c++){
                cell = grid.getCell(r, c);
                if(!matchedCells.contains(cell)){
                    matchedCells.addAll(getMatches(cell, grid));
                } } }
        return matchedCells;
    }

    /**
     * Finds matches originating in selected cells.
     * @param selected cells
     * @param grid to find other matches
     * @return cells included in matches
     */
    public List<Cell> makeMatches(List<Cell> selected, Grid grid){
        Cell first = selected.get(0);
        Cell second = selected.get(1);
        List<Cell> matchedCells = new ArrayList<>();
        for (Cell cell:selected) {
            matchedCells.addAll(getMatches(cell, grid));
        }
        if (matchedCells.size()==0){
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    first.swap(second);
                }
            }, SECOND);
        }

        return matchedCells;
    }

    private List<Cell> getMatches(Cell cell, Grid grid){
        List<Cell> allMatches = getVerticalMatches(cell, grid);
        allMatches.addAll(getHorizontalMatches(cell, grid));
        return allMatches;
    }

    private List<Cell> getVerticalMatches(Cell cell, Grid grid){
        List<Cell> verticalMatches = new ArrayList<>();
        verticalMatches.add(cell);
        int row = cell.getRow()-1;
        int col = cell.getColumn();
        Cell currCell;
        while (cell.getMyState() != -1 && inBounds(row,col,grid) && (currCell = grid.getCell(row,col))!=null && currCell.getMyState()==cell.getMyState()) {
            verticalMatches.add(currCell);
            row--;
        }
        row = cell.getRow()+1;
        while (cell.getMyState() != -1 && inBounds(row,col,grid) && (currCell = grid.getCell(row,col))!=null && currCell.getMyState()==cell.getMyState()) {
            verticalMatches.add(currCell);
            row++;
        }
        if (verticalMatches.size()< DEFAULT_MATCH_LENGTH) {
            verticalMatches.clear();
        }
        return verticalMatches;
    }

    private List<Cell> getHorizontalMatches(Cell cell, Grid grid){
        List<Cell> horizontalMatches = new ArrayList<>();
        horizontalMatches.add(cell);
        int row = cell.getRow();
        int col = cell.getColumn()-1;
        Cell currCell;
        while (cell.getMyState() != -1 && inBounds(row,col,grid) && (currCell = grid.getCell(row,col))!=null && currCell.getMyState()==cell.getMyState()) {
            horizontalMatches.add(currCell);
            col--;
        }
        col = cell.getColumn()+1;
        while (cell.getMyState() != -1 && inBounds(row,col,grid) &&(currCell = grid.getCell(row,col))!=null && currCell.getMyState()==cell.getMyState()) {
            horizontalMatches.add(currCell);
            col++;
        }
        if (horizontalMatches.size()< DEFAULT_MATCH_LENGTH){
            horizontalMatches.clear();
        }
        return horizontalMatches;
    }

    private boolean inBounds(int row, int col, Grid grid){
        return (row < grid.getRows() && row > -1 && col< grid.getCols() && col > -1);
    }
}
