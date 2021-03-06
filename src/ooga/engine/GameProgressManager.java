package ooga.engine;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import ooga.engine.exceptions.InvalidDataException;

import java.util.HashMap;
import java.util.Map;

/**
 * The purpose of this class is to keep track of all of the statistics of the current
 * game, such as level, score, loss statistics, and loss/win status.
 * @author Natalie Novitsky and Tanvi Pabby
 */
public class GameProgressManager{
    private static final String SCORE = "Score";
    private static final String TARGET_SCORE = "TargetScore";
    private static final String LEVEL = "Level";
    private static final String LOSS_STAT = "LossStat";
    private static final String LIVES_LEFT = "LivesLeft";
    private static final String MOVES_LEFT = "MovesLeft";
    private static final String NUM_ROWS = "numRows";
    private static final String NUM_COLS = "numColumns";
    private Map<String, IntegerProperty> gameStats = new HashMap<>();
    private String lossStatKey;
    private BooleanProperty isLoss = new SimpleBooleanProperty();
    private BooleanProperty isWin = new SimpleBooleanProperty();
    private String rows, cols;


    public GameProgressManager(Map<String, String> gameAttributes){
        try{
            gameStats.put(SCORE, new SimpleIntegerProperty(Integer.parseInt(gameAttributes.get(SCORE))));
            gameStats.put(LEVEL, new SimpleIntegerProperty(Integer.parseInt(gameAttributes.get(LEVEL))));
            lossStatKey = gameAttributes.get(LOSS_STAT);
            gameStats.put(lossStatKey, new SimpleIntegerProperty(Integer.parseInt(gameAttributes.get(lossStatKey))));
            gameStats.put(TARGET_SCORE, new SimpleIntegerProperty(Integer.parseInt(gameAttributes.get(TARGET_SCORE))));
            gameStats.get(lossStatKey).addListener((a, b, c) ->{
                isLoss.set(gameStats.get(lossStatKey).get() <= 0);
            });
            gameStats.get(SCORE).addListener((a, b, c) -> {
                isWin.set(gameStats.get(SCORE).get() >= gameStats.get(TARGET_SCORE).get());
            });
            rows = gameAttributes.get(NUM_ROWS);
            cols = gameAttributes.get(NUM_COLS);
        } catch (Exception e) {
            throw new InvalidDataException();
        }
    }

    public int getLevel() { return gameStats.get(LEVEL).get(); }

    /**
     * This method returns a map containing information about the attributes of the current game.
     * These include the score, level, and whatever the loss stat for this game is (in a String to String mapping).
     * @return
     */
    public Map<String, String> getGameAttributes(){
        Map<String, String> currentAttributes = new HashMap<>();
        for (String key: gameStats.keySet()){
            currentAttributes.put(key, gameStats.get(key).getValue().toString());
        }
        currentAttributes.put(LOSS_STAT, lossStatKey);
        currentAttributes.put(NUM_ROWS, rows);
        currentAttributes.put(NUM_COLS, cols);
        return currentAttributes;
    }

    /**
     * This method returns a map containing information about the attributes of the current game.
     * These include the score, level, and whatever the loss stat for this game is (in a String to Integer property mapping).
     * @return
     */
    public Map<String, IntegerProperty> getGameStats(){ return gameStats; }

    /**
     * This method returns the boolean property indicating whether or not the game has been won.
     * @return
     */
    public BooleanProperty isWin(){ return isWin; }

    /**
     * This method returns the boolean property indicating whether or not the game has been lost.
     * @return
     */
    public BooleanProperty isLoss(){
        return isLoss;
    }

    /**
     * This method adds the parameter 'amount' to the current score of the game.
     * It also updates the corresponding score value stored in the game stats map.
     * @param amount
     */
    public void updateScore(int amount){
        changeValue(SCORE, amount);
    }

    /**
     * This method decrements the number of moves used in the game.
     * It also updates the corresponding score value stored in the game stats map.
     */
    public void decrementMoves(){
        changeValue(MOVES_LEFT, -1);
    }

    /**
     * This method decrements the number of lives used in the game.
     * It also updates the corresponding score value stored in the game stats map.
     */
    public void decrementLives(){ changeValue(LIVES_LEFT, -1); }

    private void changeValue(String key, int amount){
        if (!gameStats.containsKey(key)) return;
        int currValue = gameStats.get(key).get();
        currValue+=amount;
        gameStats.get(key).set(currValue);
    }

}
