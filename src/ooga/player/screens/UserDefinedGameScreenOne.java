package ooga.player.screens;

import ooga.player.Player;
import ooga.player.exceptions.NewUserDefinedGameException;

import java.util.ResourceBundle;

public class UserDefinedGameScreenOne extends UserDefinedGameScreen {
    private static final String MY_KEYS = "EngineKeys";
    private static final String BUTTON_TEXT = "Next";
    private static final String GAME_LABEL = "NewGameLabel";
    private static final String MAX_STATE = "MaxStateNumber";

    public UserDefinedGameScreenOne(Player thisPlayer) {
        super(thisPlayer);
        myKeysResources = ResourceBundle.getBundle(KEYS_RESOURCES_PATH + MY_KEYS);
        myButtonEvent = event -> {
            try{
                buildMap();
                myPlayer.setUpMakeNewGameScreenTwo();
            }
            catch(NewUserDefinedGameException p){
                myErrorMessage.textProperty().setValue(p.getMessage());
            }
        };
        myButtonText = BUTTON_TEXT;
        gameLabel = GAME_LABEL;
    }

    public int getMaxState(){
        return Integer.parseInt(selectedAttributes.get(MAX_STATE));
    }

    @Override
    protected boolean additionalValidation() {
        return true;
    }


}
