package ooga.engine;

import ooga.engine.exceptions.InvalidDataException;
import ooga.engine.gridCreator.GridCreator;
import ooga.engine.matchFinder.MatchFinder;
import ooga.engine.validator.Validator;

import java.util.Map;


/**
 * The purpose of this class is to manage all of the functions of the
 * engine component of the game.
 * @author Tanvi Pabby and Natalie Novitsky.
 */
public class Engine implements EngineBuilder {
    private Grid myGrid;
    private GridCreator myGridCreator;

    /**
     * Builds a new Engine with given attributes.
     * @param engineAttributes map with details such as type of Validator, MatchFinder, GridCreator, etc that are
     *                         constant between different levels of the game
     */
    public Engine(Map<String, String> engineAttributes) {
        ComponentCreator myComponentCreator = new ComponentCreator(engineAttributes);
        Validator myValidator = myComponentCreator.makeMyValidator();
        MatchFinder myMatchFinder = myComponentCreator.makeMyMatchFinder();
        myGridCreator = myComponentCreator.makeMyGridCreator();
        myGrid = new Grid(engineAttributes, myValidator, myMatchFinder);
    }

    /**
     * Sets up a new level of the game in the Grid. Builds an initial configuration of cells if
     * not provided with one.
     * @param initialStates null or 2D array of states
     * @param myGameAttributes map with details such as target score, loss stat, etc that change each level
     * @param openCells null of 2D array of booleans describing which cells start open (if not all open or all hidden)
     */
    @Override
    public void setupGame(int[][] initialStates, Map<String, String> myGameAttributes, boolean[][] openCells){
        if (initialStates==null) {
            try{
                initialStates = myGridCreator.getInitialConfig(myGameAttributes);
                myGrid.setNewGame(initialStates, myGameAttributes, openCells);
                myGrid.clearInitialMatches();
            } catch (Exception e){
                throw new InvalidDataException();
            }
        } else myGrid.setNewGame(initialStates, myGameAttributes, openCells);
    }

    /**
     * Gets current Grid to bind with front end
     * @return Grid
     */
    @Override
    public Grid getGrid() {
        return myGrid;
    }

    /**
     * Gets current level-specific attributes to save a game state.
     * @return current attributes
     */
    @Override
    public Map<String, String> getGameAttributes() {
        return myGrid.getGameAttributes();
    }

    /**
     * Gets current state configuration to save a game state.
     * @return 2D array of states
     */
    @Override
    public int[][] getGridConfiguration() { return myGrid.getGridConfiguration(); }

    /**
     * Gets current level of game or -1 if not yet initialized.
     * @return level or -1
     */
    @Override
    public int getLevel() { return myGrid.getLevel(); }

    /**
     * Gets current open cell configuration to save a game state if game has hidden cells or null if no hidden cells.
     * @return null or 2D array of booleans (true = open)
     */
    @Override
    public boolean[][] getOpenCellConfiguration() { return myGrid.getOpenCellConfiguration(); }
}
