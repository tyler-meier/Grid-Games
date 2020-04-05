package ooga.engine;

import ooga.data.DataObject;
import ooga.engine.grid.GridCreator;
import ooga.engine.matchFinder.MatchFinder;
import ooga.engine.newCellAdder.NewCellAdder;
import ooga.engine.validator.Validator;

import java.util.List;
import java.util.Map;


/**
 * The purpose of this class is to manage all of the functions of the
 * engine component of the game.
 */
public class Engine implements EngineBuilder {
    private static final String NEW_CELL_ADDER = "newCellAdder";
    private static final String VALIDATOR = "validator";
    private static final String MATCH_FINDER = "matchFinder";

    private Validator myValidator;
    private NewCellAdder myNewCellAdder; // not necessary
    private Cell[][] myGrid;
    private MatchFinder myMatchFinder;
    private ComponentCreator myComponentCreator;
    private int numSelected;

    public Engine(Map<String, String> myData) throws Exception {
        myComponentCreator = new ComponentCreator();
        myNewCellAdder = myComponentCreator.makeMyNewCellAdder(myData.get(NEW_CELL_ADDER)); //FIXME:getting the string that matches the key in the map from data
        myValidator = myComponentCreator.makeMyValidator(myData.get(VALIDATOR));
        myMatchFinder = myComponentCreator.makeMyMatchFinder(myData.get(MATCH_FINDER));
        //buildGrid(myData.getInitialStates(), myData.getOpen()); // have to decide how this info is coming it from dataobject
    }

    private void buildGrid(int[][] initialStates, boolean open){
        myGrid = new Cell[initialStates.length][initialStates[0].length];
        for (int r = 0; r<initialStates.length; r++){
            for (int c=0; c<initialStates[0].length; c++){
                myGrid[r][c] = new Cell(initialStates[r][c], open);
                myGrid[r][c].setSelectionChangeListener(increment -> numSelected += increment? 1 : -1);
            }
        }
    }

    @Override
    public Cell[][] updateBoard() {
        if(!myValidator.checkIsValid(myGrid, myMatchFinder)){
            return myGrid; //if not valid, return the grid as it is
        }
        // remove cells that are considered to be matched
        removeCells(myMatchFinder.identifyMatches(myGrid));
        // new cell adder - this will take care of shifting down the cells and adding in new ones if necessary


        // this should not return a board, the front end will have all the info it needs already
        return new Cell[0][];
    }

    @Override
    public Cell[][] getGrid() {
        return myGrid;
    }

    @Override
    public State getGameState() {
        return null;
    }

    private void removeCells(List<int[]> cellsToRemove){
        // gets a list of coordinates of cells to 'remove' - remove just means to set the state to 0
        // will have to iterate over the whole grid and for each cell, check if its coordinates match the ones in the list
        // if it is a match, call .removeCell() on that cell
    }
}
