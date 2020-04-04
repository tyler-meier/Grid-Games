package ooga.engine.grid;

import ooga.engine.Cell;

import java.util.ArrayList;
import java.util.List;

public abstract class GridCreator {
    int myNumRows;
    int myNumColumns;
    int myNumStatesOfCells;
    List<Cell> myCells;

    public GridCreator(int numRows, int numColumns, int numStatesOfCells){
        myNumRows = numRows;
        myNumColumns = numColumns;
        myNumStatesOfCells = numStatesOfCells;
        myCells = new ArrayList<>();
    }
    /**
     * This method will create the initial configuration of the grid,
     * depending on what kind of initial configuration is specified in the data
     * file/ user interaction with the program - either random, from memory, or loaded.
     * @return
     */
    public  Cell[][] setUpGrid(){
        makeMyCells();
        Cell[][] grid = new Cell[myNumRows][myNumColumns];
        for(int row = 0; row < myNumRows; row++){
            for(int col = 0; col < myNumColumns; col++){
                grid[row][col] = getRandomCell();
                grid[row][col].setCoordinates(row,col);
            }
        }
        return grid;
    }

    /**
     * This method generates the cells that will be included in the
     * initial grid for this game.
     */
    public abstract void makeMyCells();

    /**
     * Selects a random cell from the array of cells, and then removes the cell.
     * @return
     */
    private Cell getRandomCell(){
        int curSize = myCells.size(); // get the current length of the array of cells
        int randomNum = (int) ((Math.random() * curSize) + 1); //FIXME: need to verify that this gives the numbers we want
        Cell ret = myCells.get(randomNum);
        myCells.remove(randomNum);
        return ret;
    }
}
