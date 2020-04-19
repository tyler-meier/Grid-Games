package ooga.player.screens;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ooga.player.Player;


public class UserDefinedGameScreenThree extends SuperScreen {
    private int numberRows;
    private int numberColumns;
    private StringProperty[][] initialStates;
    public UserDefinedGameScreenThree(Player thisPlayer) {
        super(thisPlayer);
    }

    public Scene setUpScene(int numRows, int numCols){
        numberRows = numRows;
        numberColumns = numCols;
        Label prompt = makeLabel();
        // need to figure out how to let the user select the state for every single cell
        GridPane enterStates = setUpGridConfig();
        VBox goButton = setUpButtons();
        return styleScene(prompt, enterStates, goButton);
    }

    public StringProperty[][] getUserSelectedInitialStates(){
        return initialStates;
    }

    private GridPane setUpGridConfig(){
        initialStates = new StringProperty[numberRows][numberColumns];
        GridPane myGrid = new GridPane();
        myGrid.setGridLinesVisible(true);

        for(int row = 0; row < numberRows; row++){
            for(int col = 0; col < numberColumns; col++){
                TextField state = new TextField();
                myGrid.getChildren().add(state);
                //initialStates[row][col].bind((StringProperty)state.getText());
            }
        }
        // now initialStates should have all of the input states given by the user
        return myGrid;
    }

    private Label makeLabel(){
        return new Label("Please give the initial states for the cells in your grid.");
    }

    private VBox setUpButtons(){
        Button startButton = makeButton("StartCommand", e -> {
            try {
                //getStates();
                // something for the grid here
                myPlayer.setGameType("UserMadeGame");
                myPlayer.getUserMAdeStartButton().handle(e);
            } catch (NullPointerException p){
                p.printStackTrace();
                myErrorMessage.textProperty().setValue(myStringResources.getString("BlankChoice"));
            }
        });
        return styleContents(startButton);
    }

    private void getStates(){
        for(int row = 0; row < numberRows; row++){
            for(int col = 0; col < numberColumns; col++){
                //initialStates[row][col] = Integer.parseInt(state.getText());
            }
        }
    }
}
