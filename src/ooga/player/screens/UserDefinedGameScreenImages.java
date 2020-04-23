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

/**
 * Screen where the user can select the images they want in their new
 * user defined game.
 * @author Natalie Novitsky.
 */
public class UserDefinedGameScreenImages extends UserDefinedGameScreen {
    private static final String KEY = "ImageGroups";
    private static final String BUTTON_TEXT = "Next";
    private static final String GAME_LABEL = "ChooseImage";
    private static final String IMAGE_GROUP = "ImageGroup";
    private static final String IMAGERESOURCES = "src/ooga/player/Resources/images/";
    private static final String MINESWEEPER = "Minesweeper";
    private static final int SPACING = 20;
    private static final int WIDTH = 50;
    private VBox images = new VBox();
    private String imagePath;

    /**
     * Constructor for this class, sets up  the screen to allow users to choose images for their game
     * @param thisPlayer current player
     */
    public UserDefinedGameScreenImages(Player thisPlayer) {
        super(thisPlayer);
        myKeysResources = ResourceBundle.getBundle(KEYS_RESOURCES_PATH + KEY);
        myButtonEvent = event -> {
            try{
                buildMap();
                myPlayer.setUpMakeNewGameScreenOne();
            }
            catch(NewUserDefinedGameException p){
                myErrorMessage.textProperty().setValue(p.getMessage());
            }
        };
        myButtonText = BUTTON_TEXT;
        gameLabel = GAME_LABEL;
    }

    /**
     * @return the path of the images the user selected for their game.
     */
    public String getImagePath() { return imagePath; }

    /**
     * @return the range of the allowed states for the current game, based on the images selected by the user.
     */
    public int[] getStateRange() { return stateRange; }

    /**
     * @return a boolean saying whether or not the selected game is minesweeper.
     */
    public boolean isMinesweeper() { return imagePath.equals(MINESWEEPER); }

    @Override
    protected boolean additionalValidation() {
        return true;
    }

    @Override
    protected void screenSpecificSetup() {
        inputField.getChildren().clear();
        inputField.getChildren().addAll(labelMap.get(IMAGE_GROUP), userInputFields.get(IMAGE_GROUP));
        ComboBox groupChoice = (ComboBox) userInputFields.get(IMAGE_GROUP);
        groupChoice.getSelectionModel().selectedItemProperty().addListener(e->{
            String newKey = groupChoice.getSelectionModel().getSelectedItem().toString();
            String path = myKeysResources.getString(newKey);
            images.getChildren().clear();
            stateRange = new int[]{-1, -1};
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
                setMinMax(key);
                ImageView view = buildImageView(imagePathResources.getString(key));
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

    private void setMinMax(String value){
        int intValue = Integer.parseInt(value);
        if (stateRange[0] == -1 || intValue<stateRange[0]) stateRange[0] = intValue;
        if (stateRange[1] == -1 || intValue>stateRange[1]) stateRange[1] = intValue;
    }

    private ImageView buildImageView(String imageName) throws FileNotFoundException{
        String imagePath = IMAGERESOURCES + imageName + ".png";
        FileInputStream input = new FileInputStream(imagePath);
        Image image = new Image(input);
        ImageView view = new ImageView(image);
        view.setPreserveRatio(true);
        view.setFitWidth(WIDTH);
        return view;
    }
}
