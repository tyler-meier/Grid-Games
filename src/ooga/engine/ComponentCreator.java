package ooga.engine;

import ooga.engine.exceptions.InvalidDataException;
import ooga.engine.gridCreator.GridCreator;
import ooga.engine.matchFinder.MatchFinder;
import ooga.engine.validator.Validator;

import java.util.Map;

/**
 * The purpose of this class is to initialize the correct
 * components of the engine for the given game. This will be done using
 * reflection, as well as information from the Data component of the game.
 * @author Tanvi Pabby and Natalie Novitsky.
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
    private Map<String, String> myEngineAttributes;

    /**
     * Sets up new ComponentCreator with given engine attributes.
     * @param engineAttributes details for this engine
     */
    public ComponentCreator(Map<String, String> engineAttributes){
        myEngineAttributes = engineAttributes;
    }

    /**
     * This method is going to initialize and return the correct Validator
     * for the current game. (**Can determine the file path based off the string it is passed)
     * @return appropriate Validator
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
            throw new InvalidDataException();
        }
    }

    /**
     * This method is going to initialize and return the correct MatchFinder
     * for the current game.
     * @return appropriate MatchFinder
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
            throw new InvalidDataException();
        }
    }

    /**
     * Initializes the correct GridCreator based on engine attributes, then passes necessary details to the
     * GridCreator. GridCreator is always created, but only used if no initial config is provided.
     * @return appropriate GridCreator subclass
     */
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
            throw new InvalidDataException();
        }
    }

    private Class<?> getClass(String nameOfClass, String path) {
        try{
            return Class.forName(path + nameOfClass);
        }
        catch(ClassNotFoundException e){
            throw new InvalidDataException();
        }
    }
}