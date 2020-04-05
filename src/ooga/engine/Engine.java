package ooga.engine;

import ooga.engine.grid.Grid;
import ooga.engine.matchFinder.MatchFinder;
import ooga.engine.validator.Validator;
import java.util.Map;


/**
 * The purpose of this class is to manage all of the functions of the
 * engine component of the game.
 */
public class Engine implements EngineBuilder {
    private static final String VALIDATOR = "validator";
    private static final String MATCH_FINDER = "matchFinder";

    private Grid myGrid;

    public Engine(Map<String, String> myData, int[][] initialConfig) throws Exception {
        ComponentCreator myComponentCreator = new ComponentCreator();
        Validator myValidator = myComponentCreator.makeMyValidator(myData.get(VALIDATOR));
        MatchFinder myMatchFinder = myComponentCreator.makeMyMatchFinder(myData.get(MATCH_FINDER));
        //myGrid = new Grid(initialConfig, numSelectedPerMove, addNewCells, maxStateNumber, hasHiddenCells);
    }

    public void resetGrid(int[][] initialConfig){
        myGrid.resetGrid(initialConfig);
    }

    @Override
    public Grid getGrid() {
        return myGrid;
    }

    @Override
    public State getGameState() {
        return null;
    }
}
