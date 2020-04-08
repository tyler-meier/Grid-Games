package ooga.engine;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class GameProgressManager{
    private static final String SCORE = "Score";
    private static final String TARGET_SCORE = "TargetScore";
    private static final String LEVEL = "Level";
    private static final String LOSS_STAT = "LossStat";
    private static final String LOSS_THRESHOLD = "LossThreshold";
    private static final String TIME = "Time";
    private static final String LIVES_LOST = "LivesLost";
    private static final String MOVES_USED = "MovesUsed";
    private Map<String, SimpleIntegerProperty> gameStats = new HashMap<>();
    private String lossStatKey;
    private int targetScore;
    private IntegerProperty timeSeconds = new SimpleIntegerProperty();
    private Timer timer;

    public GameProgressManager(Map<String, String> gameAttributes){
        timeSeconds.set(Integer.parseInt(gameAttributes.get(TIME)));
        gameStats.put(TIME, (SimpleIntegerProperty) timeSeconds);
        gameStats.put(SCORE, new SimpleIntegerProperty(Integer.parseInt(gameAttributes.get(SCORE))));
        gameStats.put(LEVEL, new SimpleIntegerProperty(Integer.parseInt(gameAttributes.get(LEVEL))));
        lossStatKey = gameAttributes.get(LOSS_STAT);
        int startingLossStatAmount = Integer.parseInt(gameAttributes.get(lossStatKey));
        int totalLossStatAmount = Integer.parseInt(gameAttributes.get(LOSS_THRESHOLD));
        int remainingLossStatAmount = totalLossStatAmount -startingLossStatAmount;
        gameStats.put(lossStatKey, new SimpleIntegerProperty(remainingLossStatAmount));
        targetScore =  Integer.parseInt(gameAttributes.get(TARGET_SCORE));
        timer = new Timer();
    }

    public boolean isWin(){
        return gameStats.get(SCORE).get() >= targetScore;
    }

    public boolean isLoss(){
        return gameStats.get(lossStatKey).get() <= 0;
    }

    // pass this to front end to always display score and level, also display loss stat and amount
    // use properties to bind to avoid passing a lot
    public Map<String, SimpleIntegerProperty> getGameStats(){ return gameStats; }

    public void updateScore(int amount){ changeValue(SCORE, amount); }

    public void startTimer() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                updateTime();
            }
        };
        while (getTimeSeconds() >= 0) {
            timer.schedule(task, 1000);
        }
    }

    public void pauseTimer(){
        timer.cancel();
    }
    //????? idk how this will work
    // we should bind this timeSeconds property to something on the frontend so that the time display is updated when
    // timeSeconds is updated
    private void updateTime(){
        // decrement timeSeconds every time this method is called
        timeSeconds.set(timeSeconds.get() - 1);
        // update the time value in the map
        gameStats.get(TIME).set(timeSeconds.get());
    }

    public int getTimeSeconds(){
        return timeSeconds.get();
    }

    public void incrementMoves(){ changeValue(MOVES_USED, -1); }

    public void incrementLives(){ changeValue(LIVES_LOST, -1); }

    private void changeValue(String key, int amount){
        if (!gameStats.containsKey(key)) return;
        int currValue = gameStats.get(key).get();
        currValue+=amount;
        gameStats.get(key).set(currValue);
    }

}
