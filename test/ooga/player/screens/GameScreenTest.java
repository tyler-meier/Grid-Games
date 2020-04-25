package ooga.player.screens;

import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ooga.player.Player;
import ooga.util.DukeApplicationTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameScreenTest extends DukeApplicationTest {
    private Button myHomeButton;
    private Button myCustomizeButton;
    private Button myLogoutButton;
    private Button myResetLevelButton;
    private Button myResetGameButton;
    private Button myLeaderboardButton;
    private Button mySaveButton;
    private Label myErrorMessage;
    private Stage myStage;

    @Override
    public void start(Stage stage) {
        Player myPlayer = new Player(stage);
        GameScreen myGameScreen = new GameScreen("CandyCrush", myPlayer);
        Scene myScene = myGameScreen.makeScene();
        stage.setScene(myScene);
        stage.show();

        myHomeButton = lookup("#homebutton").queryButton();
        myCustomizeButton = lookup("#customview").queryButton();

//        myLogoutButton = lookup("Logout").queryButton();
//        myResetLevelButton = lookup("Reset Level").queryButton();
//        myResetGameButton = lookup("Reset Game").queryButton();
//        mySaveButton = lookup("Save Progress").queryButton();
//        myLeaderboardButton = lookup("See Leaderboard").queryButton();
    }

    //tests to see if the correct buttons were made in horizontal toolbar
    @Test
    void checkToolBar() {
        HBox toolbar = lookup("#toolbar").query();
        assertEquals(toolbar.getSpacing(), 40);
        assertEquals(myHomeButton.getText(), "Home");
        assertEquals(myCustomizeButton.getText(), "Customize");
    }

    @Test
    void setGrid() {

    }

}