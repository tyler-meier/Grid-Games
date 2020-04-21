package ooga.player.screens;

import ooga.player.Player;
import ooga.player.exceptions.NewUserDefinedGameException;


import java.util.ResourceBundle;

public class UserDefinedGameScreenTwo extends UserDefinedGameScreen {
    //TODO: need to figure out how to give data the correct info for user made game so reset and save works
    //TODO: still need to make images dynamic
    //TODO: figure out exception handling
    private static final String MY_KEYS = "GameKeys";
    private static final String BUTTON_TEXT = "Next";
    private static final String GAME_LABEL = "NewGameLabelTwo";


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

    @Override
    protected boolean additionalValidation() {
        return true;
    }
}
