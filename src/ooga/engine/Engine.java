package ooga.engine;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ooga.engine.gridCreator.Grid;
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
    private StringProperty myErrorMessage = new SimpleStringProperty();

    public Engine(Map<String, String> engineAttributes, StringProperty errorMessage) {
        ComponentCreator myComponentCreator = new ComponentCreator(engineAttributes, errorMessage);
        Validator myValidator = myComponentCreator.makeMyValidator();
        MatchFinder myMatchFinder = myComponentCreator.makeMyMatchFinder();
        myGridCreator = myComponentCreator.makeMyGridCreator();
        myGrid = new Grid(engineAttributes, myValidator, myMatchFinder, errorMessage);
        myErrorMessage.bindBidirectional(errorMessage);
    }

    @Override
    public void setupGame(int[][] initialStates, Map<String, String> myGameAttributes, boolean[][] openCells){
        if (initialStates==null) {
            try{
                initialStates = myGridCreator.getInitialConfig(myGameAttributes);
                myGrid.setNewGame(initialStates, myGameAttributes, openCells);
                myGrid.clearInitialMatches();
            } catch (Exception e){
                myErrorMessage.set(e.getMessage());
            }
        } else myGrid.setNewGame(initialStates, myGameAttributes, openCells);
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

    @Override
    public int getLevel() { return myGrid.getLevel(); }

    @Override
    public boolean[][] getOpenCellConfiguration() { return myGrid.getOpenCellConfiguration(); }
}
