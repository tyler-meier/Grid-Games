package ooga.engine;

import javafx.beans.property.StringProperty;
import ooga.engine.grid.Grid;
import ooga.engine.matchFinder.MatchFinder;
import ooga.engine.validator.Validator;

import java.util.Map;


/**
 * The purpose of this class is to manage all of the functions of the
 * engine component of the game.
 */
public class Engine implements EngineBuilder {
    private static final String VALIDATOR = "Validator";
    private static final String MATCH_FINDER = "MatchFinder";

    private Grid myGrid;

    public Engine(Map<String, String> engineAttributes, StringProperty errorMessage) {
        ComponentCreator myComponentCreator = new ComponentCreator(errorMessage);
        Validator myValidator = myComponentCreator.makeMyValidator(engineAttributes.get(VALIDATOR));
        MatchFinder myMatchFinder = myComponentCreator.makeMyMatchFinder(engineAttributes.get(MATCH_FINDER));
        myGrid = new Grid(engineAttributes, myValidator, myMatchFinder, errorMessage);
    }

    // use for reset as well
    public void setupGame(int[][] initialStates, Map<String, String> myGameAttributes){
        myGrid.setNewGame(initialStates, myGameAttributes);
    }

    @Override
    public Grid getGrid() {
        return myGrid;
    }

    public Map<String, String> getGameAttributes() {
        return myGrid.getGameAttributes();
    }

    /**
     * Returns the configuration of the backend grid in the form of a 2D array.
     * @return
     */
    public int[][] getGridConfiguration() { return myGrid.getGridConfiguration(); }
}
