package ooga.player.screens;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ooga.data.UserProfile;
import ooga.player.Player;
import ooga.player.exceptions.NewUserDefinedGameException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class UserDefinedGameScreenOne extends UserDefinedGameScreen {
    private static final String KEYS_RESOURCES_PATH = "resources.";
    private static final String MY_KEYS = "EngineKeys";
    private static final String BUTTON_TEXT = "Next";
    private static final String GAME_LABEL = "NewGameLabel";
    private Map<String, String> selectedEngineAttributes = new HashMap<>();
    private ComboBox<String> addNewCells = new ComboBox<>();
    private ComboBox<String> validator = new ComboBox<>();
    private ComboBox<String> matchFinder = new ComboBox<>();
    private TextField numSelectedPerMove = new TextField();
    private ComboBox<String> noHiddenCells = new ComboBox<>();
    private ComboBox<String> maxStateNumber = new ComboBox<>();
    private TextField pointsPerCell = new TextField();
    private TextField secondsOpen = new TextField();


    public UserDefinedGameScreenOne(Player thisPlayer) {
        super(thisPlayer);
        myKeysResources = ResourceBundle.getBundle(KEYS_RESOURCES_PATH + MY_KEYS);
        myButtonEvent = event -> {
            try{
                selectedEngineAttributes = buildMap();
                myPlayer.setUpMakeNewGameScreenTwo();
            }
            catch(NewUserDefinedGameException p){
                myErrorMessage.textProperty().setValue(p.getMessage());
            }
        };
        myButtonText = BUTTON_TEXT;
        gameLabel = GAME_LABEL;
    }

//    public Scene setUpScene(){
//        Label newGameLabel = makeNewGameLabel();
//        Node engineCharacteristicSelection = buildInputFields();
//        ScrollPane myEngineScroller = new ScrollPane();
//        myEngineScroller.setContent(engineCharacteristicSelection);
//        VBox nextButton = setUpButtons();
//        return styleScene(newGameLabel, myEngineScroller, nextButton);
//    }

    public Map<String,String> getUserSelectedEngineAttributes(){
        return selectedEngineAttributes;
    }


//    private VBox makeEngineCharSelection(){
//        VBox myVBox = new VBox();
//        myVBox.setSpacing(10);
//        myVBox.setAlignment(Pos.CENTER);
//        for(String key : Collections.list(engineKeysResources.getKeys())){
//            Label label = new Label(newGameStringsResources.getString(key));
//            if (isInteger(engineKeysResources.getString(key))) userInputFields.put(key, new TextField());
//            else {
//                ComboBox<String> dropDown = new ComboBox<>();
//                String[] options = engineKeysResources.getString(key).split(SPACE);
//                for (String s:options) dropDown.getItems().add(s);
//                userInputFields.put(key, dropDown);
//            }
//            myVBox.getChildren().addAll(label, userInputFields.get(key));
//            //selectedEngineAttributes.put(key, engineKeysResources.getString(key));
//        }

//        Label addNewCells = new Label(myStringResources.getString("AddNewCells"));
//        this.addNewCells.getItems().addAll(myStringResources.getString("True"), myStringResources.getString("False"));
//        Label validator = new Label(myStringResources.getString("TypeOfValidator"));
//        this.validator.getItems().addAll(myStringResources.getString("Pair"), myStringResources.getString("Switch"));
//        Label matchFinder = new Label(myStringResources.getString("TypeOfMatchFinder"));
//        this.matchFinder.getItems().addAll(myStringResources.getString("Flipped"), myStringResources.getString("Open"));
//        Label numSelectedPerMove = new Label(myStringResources.getString("NumCells")); //hardcode so that it can be valid
//        Label noHiddenCells = new Label(myStringResources.getString("HasHiddenCells"));
//        this.noHiddenCells.getItems().addAll(myStringResources.getString("True"), myStringResources.getString("False"));
//        Label maxStateNumber = new Label(myStringResources.getString("MaxStateNum"));
//        this.maxStateNumber.getItems().addAll(myStringResources.getString("1"), myStringResources.getString("2"), myStringResources.getString("3"),
//                myStringResources.getString("4"), myStringResources.getString("5"), myStringResources.getString("6"));
//        Label pointsPerCell = new Label(myStringResources.getString("PointsPerCell"));
//        Label secondsOpen = new Label(myStringResources.getString("SecondsOpen"));
//        return styleContents(addNewCells, this.addNewCells, validator, this.validator,
//                matchFinder, this.matchFinder, numSelectedPerMove, this.numSelectedPerMove, noHiddenCells,
//                this.noHiddenCells,maxStateNumber, this.maxStateNumber, pointsPerCell, this.pointsPerCell, secondsOpen, this.secondsOpen);
//        return myVBox;
//    }


/*
    private void buildMap(){
        for (String key:integerInputs) if (!isInteger(selectedEngineAttributes.get(key))) System.out.println("bad input");//throw error;

        selectedEngineAttributes.put("AddNewCells", this.addNewCells.getValue());
        selectedEngineAttributes.put("Validator", this.validator.getValue());
        selectedEngineAttributes.put("MatchFinder", this.matchFinder.getValue());
        if (isInteger(numSelectedPerMove.getText()))selectedEngineAttributes.put("NumSelectedPerMove", this.numSelectedPerMove.getText());
        else System.out.println("nope");

        selectedEngineAttributes.put("NoHiddenCells", this.noHiddenCells.getValue());
        selectedEngineAttributes.put("MaxStateNumber", this.maxStateNumber.getValue());
        selectedEngineAttributes.put("PointsPerCell", this.pointsPerCell.getText());
        selectedEngineAttributes.put("SecondsOpen", this.secondsOpen.getText());
    }

 */

}
