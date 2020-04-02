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

    /**
     * This method will return the current grid with the updated states of all the cells.
     * This method will be so the frontend can get the updated grid from the backend.
     * @return
     */
    Cell[][] getGrid();

    /**
     * This method is so the current state of the user's game can be saved and loaded later.
     * @return
     */
    State getGameState();
}
