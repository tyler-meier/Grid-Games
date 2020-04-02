package ooga.engine;

public abstract class GridCreator {
    /**
     * This method will create the intial configuration of the grid,
     * depending on what kind of initial configuration is specified in the data
     * file/ user interaction with the program - either random, from memory, or loaded.
     * @return
     */
    public abstract Cell[][] setUpGrid();
}
