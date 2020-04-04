package ooga.engine;

import ooga.data.DataObject;
import ooga.engine.grid.GridCreator;


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
        // going to get information about the characteristics of the game
        myComponentCreator = new ComponentCreator();
        myNewCellAdder = myComponentCreator.makeMyNewCellAdder();
        myGridCreator = myComponentCreator.makeMyGridCreator(); //the number of rows, columns, and cell states are also going to be given as a parameter here
        myValidator = myComponentCreator.makeMyValidator();
        myMatchFinder = myComponentCreator.makeMyMatchFinder();
    }

    @Override
    public Cell[][] updateBoard() {
        return new Cell[0][];
    }

    @Override
    public Cell[][] getGrid() {
        return new Cell[0][];
    }

    @Override
    public State getGameState() {
        return null;
    }
}
