import java.util.Map;

/**
 * Interface for Engine class.
 * @author Tanvi and Natalie
 */
public interface EngineBuilder {

    /**
     * This method will return the current grid with the updated states of all the cells.
     * This method will be so the frontend can get the updated grid from the backend.
     * @return current Grid
     */
    Grid getGrid();

    /**
     * This method is so the current state of the user's game can be saved and loaded later.
     * @return map of current attributes
     */
    Map<String, String> getGameAttributes();

    /**
     * Resets the game grid to have the specified state configuration for the cells
     * @param initialConfig 2D array of states or null
     * @param gameAttributes level-specific attributes
     * @param openCells 2D array of initially open cells or null
     */
    void setupGame(int[][] initialConfig, Map<String, String> gameAttributes, boolean[][] openCells);

    /**
     * Returns the configuration of the backend grid in the form of a 2D array.
     * @return 2D array of states
     */
    int[][] getGridConfiguration();

    /**
     * Returns the isOpen state of all the cells in the grid, in the form of a 2D array.
     * @return 2D array of states or null if not applicable
     */
    boolean[][] getOpenCellConfiguration();

    /**
     * Returns the current level of the game that is being played.
     * @return level or -1 if no level started yet
     */
    int getLevel();


}
