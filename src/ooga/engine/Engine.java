package ooga.engine;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ooga.engine.grid.Grid;
import ooga.engine.matchFinder.MatchFinder;
import ooga.engine.validator.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The purpose of this class is to manage all of the functions of the
 * engine component of the game.
 */
public class Engine implements EngineBuilder {
    private static final String VALIDATOR = "validator";
    private static final String MATCH_FINDER = "matchFinder";
    private Validator myValidator;
    private MatchFinder myMatchFinder;
    // bind to front end error message
    private StringProperty errorMessage = new SimpleStringProperty();

    private Grid myGrid;

    public Engine(Map<String, String> engineAttributes, StringProperty errorMessage) {
        this.errorMessage.bindBidirectional(errorMessage);
        ComponentCreator myComponentCreator = new ComponentCreator();
        try{
            myValidator = myComponentCreator.makeMyValidator(engineAttributes.get(VALIDATOR));
            myMatchFinder = myComponentCreator.makeMyMatchFinder(engineAttributes.get(MATCH_FINDER));
        } catch (Exception e){
            // is this error handling okay?
            errorMessage.set(e.getMessage());
        }
        myGrid = new Grid(engineAttributes);
    }

    // use for reset as well
    public void setupGame(int[][] initialStates, Map<String, String> myGameAttributes){
        myGrid.setNewGame(initialStates, myGameAttributes);
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
