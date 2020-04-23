package ooga.player.screens;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ooga.player.Player;
import ooga.player.exceptions.NewUserDefinedGameException;

import java.util.ResourceBundle;

public class UserDefinedGameScreenOne extends UserDefinedGameScreen {
    private static final String MY_KEYS = "EngineKeys";
    private static final String BUTTON_TEXT = "Next";
    private static final String GAME_LABEL = "NewGameLabel";
    private static final String MAX_STATE = "MaxStateNumber";
    private static final String NO_HIDDEN_CELLS = "NoHiddenCells";
    private static final String NEW_CELLS = "AddNewCells";
    private static final String SECONDS_OPEN = "SecondsOpen";
    private static final String POINTS = "PointsPerCell";
    private static final String MATCH_FINDER = "MatchFinder";
    private static final String VALIDATOR = "Validator";
    private static final String NUM_SELECTED = "NumSelectedPerMove";
    private static final String FALSE = "false";
    private static final String SWITCH = "SwitchValidator";
    private static final String PAIR = "PairValidator";
    private static final String OPEN = "OpenFinder";
    private static final String FLIPPED = "FlippedFinder";
    private static final String TITLE_PROMPT = "TitlePrompt";
    private TextField titleField = new TextField();


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


    public String getTitle() { return titleField.getText(); }
    public boolean hasHiddenCells() { return !Boolean.parseBoolean(selectedAttributes.get(NO_HIDDEN_CELLS)); }

    @Override
    protected void screenSpecificSetup() {
        inputField.getChildren().clear();
        addGameNameField();
        inputField.getChildren().addAll(labelMap.get(POINTS), userInputFields.get(POINTS), labelMap.get(MAX_STATE), userInputFields.get(MAX_STATE), labelMap.get(NO_HIDDEN_CELLS), userInputFields.get(NO_HIDDEN_CELLS));
        ComboBox noHiddenCells = (ComboBox) userInputFields.get(NO_HIDDEN_CELLS);
        noHiddenCells.getSelectionModel().selectedItemProperty().addListener(e->{
            if (noHiddenCells.getSelectionModel().getSelectedItem().equals(FALSE)){
                ((ComboBox) userInputFields.get(VALIDATOR)).setValue(PAIR);
                ((ComboBox) userInputFields.get(MATCH_FINDER)).setValue(FLIPPED);
                ((ComboBox) userInputFields.get(NEW_CELLS)).setValue(FALSE);
                inputField.getChildren().addAll(labelMap.get(SECONDS_OPEN), userInputFields.get(SECONDS_OPEN), labelMap.get(NUM_SELECTED), userInputFields.get(NUM_SELECTED));
                inputField.getChildren().removeAll(labelMap.get(NEW_CELLS), userInputFields.get(NEW_CELLS));
            } else {
                ((ComboBox) userInputFields.get(VALIDATOR)).setValue(SWITCH);
                ((ComboBox) userInputFields.get(MATCH_FINDER)).setValue(OPEN);
                ((ComboBox) userInputFields.get(NEW_CELLS)).setValue(null);
                inputField.getChildren().addAll(labelMap.get(NEW_CELLS), userInputFields.get(NEW_CELLS));
                inputField.getChildren().removeAll(labelMap.get(SECONDS_OPEN), userInputFields.get(SECONDS_OPEN), labelMap.get(NUM_SELECTED), userInputFields.get(NUM_SELECTED));
            }
        });
    }

    @Override
    protected boolean additionalValidation() {
        int maxState = Integer.parseInt(selectedAttributes.get(MAX_STATE));
        if (!inRange(maxState)) return false;
        return !titleField.getText().isEmpty();
    }

    private void addGameNameField(){
        Label titleLabel = new Label(newGameStringsResources.getString(TITLE_PROMPT));
        inputField.getChildren().addAll(titleLabel, titleField);
    }

}
