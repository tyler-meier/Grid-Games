package ooga.player.screens;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ooga.player.Player;
import ooga.util.DukeApplicationTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomViewTest extends DukeApplicationTest {
    private static final String TITLE = "Preferences";
    private static final int DIMENSION = 650;
    private Player myPlayer;
    private CustomView myCustomView;
    private Button defaultMode;
    private Button darkMode;
    private Button rainbowMode;
    private Button greenMode;
    private Button waveMode;

    @Override
    public void start(Stage stage) {
        myPlayer = new Player(stage);
        myCustomView = new CustomView(myPlayer);

        stage.setScene(myCustomView.finishStyling(myCustomView.makePanel()));

        defaultMode = lookup("#default").query();
        darkMode = lookup("#dark").query();
        rainbowMode = lookup("#rainbow").query();
        greenMode = lookup("#green").query();
        waveMode = lookup("#wave").query();
}
    //checks button images and texts
    @Test
    void checkButtons() {
        assertEquals(defaultMode.getText(), "Candy Mode");
        assertEquals(darkMode.getText(), "Dark Mode");
        assertEquals(rainbowMode.getText(), "Rainbow Mode");
        assertEquals(greenMode.getText(), "Green Mode");
        assertEquals(waveMode.getText(), "Wave Mode");
    }

}