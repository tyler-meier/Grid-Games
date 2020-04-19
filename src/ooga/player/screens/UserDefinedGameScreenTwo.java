package ooga.player.screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import ooga.player.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class UserDefinedGameScreenTwo extends SuperScreen {
    //TODO: still need to make initial grid config dynamic
    //TODO: need to do exception handling for this part
    //TODO: need to figure out how to give data the correct info for user made game so reset and save works
    
    private static final String KEYS_RESOURCES_PATH = "resources.";

    private Map<String, String> selectedEngineAttributes = new HashMap<>();
    private Map<String, String> selectedGameAttributes = new HashMap<>();
    private ComboBox<String> level = new ComboBox<>();
    private ComboBox<String> score = new ComboBox<>();
    private ComboBox<String> targetScore = new ComboBox<>();
    private ComboBox<String> lossStat = new ComboBox<>();
    private ComboBox<String> livesLeft = new ComboBox<>();
    private ComboBox<String> movesLeft = new ComboBox<>();
    private ComboBox<String> time = new ComboBox<>();
    private ResourceBundle gameKeysResources;


    public UserDefinedGameScreenTwo(Player thisPlayer) {
        super(thisPlayer);
        gameKeysResources = ResourceBundle.getBundle(KEYS_RESOURCES_PATH + "GameKeys");
    }

    public Scene setUpScene(Map<String, String> selectedEngineAttributes, Map<String, String> selectedGameAttributes){
        this.selectedEngineAttributes = selectedEngineAttributes; //here the engine one should already be set up
        this.selectedGameAttributes = selectedGameAttributes;
        VBox gameCharacteristicSelection = makeGameCharSelection();
        ScrollPane myGameScroller = new ScrollPane();
        myGameScroller.setContent(gameCharacteristicSelection);
        VBox goButton = setUpButtons();
        return styleScene(gameCharacteristicSelection, goButton);
    }

    public Map<String,String> getUserSelectedEngineAttributes(){
        return selectedEngineAttributes;
    }

    public Map<String, String> getUserSelectedGameAttributes(){
        return selectedGameAttributes;
    }

    private VBox makeGameCharSelection(){
        for(String key : Collections.list(gameKeysResources.getKeys())){
            selectedGameAttributes.put(key, gameKeysResources.getString(key));
        }
        Label Level = new Label("Level");
        this.level.getItems().addAll("1");
        Label Score = new Label("Starting Score");
        this.score.getItems().addAll("0");
        Label TargetScore = new Label("Target Score");
        this.targetScore.getItems().addAll("10", "100", "500");
        Label LossStat = new Label("Loss Stat");
        this.lossStat.getItems().addAll("MovesLeft", "Time", "LivesLeft");
        Label LivesLeft = new Label("Lives");
        this.livesLeft.getItems().addAll("5", "10");
        Label MovesLeft = new Label("Moves Left");
        this.movesLeft.getItems().addAll("5", "10", "20");
        Label Time = new Label("Time");
        this.time.getItems().addAll("60");
        return styleContents(Level, this.level, Score, this.score,
                TargetScore, this.targetScore, LossStat, this.lossStat, LivesLeft,
                this.livesLeft, MovesLeft, this.movesLeft, Time, this.time);
    }

    private VBox setUpButtons(){
        Button startButton = makeButton("StartCommand", e -> {
            try {
                makeGamesMap();
                myPlayer.setGameType("UserMadeGame");
                myPlayer.getUserMAdeStartButton().handle(e);
            } catch (NullPointerException p){
                p.printStackTrace();
                myErrorMessage.textProperty().setValue(myStringResources.getString("BlankChoice"));
            }
        });

        return styleContents(startButton);
    }

    private void makeGamesMap(){
        selectedGameAttributes.put("Level", this.level.getValue());
        selectedGameAttributes.put("Score", this.score.getValue());
        selectedGameAttributes.put("TargetScore", this.targetScore.getValue());
        selectedGameAttributes.put("LossStat", this.lossStat.getValue());
        selectedGameAttributes.put("MovesLeft", this.movesLeft.getValue());
        selectedGameAttributes.put("LivesLeft", this.livesLeft.getValue());
        selectedGameAttributes.put("Time", this.time.getValue());
    }
}
