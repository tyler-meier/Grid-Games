package ooga.engine;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
 * @author Tanvi Pabby
 */
public class ComponentCreator {
    private static final String MATCH_FINDER_PATH = "ooga.engine.matchFinder.";
    private static final String VALIDATOR_PATH = "ooga.engine.validator.";
    private StringProperty errorMessage = new SimpleStringProperty();

    public ComponentCreator(StringProperty errorMessage){
        this.errorMessage.bindBidirectional(errorMessage);
    }

    /**
     * This method is going to initialize and return the correct validator
     * for the current game. (**Can determine the file path based off the string it is passed)
     * @return
     */
    public Validator makeMyValidator(String validatorType){
        Class<?> clazz = getClass(validatorType, VALIDATOR_PATH);
        try{
            assert clazz != null;
            Object o = clazz.getDeclaredConstructor().newInstance();
            return (Validator) o;
        }
        catch (Exception e) {
            errorMessage.set(e.getMessage());
        }
        return null;
    }

    /**
     * This method is going to initialize and return the correct match finder
     * for the current game.
     * @return
     */
    public MatchFinder makeMyMatchFinder(String matchFinderType){
        Class<?> clazz = getClass(matchFinderType, MATCH_FINDER_PATH);
        try{
            assert clazz != null;
            Object o = clazz.getDeclaredConstructor().newInstance();
            return (MatchFinder) o;
        }
        catch (Exception e) {
            errorMessage.set(e.getMessage());
        }
        return null;
    }

    /**
     * Creates the class that matches the name of the command, if one exists
     * @param nameOfClass
     * @param path
     * @return
     */
    private Class<?> getClass(String nameOfClass, String path) {
        try{
            return Class.forName(path + nameOfClass);
        }
        catch(ClassNotFoundException e){
            errorMessage.set(e.getMessage());
        }
        return null;
    }
}