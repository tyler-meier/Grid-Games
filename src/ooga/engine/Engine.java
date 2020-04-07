package ooga.engine;

import ooga.engine.grid.Grid;
import ooga.engine.matchFinder.MatchFinder;
import ooga.engine.validator.Validator;

import java.util.List;
import java.util.Map;


/**
 * The purpose of this class is to manage all of the functions of the
 * engine component of the game.
 */
public class Engine implements EngineBuilder {
    private static final String VALIDATOR = "validator";
    private static final String MATCH_FINDER = "matchFinder";
    private static final String NUM_SELECTED_PER_MOVE = "numSelectedPerMove";
    private static final String ADD_NEW_CELLS = "addNewCells";
    private static final String MAX_STATE_NUMBER = "maxStateNumber";
    private static final String HAS_HIDDEN_CELLS = "hasHiddenCells";

    private Grid myGrid;

    public Engine(Map<String, String> myData, int[][] initialConfig) throws Exception {
        ComponentCreator myComponentCreator = new ComponentCreator();
        Validator myValidator = myComponentCreator.makeMyValidator(myData.get(VALIDATOR));
        MatchFinder myMatchFinder = myComponentCreator.makeMyMatchFinder(myData.get(MATCH_FINDER));
        myGrid = new Grid(initialConfig, Integer.parseInt(myData.get(NUM_SELECTED_PER_MOVE)),
                Boolean.parseBoolean(myData.get(ADD_NEW_CELLS)), Integer.parseInt(myData.get(MAX_STATE_NUMBER)),
                Boolean.parseBoolean(myData.get(HAS_HIDDEN_CELLS)));
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
        State curState = new State(myGrid, myGrid.getMyScore());
        return curState;
    }
}
