package ooga.engine;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ooga.engine.gridCreator.GridCreator;
import ooga.engine.matchFinder.MatchFinder;
import ooga.engine.validator.Validator;

import java.util.Map;

/**
 * The purpose of this class is to initialize the correct
 * components of the engine for the given game. This will be done using
 * reflection, as well as information from the Data component of the game.
 * @author Tanvi Pabby
 */
public class ComponentCreator {
    private static final String MATCH_FINDER_PATH = "ooga.engine.matchFinder.";
    private static final String VALIDATOR_PATH = "ooga.engine.validator.";
    private static final String GRID_CREATOR_PATH = "ooga.engine.gridCreator.";
    private static final String VALIDATOR = "Validator";
    private static final String MATCH_FINDER = "MatchFinder";
    private static final String GRID_CREATOR = "GridCreator";
    private static final String NUM_SELECTED_PER_MOVE = "NumSelectedPerMove";
    private static final String MAX_STATE_NUMBER = "MaxStateNumber";
    private StringProperty errorMessage = new SimpleStringProperty();
    private Map<String, String> myEngineAttributes;

    public ComponentCreator(Map<String, String> engineAttributes, StringProperty errorMessage){
        this.errorMessage.bindBidirectional(errorMessage);
        myEngineAttributes = engineAttributes;
    }

    /**
     * This method is going to initialize and return the correct validator
     * for the current game. (**Can determine the file path based off the string it is passed)
     * @return
     */
    public Validator makeMyValidator(){
        String validatorType = myEngineAttributes.get(VALIDATOR);
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
    public MatchFinder makeMyMatchFinder(){
        String matchFinderType = myEngineAttributes.get(MATCH_FINDER);
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

    public GridCreator makeMyGridCreator(){
        String gridCreatorType = myEngineAttributes.get(GRID_CREATOR);
        Class<?> clazz = getClass(gridCreatorType, GRID_CREATOR_PATH);
        try{
            assert clazz != null;
            Object o = clazz.getDeclaredConstructor().newInstance();
            GridCreator gridCreator = (GridCreator) o;
            gridCreator.setEngineAttributes(Integer.parseInt(myEngineAttributes.get(MAX_STATE_NUMBER)), Integer.parseInt(myEngineAttributes.get(NUM_SELECTED_PER_MOVE)));
            return gridCreator;
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