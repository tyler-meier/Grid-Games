package ooga.engine;

import ooga.data.DataObject;
import ooga.engine.grid.GridCreator;
import ooga.engine.matchFinder.MatchFinder;
import ooga.engine.newCellAdder.NewCellAdder;
import ooga.engine.validator.Validator;

import java.util.List;


/**
 * The purpose of this class is to manage all of the functions of the
 * engine component of the game.
 */
public class Engine implements EngineBuilder {
    private GridCreator myGridCreator;
    private Validator myValidator;
    private NewCellAdder myNewCellAdder;
    private Cell[][] myGrid;
    private MatchFinder myMatchFinder;
    private ComponentCreator myComponentCreator;

    public Engine(DataObject myData){
        myComponentCreator = new ComponentCreator();
        myNewCellAdder = myComponentCreator.makeMyNewCellAdder();
        myGridCreator = myComponentCreator.makeMyGridCreator(); //the number of rows, columns, and cell states are also going to be given as a parameter here
        myValidator = myComponentCreator.makeMyValidator();
        myMatchFinder = myComponentCreator.makeMyMatchFinder();
        myGrid = myGridCreator.setUpGrid();
    }

    @Override
    public Cell[][] updateBoard() {
        if(!myValidator.checkIsValid(myGrid, myMatchFinder)){
            return myGrid; //if not valid, return the grid as it is
        }
        // remove cells that are considered to be matched
        removeCells(myMatchFinder.identifyMatches(myGrid));
        // new cell adder - this will take care of shifting down the cells and adding in new ones if necessary

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
