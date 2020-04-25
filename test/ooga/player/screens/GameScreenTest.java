package ooga.player.screens;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ooga.data.Data;
import ooga.engine.Engine;
import ooga.player.Player;
import ooga.player.screens.GameScreen;
import ooga.util.DukeApplicationTest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class GameScreenTest extends DukeApplicationTest {
    private Button myHomeButton;
    private Button myCustomizeButton;

    @Override
    public void start(Stage stage) {
        Player myPlayer = new Player(stage);
        GameScreen myGameScreen = new GameScreen("CandyCrush", myPlayer);
        stage.setScene(myGameScreen.makeScene());
        stage.show();

        myHomeButton = lookup("#homebutton").query();
        myCustomizeButton = lookup("#customview").query();
    }

    //tests to see if the correct buttons were made in horizontal toolbar
    @Test
    void checkToolBar() {
        HBox toolbar = lookup("#toolbar").query();
        assertEquals(toolbar.getSpacing(), 40);
        assertEquals(myHomeButton.getText(), "Home");
        assertEquals(myCustomizeButton.getText(), "Customize");
    }


}