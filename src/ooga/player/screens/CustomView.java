package ooga.player.screens;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import ooga.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomView extends SuperScreen{

    private static final String IMAGERESOURCES = "src/ooga/player/Resources/images/preferences/";

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

        VBox panel = new VBox(styleContents(label, defaultButton, darkMode, rainbowMode, greenMode, waveMode));
        panel.setSpacing(6);

        return panel;
    }

    //make buttons that represent a new mode
    //TODO: fix file not found exception
    private Button makeImageButtons(String modeType){
        try {
            String imagePath = IMAGERESOURCES + modeType + ".png";
            FileInputStream input = new FileInputStream(imagePath);
            Image image = new Image(input);
            Button mode = makeButton(modeType, e-> myPlayer.setMode(modeType + ".css"));
            ImageView modeImageView = new ImageView(image);
            modeImageView.setPreserveRatio(true);
            modeImageView.setFitHeight(80);
            modeImageView.setFitWidth(80);
            mode.setGraphic(modeImageView);
            return mode;
        } catch (FileNotFoundException e) {
            //TODO: deal with exception later
        }
        return null;
    }

}
