package ooga.engine;

import ooga.engine.grid.GridCreator;

/**
 * The purpose of this class is to initialize the correct
 * components of the engine for the given game. This will be done using
 * reflection, as well as information from the Data component of the game.
 */
public class ComponentCreator {

    public ComponentCreator(){

    }

    /**
     * This method is going to initialize the different components of the
     * backend using reflection. (**Can determine the file path based off the string it is passed)
     */
    public GridCreator makeMyGridCreator(){
        return null;
    }

    public NewCellAdder makeMyNewCellAdder(){
        return null;
    }

    public Validator makeMyValidator(){
        return null;
    }

    public MatchFinder makeMyMatchFinder(){
        return null;
    }
}

