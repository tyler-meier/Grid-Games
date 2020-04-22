package ooga.player.screens;

import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ooga.player.Player;
import ooga.player.exceptions.NewUserDefinedGameException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class UserDefinedGameScreenImages extends UserDefinedGameScreen {
    private static final String KEY = "ImageGroups";
    private static final String BUTTON_TEXT = "Next";
    private static final String GAME_LABEL = "ChooseImage";
    private static final String IMAGE_GROUP = "ImageGroup";
    private static final String IMAGERESOURCES = "src/ooga/player/Resources/images/";
    private static final int SPACING = 20;
    private static final int WIDTH = 50;
    private VBox images = new VBox();
    private String imagePath;

    public UserDefinedGameScreenImages(Player thisPlayer) {
        super(thisPlayer);
        myKeysResources = ResourceBundle.getBundle(KEYS_RESOURCES_PATH + KEY);
        myButtonEvent = event -> {
            System.out.println("nice");
            try{
                myPlayer.setUpMakeNewGameScreenOne();
            }
            catch(NewUserDefinedGameException p){
                myErrorMessage.textProperty().setValue(p.getMessage());
            }
        };
        myButtonText = BUTTON_TEXT;
        gameLabel = GAME_LABEL;
    }

    public String getImagePath() { return imagePath; }

    @Override
    protected void screenSpecificSetup() {
        inputField.getChildren().clear();
        inputField.getChildren().addAll(labelMap.get(IMAGE_GROUP), userInputFields.get(IMAGE_GROUP));
        ComboBox groupChoice = (ComboBox) userInputFields.get(IMAGE_GROUP);
        groupChoice.getSelectionModel().selectedItemProperty().addListener(e->{
            String newKey = groupChoice.getSelectionModel().getSelectedItem().toString();
            String path = myKeysResources.getString(newKey);
            images.getChildren().clear();
            buildImages(path);
            imagePath = path;
        });
        images.setAlignment(Pos.CENTER);
        inputField.getChildren().add(images);
    }

    private void buildImages(String path){
        ResourceBundle imagePathResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + path);
        List<String> keys = Collections.list(imagePathResources.getKeys());
        try {
            for (String key : keys) {
                String imageName = imagePathResources.getString(key);
                String imagePath = IMAGERESOURCES + imageName + ".png";
                FileInputStream input = new FileInputStream(imagePath);
                Image image = new Image(input);
                ImageView view = new ImageView(image);
                view.setPreserveRatio(true);
                view.setFitWidth(WIDTH);
                HBox box = new HBox();
                box.setAlignment(Pos.CENTER);
                box.setSpacing(SPACING);
                Label label = new Label(key);
                box.getChildren().addAll(label, view);
                images.getChildren().add(box);
            }
        } catch (FileNotFoundException e){
            //TODO: deal with exception later
        }
    }

    @Override
    protected boolean additionalValidation() {
        return false;
    }
}