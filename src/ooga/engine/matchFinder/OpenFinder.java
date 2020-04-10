package ooga.engine.matchFinder;


import ooga.engine.Cell;
import ooga.engine.grid.Grid;

import java.util.ArrayList;
import java.util.List;

public class OpenFinder extends MatchFinder {

    public OpenFinder(){
        super();
    }

    @Override
    public List<Cell> makeMatches(Grid grid) {
        // loop over all cells, if >x in a row of same kind, set these cells to -1
        List<Cell> matchedCells = new ArrayList<>();
        int rows = grid.getRows();
        int cols = grid.getCols();
        Cell cell;
        for (int r = 0; r<rows; r++){
            for (int c=0; c<cols; c++){
                cell = grid.getCell(r, c);
                matchedCells.addAll(getMatches(cell, grid));
            }
        }
        return matchedCells;
    }

    public List<Cell> makeMatches(List<Cell> selected, Grid grid){
        System.out.println("Inside the TWO PARAMETER makeMatches method");
        Cell first = selected.get(0);
        Cell second = selected.get(1);
        List<Cell> matchedCells = new ArrayList<>();
        for (Cell cell:selected) {
            matchedCells.addAll(getMatches(cell, grid));
        }
        System.out.println("This is the size of matched cell array: " + matchedCells.size());
        if (matchedCells.size()==0){
            first.swap(second);
            System.out.println("State of first cell after swapping back bc invalid move: " + first.getMyState());
            System.out.println("State of second cell after swapping back bc invalid move: " + second.getMyState());
        }

        return matchedCells;
    }

    //FIXME: for some reason, this method keeps repeating
    private List<Cell> getMatches(Cell cell, Grid grid){
        System.out.println("About to get matches for the grid");
        System.out.println("This is the cell being passed to vertical matches: row = " + cell.getRow() + " col = " +cell.getColumn());
        List<Cell> allMatches = getVerticalMatches(cell, grid);
        System.out.println("sucessfully got VERTICAL MATCHES");
        allMatches.addAll(getHorizontalMatches(cell, grid));
        System.out.println("sucessfully got HORIZONTAL MATCHES");
        return allMatches;
    }

    private List<Cell> getVerticalMatches(Cell cell, Grid grid){
        List<Cell> verticalMatches = new ArrayList<>();
        verticalMatches.add(cell);
        int row = cell.getRow()-1;
        int col = cell.getColumn();
        Cell currCell;
        while (inBounds(row,col,grid) && (currCell = grid.getCell(row,col))!=null && currCell.getMyState()==cell.getMyState()) {
            verticalMatches.add(currCell);
            row--;
        }
        row = cell.getRow()+1;
        while (inBounds(row,col,grid) && (currCell = grid.getCell(row,col))!=null && currCell.getMyState()==cell.getMyState()) {
            verticalMatches.add(currCell);
            row++;
        }
        if (verticalMatches.size()<matchLength) {
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
        while (inBounds(row,col,grid) && (currCell = grid.getCell(row,col))!=null && currCell.getMyState()==cell.getMyState()) {
            horizontalMatches.add(currCell);
            col--;
        }
        col = cell.getColumn()+1;
        while (inBounds(row,col,grid) &&(currCell = grid.getCell(row,col))!=null && currCell.getMyState()==cell.getMyState()) {
            horizontalMatches.add(currCell);
            row++;
        }
        if (horizontalMatches.size()<matchLength) horizontalMatches.clear();
        return horizontalMatches;
    }

    private boolean inBounds(int row, int col, Grid grid){
        return (row < grid.getRows() && col< grid.getCols());
    }
}
