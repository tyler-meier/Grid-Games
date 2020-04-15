package ooga.engine;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import ooga.engine.grid.InvalidDataException;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class GameProgressManager{
    private static final String SCORE = "Score";
    private static final String TARGET_SCORE = "TargetScore";
    private static final String LEVEL = "Level";
    private static final String LOSS_STAT = "LossStat";
    private static final String LIVES_LEFT = "LivesLeft";
    private static final String MOVES_LEFT = "MovesLeft";
    private Map<String, IntegerProperty> gameStats = new HashMap<>();
    private String lossStatKey;
    private int targetScore;
    private BooleanProperty isLoss = new SimpleBooleanProperty();
    private BooleanProperty isWin = new SimpleBooleanProperty();


    public GameProgressManager(Map<String, String> gameAttributes) throws InvalidDataException {
        try{
            gameStats.put(SCORE, new SimpleIntegerProperty(Integer.parseInt(gameAttributes.get(SCORE))));
            gameStats.put(LEVEL, new SimpleIntegerProperty(Integer.parseInt(gameAttributes.get(LEVEL))));
            lossStatKey = gameAttributes.get(LOSS_STAT);
            gameStats.put(lossStatKey, new SimpleIntegerProperty(Integer.parseInt(gameAttributes.get(lossStatKey))));
            targetScore =  Integer.parseInt(gameAttributes.get(TARGET_SCORE));
            gameStats.get(lossStatKey).addListener((a, b, c) ->{
                isLoss.set(gameStats.get(lossStatKey).get() <= 0);
            });
            gameStats.get(SCORE).addListener((a, b, c) -> {
                isWin.set(gameStats.get(SCORE).get() >= targetScore);
            });
        } catch (Exception e) {
            throw new InvalidDataException();
        }
    }

    public Map<String, String> getGameAttributes(){
        Map<String, String> currentAttributes = new HashMap<>();
        for (String key: gameStats.keySet()){
            currentAttributes.put(key, gameStats.get(key).getValue().toString());
        }
        currentAttributes.put(LOSS_STAT, lossStatKey);
        return currentAttributes;
    }

    public BooleanProperty isWin(){ return isWin; }

    public BooleanProperty isLoss(){
        return isLoss;
    }

    // pass this to front end to always display score and level, also display loss stat and amount
    // use properties to bind to avoid passing a lot
    public Map<String, IntegerProperty> getGameStats(){ return gameStats; }

    public void updateScore(int amount){
        changeValue(SCORE, amount);
    }

    public void decrementMoves(){
        changeValue(MOVES_LEFT, -1);
    }

    public void decrementLives(){ changeValue(LIVES_LEFT, -1); }

    private void changeValue(String key, int amount){
        if (!gameStats.containsKey(key)) return;
        int currValue = gameStats.get(key).get();
        currValue+=amount;
        gameStats.get(key).set(currValue);
    }
}
