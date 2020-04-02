package ooga.engine;

import java.util.ArrayList;
import java.util.List;

public interface EngineBuilder {
    /**
     * This method will be called once a player gives input to try and play the game.
     * The method will call other methods within the Engine interface in order to validate the move,
     * identify the cells to be removed, remove the cells, and update the grid. It will return the updated grid.
     * @return
     */
    Cell[][] updateBoard();

    public abstract class MatchFinder {
        /**
         * This method determines whether or not there are matches in the given grid.
         * For games that use are pair-based, the method will determine if, of the
         * 'open' cells, there is a match. For games that are match-based, this
         * method will check the whole grid and identify if there are any existing matches.
         * @param grid
         * @return boolean
         */
        abstract boolean matchesExist(Cell[][] grid);

        /**
         * This method determines which cells in the grid represent a match. So if the game is
         * pair based, the method will just return the coordinates of the cells that were accurately
         * selected by the user, so that they can be removed. For games that are match based,
         * the method will look at all the cells, and return the coordinates of all cells that are
         * the same as at least one of their neighbors.
         * @param grid
         * @return a list of coordinates (list of lists of two integers)
         */
        abstract List<List<Integer>> identifyMatches(Cell[][] grid);
    }

    public abstract class NewCellAdder {
        /**
         * This method will 're-fill' the board (if the selected game calls for it),
         * once the matches are removed. Depending on the game, the grid will either
         * be filled randomly from the top, or no cells will be added to the grid.
         * @param grid
         * @return the updated grid
         */
        abstract Cell[][] addCellsToBoard(Cell[][] grid);
    }

    public abstract class GridCreator {
        /**
         * This method will create the intial configuration of the grid,
         * depending on what kind of initial configuration is specified in the data
         * file/ user interaction with the program - either random, from memory, or loaded.
         * @return
         */
        abstract Cell[][] setUpGrid();
    }

    public class Cell {
        /**
         * This method will remove cells that are considered to be part
         * of a pair, or of a 'match'. When the cell is removed, its point
         * value is also added to the player's score, and if there are power-ups
         * for the specific cell, they are also applied in this method.
         * Removing the cell will just be changing its state to signify that the
         * cell is 'empty'.
         */
        void removeCell() {

        }
    }

    public abstract class PowerUps {
        /**
         * This method will be called when a cell is removed, so that if the
         * cell has a specific power-up, however that power-up is supposed to
         * work, will be implemented when this method is called. For example,
         * if the powerup of a certain cell is to have double points, when that cell
         * is being removed, it will call this abstract method and this method will double the
         * point value that is added to the player's overall score when the cell is removed.
         */
        abstract void applyPowerUps();
    }

    public abstract class Validator {
        /**
         * This method determines if the user's input (the tile(s) that they selected)
         * is valid for the specific game they are playing. How the validity is determined
         * is based on if the game is Pair- oriented, or Match - oriented.
         * @param grid
         */
        abstract boolean checkIsValid(Cell[][] grid);
    }

}
