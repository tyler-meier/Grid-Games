package ooga.engine;

import java.util.Timer;
import java.util.TimerTask;

public class CountdownTimer {
    private GameProgressManager gameProgressManager;
    private Timer timer;

    public CountdownTimer(GameProgressManager gameProgressManager){
        this.gameProgressManager = gameProgressManager;
        timer = new Timer();
    }

    /**
     * This method, when called will decrement the game time every second.
     */
    public void startTimer(){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                gameProgressManager.updateTime();
            }
        };
        while (gameProgressManager.getTimeSeconds() >= 0) {
            timer.schedule(task, 1000);
        }
    }

    /**
     * This method, when called will pause the game timer.
     */
    public void pauseTimer(){
        timer.cancel();
    }

    /**
     * This method, when called will resume the game timer.
     */
    public void resumeTimer(){
        startTimer();
    }

}
