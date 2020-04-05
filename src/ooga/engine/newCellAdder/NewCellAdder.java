package ooga.engine.newCellAdder;

import ooga.engine.Cell;

public abstract class NewCellAdder {

    public NewCellAdder(){

    }
    /**
     * This method will 're-fill' the board (if the selected game calls for it),
     * once the matches are removed. Depending on the game, the grid will either
     * be filled randomly from the top, or no cells will be added to the grid.
     * @param grid
     * @return the updated grid
     */
    public abstract Cell[][] addCellsToBoard(Cell[][] grid);

    /**
     * This method fills in the bottom of the grid so that only the cells at the top are empty.
     * @param grid
     */
    private void shiftCellsDown(Cell[][] grid){

    }
}
