package ooga.engine;

import javafx.beans.property.IntegerProperty;
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
    private static final String TIME = "Time";
    private static final String LIVES_LOST = "LivesLost";
    private static final String MOVES_USED = "MovesUsed";
    private static final int ONE_SECOND = 1000;
    private Map<String, IntegerProperty> gameStats = new HashMap<>();
    private String lossStatKey;
    private int targetScore;
    private Timer timer = new Timer();
    private TimerTask timerTask;

    public GameProgressManager(Map<String, String> gameAttributes) throws InvalidDataException {
        try{
            System.out.println("made it to constructor of progress manager");
            gameStats.put(SCORE, new SimpleIntegerProperty(Integer.parseInt(gameAttributes.get(SCORE))));
            System.out.println("SCORE");
            gameStats.put(LEVEL, new SimpleIntegerProperty(Integer.parseInt(gameAttributes.get(LEVEL))));
            System.out.println("LEVEL");
            lossStatKey = gameAttributes.get(LOSS_STAT);
            System.out.println("LOSS STAT");
            gameStats.put(lossStatKey, new SimpleIntegerProperty(10));
            System.out.println("LOSS STAT KEY");
            targetScore =  Integer.parseInt(gameAttributes.get(TARGET_SCORE));
            System.out.println("Correctly made the game manager");
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

    public boolean isWin(){
        return gameStats.get(SCORE).get() >= targetScore;
    }

    public boolean isLoss(){
        return gameStats.get(lossStatKey).get() <= 0;
    }

    // pass this to front end to always display score and level, also display loss stat and amount
    // use properties to bind to avoid passing a lot
    public Map<String, IntegerProperty> getGameStats(){ return gameStats; }

    public void updateScore(int amount){
        System.out.println("UPDATING THE SCORE NOW");
        changeValue(SCORE, amount); }

    public void startTimer() {
        if (!gameStats.containsKey(TIME)) return;
        while (gameStats.get(TIME).get() >= 0) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    int time = gameStats.get(TIME).get();
                    time --;
                    gameStats.get(TIME).set(time);
                }
            }, ONE_SECOND);
        }
        timer.cancel();
        System.out.println("out of time");
    }

    public void pauseTimer(){
        if (!gameStats.containsKey(TIME)) return;
        timer.cancel();
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
