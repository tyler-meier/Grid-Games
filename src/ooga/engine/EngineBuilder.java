package ooga.engine;


import ooga.engine.grid.Grid;

import java.util.Map;

public interface EngineBuilder {

    /**
     * This method will return the current grid with the updated states of all the cells.
     * This method will be so the frontend can get the updated grid from the backend.
     * @return
     */
    Grid getGrid();

    /**
     * This method is so the current state of the user's game can be saved and loaded later.
     * @return
     */
    State getGameState();

    /**
     * Resets the game grid to have the specified state configuration for the cells
     * @param initialConfig
     */
    void setupGame(int[][] initialConfig, Map<String, String> gameAttributes);
}
