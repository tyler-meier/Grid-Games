package ooga.player.screens;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ooga.player.Player;
import ooga.player.exceptions.NewUserDefinedGameException;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class UserDefinedGameScreenThree extends UserDefinedGameScreen {
    private static final String MY_KEYS = "NewGridKeys";
    private static final String BUTTON_TEXT = "MakeGrid";
    private static final String GAME_LABEL = "InitialGrid";
    private static final String START_BUTTON = "StartCommand";
    private static final String ROWS = "numRows";
    private static final String COLUMNS = "numColumns";
    private Button startButton;
    private int[][] initialStates;
    GridPane myGrid = new GridPane();

    public UserDefinedGameScreenThree(Player thisPlayer) {
        super(thisPlayer);
        myKeysResources = ResourceBundle.getBundle(KEYS_RESOURCES_PATH + MY_KEYS);
        myButtonEvent = e -> {
            try{
                makeGrid();
            }
            catch (NewUserDefinedGameException p){
                myErrorMessage.textProperty().setValue(p.getMessage());
            }
        };
        myButtonText = BUTTON_TEXT;
        gameLabel = GAME_LABEL;
    }

    @Override
    protected boolean additionalValidation() {
        return true;
    }

    @Override
    public Scene setUpScene(){
        Parent scene = super.setUpScene().getRoot();
        startButton = makeButton(START_BUTTON, e ->{
            try {
                //TODO: ugly
                getStates();
                myPlayer.setGameType("UserMadeGame");
                myPlayer.getUserMAdeStartButton().handle(e);
            } catch (NewUserDefinedGameException p){ myErrorMessage.textProperty().setValue(p.getMessage()); }
        });
        List<Node> kids = new ArrayList<>(scene.getChildrenUnmodifiable());
        myGrid.visibleProperty().set(false);
        kids.add(myGrid);
        startButton.visibleProperty().set(false);
        kids.add(startButton);
        VBox myBox = new VBox();
        myBox.getChildren().addAll(kids);
        myBox.setSpacing(MAIN_SPACING);
        myBox.setAlignment(Pos.CENTER);
        return finishStyling(myBox);
    }

    public int[][] getUserSelectedInitialStates(){
        return initialStates;
    }

    private void makeGrid(){
        try{
            buildMap();
        } catch (NewUserDefinedGameException p){
            myErrorMessage.textProperty().setValue(p.getMessage());
        }
        myGrid.getChildren().clear();
        myGrid.visibleProperty().set(true);
        myGrid.setGridLinesVisible(true);
        startButton.visibleProperty().set(true);
        //TODO: hard coded keys :(
        int rows = Integer.parseInt(selectedAttributes.get(ROWS));
        int cols = Integer.parseInt(selectedAttributes.get(COLUMNS));
        initialStates = new int[rows][cols];
        for(int row = 0; row < rows; row++){
            for(int col = 0; col < cols; col++){
                TextField state = new TextField();
                GridPane.setRowIndex(state, row);
                GridPane.setColumnIndex(state, col);
                myGrid.getChildren().add(state);
            }
        }
    }


    private void getStates(){
        for(Node node: myGrid.getChildren()){
            if(GridPane.getRowIndex(node) != null){
                int row = GridPane.getRowIndex(node);
                int col = GridPane.getColumnIndex(node);
                if (isInteger(((TextField) node).getText()))
                    initialStates[row][col] = Integer.parseInt(((TextField) node).getText());
                else throw new NewUserDefinedGameException();
            }
        }
    }
}
