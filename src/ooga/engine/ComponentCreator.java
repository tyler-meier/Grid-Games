package ooga.engine;

import ooga.engine.grid.GridCreator;
import ooga.engine.matchFinder.MatchFinder;
import ooga.engine.newCellAdder.NewCellAdder;
import ooga.engine.validator.Validator;

/**
 * The purpose of this class is to initialize the correct
 * components of the engine for the given game. This will be done using
 * reflection, as well as information from the Data component of the game.
 */
public class ComponentCreator {

    public ComponentCreator(){

    }

    /**
     * This method is going to initialize and return the correct grid creator
     * for the current game. (**Can determine the file path based off the string it is passed)
     * @return
     */
    public GridCreator makeMyGridCreator(){
        //Class<?> clazz = getClass(nameOfClass, path);
        return null;
    }

    /**
     * This method is going to initialize and return the correct cell adder
     * for the current game. (**Can determine the file path based off the string it is passed)
     * @return
     */
    public NewCellAdder makeMyNewCellAdder(){
        //Class<?> clazz = getClass(nameOfClass, path);
        return null;
    }

    /**
     * This method is going to initialize and return the correct validator
     * for the current game. (**Can determine the file path based off the string it is passed)
     * @return
     */
    public Validator makeMyValidator(){
        //Class<?> clazz = getClass(nameOfClass, path);
        return null;
    }

    /**
     * This method is going to initialize and return the correct match finder
     * for the current game. (**Can determine the file path based off the string it is passed)
     * @return
     */
    public MatchFinder makeMyMatchFinder(){
        //Class<?> clazz = getClass(nameOfClass, path);
        return null;
    }


    /**
     * Creates the class that matches the name of the command, if one exists
     * @param nameOfClass
     * @param path
     * @return
     */
    private Class<?> getClass(String nameOfClass, String path) throws ClassNotFoundException {
        //Class<?> clazz = Class.forName(path + curWord);
        return null;
    }
}

