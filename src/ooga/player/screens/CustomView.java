package ooga.player.screens;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

public class CustomView extends SuperScreen {

    private static final String IMAGE_RESOURCES = "src/ooga/player/Resources/images/preferences/";
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
     * Sets up the start/home scene, with the labels, combo box, and buttons
     * @return the final completed scene to be shown
     */
    public Scene setUpScene(){
        Node panel = makePanel();
        return styleScene(panel);
    }

    public void display() {
        Stage popUpWindow = new Stage();
        popUpWindow.initModality(Modality.APPLICATION_MODAL);
        popUpWindow.setTitle("Preferences");

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
            ImageView modeImageView = new ImageView(image);
            modeImageView.setPreserveRatio(true);
            modeImageView.setFitHeight(IMAGE_SIZE);
            modeImageView.setFitWidth(IMAGE_SIZE);
            mode.setGraphic(modeImageView);
            return mode;
        } catch (FileNotFoundException e) {
            myErrorMessage.textProperty().setValue(myStringResources.getString("NoStyleError"));
        }
        return null;
    }

}
