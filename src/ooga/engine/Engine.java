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

    @Override
    public void setupGame(int[][] initialStates, Map<String, String> myGameAttributes, boolean[][] openCells){
        myGrid.setNewGame(initialStates, myGameAttributes, openCells);
    }

    @Override
    public Grid getGrid() {
        return myGrid;
    }

    @Override
    public Map<String, String> getGameAttributes() {
        return myGrid.getGameAttributes();
    }

    @Override
    public int[][] getGridConfiguration() { return myGrid.getGridConfiguration(); }

    public int getLevel() { return myGrid.getLevel(); }

    public boolean[][] getOpenCellConfiguration() { return myGrid.getOpenCellConfiguration(); }
}
