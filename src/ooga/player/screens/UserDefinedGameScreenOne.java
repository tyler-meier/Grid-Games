package ooga.player.screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ooga.data.UserProfile;
import ooga.player.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class UserDefinedGameScreenOne extends SuperScreen {
    private static final String KEYS_RESOURCES_PATH = "resources.";
    private Map<String, String> selectedEngineAttributes = new HashMap<>();
    private ResourceBundle engineKeysResources;
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
        engineKeysResources =  ResourceBundle.getBundle(KEYS_RESOURCES_PATH + "EngineKeys");
    }

    public Scene setUpScene(){
        Label newGameLabel = makeNewGameLabel();
        VBox engineCharacteristicSelection = makeEngineCharSelection();
        ScrollPane myEngineScroller = new ScrollPane();
        myEngineScroller.setContent(engineCharacteristicSelection);
        VBox nextButton = setUpButtons();
        return styleScene(newGameLabel, myEngineScroller, nextButton);
    }

    public Map<String,String> getUserSelectedEngineAttributes(){
        return selectedEngineAttributes;
    }


    private Label makeNewGameLabel(){
        return new Label(myStringResources.getString("NewGameLabel"));
    }

    private VBox makeEngineCharSelection(){
        for(String key : Collections.list(engineKeysResources.getKeys())){
            selectedEngineAttributes.put(key, engineKeysResources.getString(key));
        }
        Label addNewCells = new Label(myStringResources.getString("AddNewCells"));
        this.addNewCells.getItems().addAll(myStringResources.getString("True"), myStringResources.getString("False"));
        Label validator = new Label(myStringResources.getString("TypeOfValidator"));
        this.validator.getItems().addAll(myStringResources.getString("Pair"), myStringResources.getString("Switch"));
        Label matchFinder = new Label(myStringResources.getString("TypeOfMatchFinder"));
        this.matchFinder.getItems().addAll(myStringResources.getString("Flipped"), myStringResources.getString("Open"));
        Label numSelectedPerMove = new Label(myStringResources.getString("NumCells")); //hardcode so that it can be valid
        Label noHiddenCells = new Label(myStringResources.getString("HasHiddenCells"));
        this.noHiddenCells.getItems().addAll(myStringResources.getString("True"), myStringResources.getString("False"));
        Label maxStateNumber = new Label(myStringResources.getString("MaxStateNum"));
        this.maxStateNumber.getItems().addAll(myStringResources.getString("1"), myStringResources.getString("2"), myStringResources.getString("3"),
                myStringResources.getString("4"), myStringResources.getString("5"), myStringResources.getString("6"));
        Label pointsPerCell = new Label(myStringResources.getString("PointsPerCell"));
        Label secondsOpen = new Label(myStringResources.getString("SecondsOpen"));
        return styleContents(addNewCells, this.addNewCells, validator, this.validator,
                matchFinder, this.matchFinder, numSelectedPerMove, this.numSelectedPerMove, noHiddenCells,
                this.noHiddenCells,maxStateNumber, this.maxStateNumber, pointsPerCell, this.pointsPerCell, secondsOpen, this.secondsOpen);
    }


    private VBox setUpButtons(){
        Button startButton = makeButton("Next", e -> {
            makeEngineMap();
            myPlayer.setUpMakeNewGameScreenTwo();
        });

        return styleContents(startButton);
    }

    private void makeEngineMap(){
        selectedEngineAttributes.put("AddNewCells", this.addNewCells.getValue());
        selectedEngineAttributes.put("Validator", this.validator.getValue());
        selectedEngineAttributes.put("MatchFinder", this.matchFinder.getValue());
        selectedEngineAttributes.put("NumSelectedPerMove", this.numSelectedPerMove.getText());
        selectedEngineAttributes.put("NoHiddenCells", this.noHiddenCells.getValue());
        selectedEngineAttributes.put("MaxStateNumber", this.maxStateNumber.getValue());
        selectedEngineAttributes.put("PointsPerCell", this.pointsPerCell.getText());
        selectedEngineAttributes.put("SecondsOpen", this.secondsOpen.getText());
    }

}
