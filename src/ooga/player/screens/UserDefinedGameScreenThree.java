package ooga.player.screens;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ooga.player.Player;
import ooga.player.exceptions.NewUserDefinedGameException;


public class UserDefinedGameScreenThree extends SuperScreen {
    private int numberRows;
    private int numberColumns;
    private int[][] initialStates;
    GridPane myGrid = new GridPane();
    public UserDefinedGameScreenThree(Player thisPlayer) {
        super(thisPlayer);
    }

    public Scene setUpScene(int numRows, int numCols){
        numberRows = numRows;
        numberColumns = numCols;
        Label prompt = makeLabel();
        Label warning = makeWarningLabel();
        // need to figure out how to let the user select the state for every single cell
        GridPane enterStates = setUpGridConfig();
        VBox goButton = setUpButtons();
        return styleScene(prompt, warning, enterStates, goButton);
    }

    public int[][] getUserSelectedInitialStates(){
        return initialStates;
    }

    private GridPane setUpGridConfig(){
        initialStates = new int[numberRows][numberColumns];
        myGrid.setGridLinesVisible(true);

        for(int row = 0; row < numberRows; row++){
            for(int col = 0; col < numberColumns; col++){
                TextField state = new TextField();
                myGrid.setRowIndex(state, row);
                myGrid.setColumnIndex(state, col);
                myGrid.getChildren().add(state);
                System.out.println("number of children in the grid: " + myGrid.getChildren().size());
                //initialStates[row][col].bind((StringProperty)state.getText());
            }
        }
        // now initialStates should have all of the input states given by the user
        return myGrid;
    }

    private Label makeLabel(){
        return new Label("Please give the initial states for the cells in your grid.");
    }

    private Label makeWarningLabel(){
        return new Label("Remember - do not put states greater than the max state you selected!");
    }


    private VBox setUpButtons(){
        Button startButton = makeButton("StartCommand", e -> {
            try {
                getStates();
                myPlayer.setGameType("UserMadeGame");
                myPlayer.getUserMAdeStartButton().handle(e);
            } catch (NewUserDefinedGameException p){
                //p.printStackTrace();
                myErrorMessage.textProperty().setValue(p.getMessage());
            }
        });
        return styleContents(startButton);
    }

    private void getStates(){
        for(Node node: myGrid.getChildren()){
            if(myGrid.getRowIndex(node) != null){
                int row = myGrid.getRowIndex(node);
                int col = myGrid.getColumnIndex(node);
                int state = Integer.parseInt(((TextField) node).getText());
                initialStates[row][col] = state;
            }
        }
    }
}
