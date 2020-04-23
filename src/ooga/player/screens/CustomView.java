package ooga.player.screens;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import ooga.player.Player;

/**
 * Sets the style of the screen based off of the created styling sheets for each, and allows this
 * style to be displayed on all screens once saved
 * @author Alyssa Shin
 */
public class CustomView extends SuperScreen {

    private static final String IMAGE_RESOURCES = "src/ooga/player/Resources/images/preferences/";
    private static final String TITLE = "Preferences";
    private static final int IMAGE_SIZE = 80;
    private static final int SPACING = 6;

    /**
     * Constructor of this class, calls super to set up instance variables
     * @param thisPlayer the current player
     */
    public CustomView(Player thisPlayer) {
        super(thisPlayer);
    }

    /**
     * Displays scene on stage
     */
    public void display() {
        Stage popUpWindow = new Stage();
        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        popUpWindow.setTitle(TITLE);
        popUpWindow.setScene(finishStyling(makePanel()));
        popUpWindow.showAndWait();
    }

    //make node that contains all the buttons
    private Parent makePanel(){
        Label label = new Label("Pick your theme");
        Button defaultButton = makeImageButtons("default");
        Button darkMode = makeImageButtons("darkmode");
        Button rainbowMode = makeImageButtons("rainbow");
        Button greenMode = makeImageButtons("greenmode");
        Button waveMode = makeImageButtons("wavemode");

        VBox panel = new VBox(styleContents(label, defaultButton, darkMode, rainbowMode, greenMode, waveMode, myErrorMessage));
        panel.setSpacing(SPACING);
        return panel;
    }

    //make buttons that represent a new mode
    private Button makeImageButtons(String modeType){
        try {
            String imagePath = IMAGE_RESOURCES + modeType + ".png";
            FileInputStream input = new FileInputStream(imagePath);
            Image image = new Image(input);
            Button mode = makeButton(modeType, e-> myPlayer.setMode(modeType));
            ImageView modeImageView = getImageView(image);
            mode.setGraphic(modeImageView);
            return mode;
        } catch (FileNotFoundException e) {
            myErrorMessage.textProperty().setValue(myStringResources.getString("NoStyleError"));
        }
        return null;
    }

    //style given ImageView
    private ImageView getImageView(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(IMAGE_SIZE);
        imageView.setFitWidth(IMAGE_SIZE);
        return imageView;
    }
}
