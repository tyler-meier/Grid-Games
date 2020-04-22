package ooga.player.screens;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ooga.player.Player;
import ooga.player.exceptions.NewUserDefinedGameException;
import java.util.ResourceBundle;


public class UserDefinedGameScreenThree extends UserDefinedGameScreen {
    private static final String MY_KEYS = "NewGridKeys";
    private static final String MAKE_GRID_BUTTON = "MakeGrid";
    private static final String GAME_LABEL = "InitialGrid";
    private static final String START_BUTTON = "StartCommand";
    private static final String ROWS = "numRows";
    private static final String COLUMNS = "numColumns";
    private int[][] initialStates;
    GridPane myGrid = new GridPane();
    private int maxState;

    public UserDefinedGameScreenThree(Player thisPlayer) {
        super(thisPlayer);
        myKeysResources = ResourceBundle.getBundle(KEYS_RESOURCES_PATH + MY_KEYS);
        myButtonEvent = e -> {
            try {
                //TODO: ugly
                buildMap();
                getStates();
                myPlayer.setGameType("UserMadeGame");
                myPlayer.getUserMadeStartButton().handle(e);
            } catch (NewUserDefinedGameException p){ myErrorMessage.textProperty().setValue(p.getMessage()); }
        };
        myButtonText = START_BUTTON;
        gameLabel = GAME_LABEL;
    }


    public void setMaxState(int max){ maxState = max;}

    @Override
    protected boolean additionalValidation() {
        int rows = Integer.parseInt(selectedAttributes.get(ROWS));
        int cols = Integer.parseInt(selectedAttributes.get(COLUMNS));
        return rows>0 && cols>0;
    }

    @Override
    protected void screenSpecificSetup() {
        Button makeGridButton = makeButton(MAKE_GRID_BUTTON, e ->{
            try{
                makeGrid();
                if (!inputField.getChildren().contains(myGrid)) inputField.getChildren().addAll(myGrid);
            }
            catch (NewUserDefinedGameException p){
                myErrorMessage.textProperty().setValue(p.getMessage());
            }
        });
        inputField.getChildren().add(makeGridButton);
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
        myGrid.setGridLinesVisible(true);
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
        for(Node node:myGrid.getChildren()){
            if(GridPane.getRowIndex(node) != null){
                int row = GridPane.getRowIndex(node);
                int col = GridPane.getColumnIndex(node);
                int val;
                if (isInteger(((TextField) node).getText()) && (val= Integer.parseInt(((TextField) node).getText())) <= maxState)
                    initialStates[row][col] = val;
                else throw new NewUserDefinedGameException();
            }
        }
    }
}
