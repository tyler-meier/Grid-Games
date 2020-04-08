package ooga.player;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javax.swing.*;

public class TimeKeeper {
    String time = "00:00:000";
    Text text = new Text();
    Timeline timeline;
    int mins = 0, secs = 0, millis = 0;

    public TimeKeeper() {
        text = new Text(time);
    }

    public String getText() {
        return time;
    }

    public void addTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                change(text);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
    }

    private void change(Text text) {
        if (millis == 1000) {
            secs ++;
            millis = 0;
        }
        if (secs == 60) {
            mins++;
            secs = 0;
        }
        time = (((mins/10) == 0) ? "0" : "") + mins + ":"
                + (((secs/10) == 0) ? "0" : "") + secs + ":"
                + (((millis/10) == 0) ? "00" : (((millis/100) == 0) ? "0" : "")) + millis++;
        text.setText(time);
    }

}
