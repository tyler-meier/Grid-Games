package ooga.player.screens;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ooga.player.Player;
import ooga.player.exceptions.NewUserDefinedGameException;


import java.awt.*;
import java.util.ResourceBundle;

/**
 * Screen where the user selects the game attributes of their
 * new game.
 * @author Tanvi Pabby and Natalie Novitsky.
 */
public class UserDefinedGameScreenTwo extends UserDefinedGameScreen {
    private static final String MY_KEYS = "GameKeys";
    private static final String BUTTON_TEXT = "Next";
    private static final String GAME_LABEL = "NewGameLabelTwo";
    private static final String TARGET_SCORE = "TargetScore";
    private static final String LOSS_STAT = "LossStat";
    private static final String LIVES_LEFT = "LivesLeft";
    private static final String ROWS = "numRows";
    private static final String COLUMNS = "numColumns";
    private VBox lossStatBox = new VBox();


    public UserDefinedGameScreenTwo(Player thisPlayer) {
        super(thisPlayer);
        myKeysResources = ResourceBundle.getBundle(KEYS_RESOURCES_PATH + MY_KEYS);
        myButtonEvent = event -> {
            handleButtonEvent();
        };
        myButtonText = BUTTON_TEXT;
        gameLabel = GAME_LABEL;
    }

    /**
     * Adds to the grid size of the new game
     * @param p
     */
    public void addGridSize(Point p){
        selectedAttributes.put(ROWS, String.valueOf(p.x));
        selectedAttributes.put(COLUMNS, String.valueOf(p.y));
    }

    /**
     * Sets the loss stat of the new game.
     * @param canLoseLives
     */
    public void setLossStatOptions(boolean canLoseLives){
        if (!canLoseLives){
            ComboBox lossStat = (ComboBox) userInputFields.get(LOSS_STAT);
            lossStat.getItems().remove(LIVES_LEFT);
        }
    }

    private void handleButtonEvent(){
        try{
            buildMap();
            //TODO: can we standardize?
            myPlayer.setUpMakeNewGameScreenThree();

        }
        catch(NewUserDefinedGameException p){
            myErrorMessage.textProperty().setValue(p.getMessage());
        }
    }

    @Override
    protected void screenSpecificSetup() {
        lossStatBox.setAlignment(Pos.CENTER);
        inputField.getChildren().clear();
        inputField.getChildren().addAll(labelMap.get(TARGET_SCORE), userInputFields.get(TARGET_SCORE), labelMap.get(LOSS_STAT), userInputFields.get(LOSS_STAT), lossStatBox);
        ComboBox lossStat = (ComboBox) userInputFields.get(LOSS_STAT);
        lossStat.getSelectionModel().selectedItemProperty().addListener(e->{
            lossStatBox.getChildren().clear();
            String newKey = lossStat.getSelectionModel().getSelectedItem().toString();
            lossStatBox.getChildren().addAll(labelMap.get(newKey), userInputFields.get(newKey));
        });
    }

    @Override
    protected boolean additionalValidation() {
        for (Node kid:lossStatBox.getChildren()){
            if (kid instanceof TextField && !isInteger(((TextField) kid).getText())) return false;
        }
        return true;

    }
}
