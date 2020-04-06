package ooga.engine;

import javafx.css.Match;
import ooga.engine.grid.GridCreator;
import ooga.engine.matchFinder.MatchFinder;
import ooga.engine.newCellAdder.NewCellAdder;
import ooga.engine.validator.Validator;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

/**
 * The purpose of this class is to initialize the correct
 * components of the engine for the given game. This will be done using
 * reflection, as well as information from the Data component of the game.
 */
public class ComponentCreator {
    private static final String MATCH_FINDER_PATH = "ooga.engine.matchFinder.";
    private static final String VALIDATOR_PATH = "ooga.engine.validator.";

    public ComponentCreator(){

    }

    /**
     * This method is going to initialize and return the correct validator
     * for the current game. (**Can determine the file path based off the string it is passed)
     * @return
     */
    public Validator makeMyValidator(String validatorType) throws Exception {
        Class<?> clazz = getClass(validatorType, VALIDATOR_PATH);
        try{
            Object o = clazz.getDeclaredConstructor().newInstance();
            Validator ret = (Validator) o;
            return ret;
        }
        catch (Exception e) {
            // FIXME: handle exception
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * This method is going to initialize and return the correct match finder
     * for the current game. (**Can determine the file path based off the string it is passed)
     * @return
     */
    public MatchFinder makeMyMatchFinder(String matchFinderType) throws Exception {
        Class<?> clazz = getClass(matchFinderType, MATCH_FINDER_PATH);
        try{
            Object o = clazz.getDeclaredConstructor().newInstance();
            MatchFinder ret = (MatchFinder) o;
            return ret;
        }
        catch (Exception e) {
            // FIXME: handle exception
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    /**
     * Creates the class that matches the name of the command, if one exists
     * @param nameOfClass
     * @param path
     * @return
     */
    private Class<?> getClass(String nameOfClass, String path) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(path + nameOfClass);
        return clazz;
    }
}