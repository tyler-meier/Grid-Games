package ooga.player;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javax.swing.*;

public class TimeKeeper {
    Text text = new Text("00:00:000");
    Timeline timeline;
    int mins = 0, secs = 0, millis = 0;

    public Text getText() {
        return text;
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
        text.setText((((mins/10) == 0) ? "0" : "") + mins + ":"
        + (((secs/10) == 0) ? "0" : "") + secs + ":"
        + (((millis/10) == 0) ? "00" : (((millis/100) == 0) ? "0" : "")) + millis++);
    }

    private void addTimeline() {
        timeline = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                change(text);
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);

    }

}
