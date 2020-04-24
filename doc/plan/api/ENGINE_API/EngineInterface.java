public interface EngineInterface{

    /**
     * This method will be called once a player gives input to try and play the game.
     * The method will call other methods within the Engine interface in order to validate the move,
     * identify the cells to be removed, remove the cells, and update the grid. It will return the updated grid.
     * @return
     *
     * @deprecated board automatically updates with binding, no longer needed
     */
    @Deprecated
    Cell[][] updateBoard();


    /**
     * This method will return the current grid with the updated states of all the cells.
     * This method will be so the frontend can get the updated grid from the backend.
     *
     * @return
     *
     * @deprecated new implementation returns Grid object, same functionality
     */
    @Deprecated
    Cell[][] getGrid();

    /**
     * This method is so the current state of the user's game can be saved and loaded later.
     * @return
     *
     * @deprecated functionality completed with 3 calls to get game attributes, grid config, and open cell config
     * since State object was never implemented
     */
    @Deprecated
    State getGameState();

}