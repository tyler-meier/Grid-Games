package ooga.engine;

import ooga.data.DataObject;

public class Engine implements EngineBuilder {
    private GridCreator myGridCreator;
    private Validator myValidator;
    private NewCellAdder myNewCellAdder;
    private Cell[][] myGrid;
    private MatchFinder myMatchFinder;

    public Engine(DataObject myData){

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
