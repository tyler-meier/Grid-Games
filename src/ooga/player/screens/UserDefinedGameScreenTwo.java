package ooga.player.screens;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ooga.player.Player;
import ooga.player.exceptions.NewUserDefinedGameException;


import java.util.ResourceBundle;

public class UserDefinedGameScreenTwo extends UserDefinedGameScreen {
    private static final String MY_KEYS = "GameKeys";
    private static final String BUTTON_TEXT = "Next";
    private static final String GAME_LABEL = "NewGameLabelTwo";
    private static final String TARGET_SCORE = "TargetScore";
    private static final String LOSS_STAT = "LossStat";
    private static final String LIVES_LEFT = "LivesLeft";
    private VBox lossStatBox = new VBox();


    public UserDefinedGameScreenTwo(Player thisPlayer) {
        super(thisPlayer);
        myKeysResources = ResourceBundle.getBundle(KEYS_RESOURCES_PATH + MY_KEYS);
        myButtonEvent = event -> {
            try{
                buildMap();
                //TODO: can we standardize?
                myPlayer.setUpMakeNewGameScreenThree();
            }
            catch(NewUserDefinedGameException p){
                myErrorMessage.textProperty().setValue(p.getMessage());
            }
        };
        myButtonText = BUTTON_TEXT;
        gameLabel = GAME_LABEL;
    }

    public void setLossStatOptions(boolean canLoseLives){
        if (!canLoseLives){
            ComboBox lossStat = (ComboBox) userInputFields.get(LOSS_STAT);
            lossStat.getItems().remove(LIVES_LEFT);
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
