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
        return new Label(myStringResources.getString("NewGameLabelTwo"));
    }

    private VBox makeGameCharSelection(){
        for(String key : Collections.list(gameKeysResources.getKeys())){
            selectedGameAttributes.put(key, gameKeysResources.getString(key));
        }
        Label Level = new Label(myStringResources.getString("Level"));
        this.level.getItems().addAll(myStringResources.getString("1"));
        Label Score = new Label(myStringResources.getString("StartingScore"));
       // this.score.getItems().addAll("0");
        Label TargetScore = new Label(myStringResources.getString("TargetScore"));
        //this.targetScore.getItems().addAll("10", "100", "500");
        Label LossStat = new Label(myStringResources.getString("LossStat"));
        this.lossStat.getItems().addAll(myStringResources.getString("MovesLeftNS"), myStringResources.getString("Time"), myStringResources.getString("LivesLeftNS"));
        Label LivesLeft = new Label(myStringResources.getString("LivesLeft"));
        //this.livesLeft.getItems().addAll("5", "10");
        Label MovesLeft = new Label(myStringResources.getString("MovesLeft"));
        //this.movesLeft.getItems().addAll("5", "10", "20");
        Label Time = new Label(myStringResources.getString("Time"));
        //this.time.getItems().addAll("60");
        Label numRows = new Label(myStringResources.getString("NumRows"));
        //this.numRows.getItems().addAll("4");
        Label numCols = new Label(myStringResources.getString("NumCols"));
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
