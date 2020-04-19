package ooga.player.screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import ooga.data.UserProfile;
import ooga.player.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.Label;

public class UserDefinedGameScreenOne extends SuperScreen {
    private static final String KEYS_RESOURCES_PATH = "resources.";
    private Map<String, String> selectedEngineAttributes = new HashMap<>();
    private ResourceBundle engineKeysResources;
    private ComboBox<String> addNewCells = new ComboBox<>();
    private ComboBox<String> validator = new ComboBox<>();
    private ComboBox<String> matchFinder = new ComboBox<>();
    private ComboBox<String> numSelectedPerMove = new ComboBox<>();
    private ComboBox<String> noHiddenCells = new ComboBox<>();
    private ComboBox<String> maxStateNumber = new ComboBox<>();
    private ComboBox<String> pointsPerCell = new ComboBox<>();
    private ComboBox<String> secondsOpen = new ComboBox<>();

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
        return new Label("Please select the characteristics of your custom game !");
    }

    private VBox makeEngineCharSelection(){
        for(String key : Collections.list(engineKeysResources.getKeys())){
            selectedEngineAttributes.put(key, engineKeysResources.getString(key));
        }
        Label addNewCells = new Label("Add New Cells?");
        this.addNewCells.getItems().addAll("True", "False");
        Label validator = new Label("Type of Validator");
        this.validator.getItems().addAll("PairValidator", "SwitchValidator");
        Label matchFinder = new Label("Type of Match Finder");
        this.matchFinder.getItems().addAll("FlippedFinder", "OpenFinder");
        Label numSelectedPerMove = new Label("Number of cells to select per move");
        this.numSelectedPerMove.getItems().addAll("1","2","3", "4");
        Label noHiddenCells = new Label("Does not have hidden Cells?");
        this.noHiddenCells.getItems().addAll("True", "False");
        Label maxStateNumber = new Label("Max state number");
        this.maxStateNumber.getItems().addAll("6");
        Label pointsPerCell = new Label("How many points per cell?");
        this.pointsPerCell.getItems().addAll("5", "10");
        Label secondsOpen = new Label("How many seconds open?");
        this.secondsOpen.getItems().addAll("0", "2", "4");
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
        selectedEngineAttributes.put("NumSelectedPerMove", this.numSelectedPerMove.getValue());
        selectedEngineAttributes.put("NoHiddenCells", this.noHiddenCells.getValue());
        selectedEngineAttributes.put("MaxStateNumber", this.maxStateNumber.getValue());
        selectedEngineAttributes.put("PointsPerCell", this.pointsPerCell.getValue());
        selectedEngineAttributes.put("SecondsOpen", this.secondsOpen.getValue());
    }

}
