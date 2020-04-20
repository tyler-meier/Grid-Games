package ooga.player.screens;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ooga.player.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class UserDefinedGameScreenTwo extends SuperScreen {
    //TODO: need to figure out how to give data the correct info for user made game so reset and save works
    //TODO: put strings in resource file
    //TODO: still need to make images dynamic
    private static final String KEYS_RESOURCES_PATH = "resources.";
    private Map<String, String> selectedGameAttributes = new HashMap<>();
    private ComboBox<String> level = new ComboBox<>();
    private TextField score = new TextField();
    private TextField targetScore = new TextField();
    private ComboBox<String> lossStat = new ComboBox<>();
    private TextField livesLeft = new TextField();
    private TextField movesLeft = new TextField();
    private TextField time = new TextField();
    private TextField numRows = new TextField();
    private TextField numCols = new TextField();
    private ResourceBundle gameKeysResources;


    public UserDefinedGameScreenTwo(Player thisPlayer) {
        super(thisPlayer);
        gameKeysResources = ResourceBundle.getBundle(KEYS_RESOURCES_PATH + "GameKeys");
    }

    public Scene setUpScene(){
        Label newGameLabel = makeNewGameLabel();
        VBox gameCharacteristicSelection = makeGameCharSelection();
        ScrollPane myGameScroller = new ScrollPane();
        myGameScroller.setContent(gameCharacteristicSelection);
        VBox nextButton = setUpButtons();
        return styleScene(newGameLabel, myGameScroller, nextButton);
    }

    public Map<String, String> getUserSelectedGameAttributes(){
        return selectedGameAttributes;
    }

    private Label makeNewGameLabel(){
        return new Label("Please finish selecting the attributes of your game!");
    }

    private VBox makeGameCharSelection(){
        for(String key : Collections.list(gameKeysResources.getKeys())){
            selectedGameAttributes.put(key, gameKeysResources.getString(key));
        }
        Label Level = new Label("Level");
        this.level.getItems().addAll("1");
        Label Score = new Label("Starting Score");
       // this.score.getItems().addAll("0");
        Label TargetScore = new Label("Target Score");
        //this.targetScore.getItems().addAll("10", "100", "500");
        Label LossStat = new Label("Loss Stat");
        this.lossStat.getItems().addAll("MovesLeft", "Time", "LivesLeft");
        Label LivesLeft = new Label("Lives");
        //this.livesLeft.getItems().addAll("5", "10");
        Label MovesLeft = new Label("Moves Left");
        //this.movesLeft.getItems().addAll("5", "10", "20");
        Label Time = new Label("Time (seconds)");
        //this.time.getItems().addAll("60");
        Label numRows = new Label("Number of Rows");
        //this.numRows.getItems().addAll("4");
        Label numCols = new Label("Number of Cols");
        //this.numCols.getItems().addAll("4");
        return styleContents(Level, this.level, Score, this.score,
                TargetScore, this.targetScore, LossStat, this.lossStat, LivesLeft,
                this.livesLeft, MovesLeft, this.movesLeft, Time, this.time, numRows,
                this.numRows, numCols, this.numCols);
    }


    private VBox setUpButtons(){
        Button startButton = makeButton("Next", e -> {
            makeGamesMap();
            myPlayer.setUpMakeNewGameScreenThree(getSelectedNumRows(), getSelectedNumCols());
            //myPlayer.setGameType("UserMadeGame");
            //myPlayer.getUserMAdeStartButton().handle(e);
        });

        return styleContents(startButton);
    }


    /*
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

     */

    private void makeGamesMap(){
        selectedGameAttributes.put("Level", this.level.getValue());
        selectedGameAttributes.put("Score", this.score.getText());
        selectedGameAttributes.put("TargetScore", this.targetScore.getText());
        selectedGameAttributes.put("LossStat", this.lossStat.getValue());
        selectedGameAttributes.put("MovesLeft", this.movesLeft.getText());
        selectedGameAttributes.put("LivesLeft", this.livesLeft.getText());
        selectedGameAttributes.put("Time", this.time.getText());
        selectedGameAttributes.put("numRows", this.numRows.getText());
        selectedGameAttributes.put("numCols", this.numCols.getText());
    }

    private int getSelectedNumRows(){
        return Integer.parseInt(selectedGameAttributes.get("numRows"));
    }

    private int getSelectedNumCols(){
        return Integer.parseInt(selectedGameAttributes.get("numCols"));
    }
}
