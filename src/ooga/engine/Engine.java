package ooga.engine;

import ooga.data.DataObject;
import ooga.engine.grid.GridCreator;
import ooga.engine.matchFinder.MatchFinder;
import ooga.engine.newCellAdder.NewCellAdder;
import ooga.engine.validator.Validator;


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
}
