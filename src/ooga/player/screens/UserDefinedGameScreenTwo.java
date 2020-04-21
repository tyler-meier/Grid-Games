package ooga.player.screens;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ooga.player.Player;
import ooga.player.exceptions.NewUserDefinedGameException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class UserDefinedGameScreenTwo extends SuperScreen {
    //TODO: need to figure out how to give data the correct info for user made game so reset and save works
    //TODO: still need to make images dynamic
    //TODO: figure out exception handling
    private static final String KEYS_RESOURCES_PATH = "resources.";
    private Map<String, String> selectedGameAttributes = new HashMap<>();
    private ComboBox<String> level = new ComboBox<>();
    private ComboBox<String> score = new ComboBox<String>();
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
        Label Score = new Label(myStringResources.getString("StartingScore")); //hard code as 0
        this.score.getItems().addAll(myStringResources.getString("0"));
        Label TargetScore = new Label(myStringResources.getString("TargetScore"));
        Label LossStat = new Label(myStringResources.getString("LossStat"));
        this.lossStat.getItems().addAll(myStringResources.getString("MovesLeftNS"), myStringResources.getString("Time"), myStringResources.getString("LivesLeftNS"));
        Label LivesLeft = new Label(myStringResources.getString("LivesLeft"));
        Label MovesLeft = new Label(myStringResources.getString("MovesLeft"));
        Label Time = new Label(myStringResources.getString("Time"));
        Label numRows = new Label(myStringResources.getString("NumRows")); //hardcode so there is upper limit
        Label numCols = new Label(myStringResources.getString("NumCols")); //hardcode so there is upper limit
        return styleContents(Level, this.level, Score, this.score,
                TargetScore, this.targetScore, LossStat, this.lossStat, LivesLeft,
                this.livesLeft, MovesLeft, this.movesLeft, Time, this.time, numRows,
                this.numRows, numCols, this.numCols);
    }


    private VBox setUpButtons(){
        Button startButton = makeButton("Next", e -> {
            try{
                makeGamesMap();
                myPlayer.setUpMakeNewGameScreenThree(getSelectedNumRows(), getSelectedNumCols());
            }
            catch(NewUserDefinedGameException p){
                myErrorMessage.textProperty().setValue(p.getMessage());
            }
        });

        return styleContents(startButton);
    }

    private void makeGamesMap(){
        selectedGameAttributes.put("Level", this.level.getValue());
        selectedGameAttributes.put("Score", this.score.getValue());
        selectedGameAttributes.put("TargetScore", this.targetScore.getText());
        selectedGameAttributes.put("LossStat", this.lossStat.getValue());
        selectedGameAttributes.put("MovesLeft", this.movesLeft.getText());
        selectedGameAttributes.put("LivesLeft", this.livesLeft.getText());
        selectedGameAttributes.put("Time", this.time.getText());
        selectedGameAttributes.put("numRows", this.numRows.getText());
        selectedGameAttributes.put("numCols", this.numCols.getText());
    }

    private int getSelectedNumRows(){
        try{
            Integer.parseInt(time.getText());
            return Integer.parseInt(selectedGameAttributes.get("numRows"));
        }
        catch(NumberFormatException e){
            //TODO: make the error message display on the screen
            NewUserDefinedGameException p = new NewUserDefinedGameException();
            myErrorMessage.textProperty().setValue(p.getMessage());
        }
        return 0;
    }

    private int getSelectedNumCols(){
        try{
            return Integer.parseInt(selectedGameAttributes.get("numCols"));
        }
        catch(NumberFormatException e){
            //TODO: make the error message display on the screen
            NewUserDefinedGameException p = new NewUserDefinedGameException();
            myErrorMessage.textProperty().setValue(p.getMessage());
        }
        return 0;
    }

    private boolean isInteger(TextField text){
        try{
            Integer.parseInt(text.getText());
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
