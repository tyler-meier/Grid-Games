package ooga.player.screens;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ooga.player.Player;
import ooga.util.DukeApplicationTest;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class CustomViewTest extends DukeApplicationTest {
    private Player myPlayer;
    private CustomView myCustomView;
    private Scene myScene;
    private Button defaultMode;
    private Button darkMode;
    private Button rainbowMode;
    private Button greenMode;
    private Button waveMode;

    @Override
    public void start(Stage stage) {
        myPlayer = new Player(stage);
        myCustomView = new CustomView(myPlayer);
        myScene = myCustomView.finishStyling(myCustomView.makePanel());
        stage.setScene(myScene);

        defaultMode = lookup("#default").query();
        darkMode = lookup("#dark").query();
        rainbowMode = lookup("#rainbow").query();
        greenMode = lookup("#green").query();
        waveMode = lookup("#wave").query();
    }

    //checks button images and texts
    @Test
    void checkButtons() throws FileNotFoundException {
        assertEquals(defaultMode.getText(), "Candy Mode");
        assertEquals(darkMode.getText(), "Dark Mode");
        assertEquals(rainbowMode.getText(), "Rainbow Mode");
        assertEquals(greenMode.getText(), "Green Mode");
        assertEquals(waveMode.getText(), "Wave Mode");
    }

}