package ooga.player.screens;

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
            }
        }
        return myGrid;
    }

    private Label makeLabel(){
        return new Label(myStringResources.getString("InitialGrid"));
    }

    private Label makeWarningLabel(){
        return new Label(myStringResources.getString("InitialGridReminder"));
    }


    private VBox setUpButtons(){
        Button startButton = makeButton("StartCommand", e -> {
            try {
                getStates();
                myPlayer.setGameType("UserMadeGame");
                myPlayer.getUserMAdeStartButton().handle(e);
            } catch (NewUserDefinedGameException p){
                System.out.println("this is where the error is happening");
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
