package ooga.engine;


import ooga.engine.gridCreator.Grid;

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
    Map<String, String> getGameAttributes();

    /**
     * Resets the game grid to have the specified state configuration for the cells
     * @param initialConfig
     */
    void setupGame(int[][] initialConfig, Map<String, String> gameAttributes, boolean[][] openCells);

    /**
     * Returns the configuration of the backend grid in the form of a 2D array.
     * @return
     */
    int[][] getGridConfiguration();

    /**
     * Returns the isOpen state of all the cells in the grid, in the form of a 2D array.
     * @return
     */
    boolean[][] getOpenCellConfiguration();


}
