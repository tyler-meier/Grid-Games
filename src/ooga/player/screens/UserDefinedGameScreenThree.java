package ooga.player.screens;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ooga.player.Player;
import ooga.player.exceptions.NewUserDefinedGameException;

import java.awt.*;
import java.util.ResourceBundle;


public class UserDefinedGameScreenThree extends UserDefinedGameScreen {
    private static final String MY_KEYS = "NewGridKeys";
    private static final String MAKE_GRID_BUTTON = "MakeGrid";
    private static final String GAME_LABEL = "InitialGrid";
    private static final String START_BUTTON = "StartCommand";
    private static final String ROWS = "numRows";
    private static final String COLUMNS = "numColumns";
    private static final String GRID_TYPE = "gridType";
    private static final String RANDOM = "random";
    private int[][] initialStates;
    GridPane myGrid = new GridPane();

    public UserDefinedGameScreenThree(Player thisPlayer) {
        super(thisPlayer);
        myKeysResources = ResourceBundle.getBundle(KEYS_RESOURCES_PATH + MY_KEYS);
        myButtonEvent = e -> {
            try {
                //TODO: ugly
                buildMap();
                getStates();
                myPlayer.setGameType("UserMadeGame");
                myPlayer.startUserDefinedGame();
            } catch (NewUserDefinedGameException p){ myErrorMessage.textProperty().setValue(p.getMessage()); }
        };
        myButtonText = START_BUTTON;
        gameLabel = GAME_LABEL;
    }

    @Override
    protected boolean additionalValidation() {
        Point p = getGridSize();
        return p.x>0 && p.y>0;
    }

    @Override
    protected void screenSpecificSetup() {
        inputField.getChildren().clear();
        inputField.getChildren().addAll(labelMap.get(GRID_TYPE), userInputFields.get(GRID_TYPE));
        Button makeGridButton = makeButton(MAKE_GRID_BUTTON, e ->{
            try{
                makeGrid();
                if (!inputField.getChildren().contains(myGrid)) inputField.getChildren().addAll(myGrid);
            }
            catch (NewUserDefinedGameException p){
                myErrorMessage.textProperty().setValue(p.getMessage());
            }
        });
        ComboBox gridType = (ComboBox) userInputFields.get(GRID_TYPE);
        gridType.getSelectionModel().selectedItemProperty().addListener(e->{
            String choice = gridType.getSelectionModel().getSelectedItem().toString();
            if (!inputField.getChildren().contains(userInputFields.get(ROWS))) inputField.getChildren().addAll(labelMap.get(ROWS), userInputFields.get(ROWS), labelMap.get(COLUMNS), userInputFields.get(COLUMNS));
            if (choice.equals(RANDOM)){
                inputField.getChildren().removeAll(makeGridButton, myGrid);
            } else{
                inputField.getChildren().add(makeGridButton);
            }
        });
    }

    public int[][] getUserSelectedInitialStates(){
        return initialStates;
    }

    public Point getGridSize(){
        int rows = Integer.parseInt(selectedAttributes.get(ROWS));
        int cols = Integer.parseInt(selectedAttributes.get(COLUMNS));
        return new Point(rows, cols);
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
        Point p = getGridSize();
        initialStates = new int[p.x][p.y];
        for(int row = 0; row < p.x; row++){
            for(int col = 0; col < p.y; col++){
                TextField state = new TextField();
                GridPane.setRowIndex(state, row);
                GridPane.setColumnIndex(state, col);
                myGrid.getChildren().add(state);
            }
        }
    }


    private void getStates(){
        String gridType = selectedAttributes.get(GRID_TYPE);
        if (gridType.equals(RANDOM)) initialStates = null;
        else {
            for(Node node:myGrid.getChildren()){
            if(GridPane.getRowIndex(node) != null){
                int row = GridPane.getRowIndex(node);
                int col = GridPane.getColumnIndex(node);
                Integer state = getValidState((TextField) node);
                if (state!=null) initialStates[row][col] = state;
                else throw new NewUserDefinedGameException();
            }
        }}
    }

    private Integer getValidState(TextField text){
        if (isInteger(text.getText())){
            int val = Integer.parseInt(text.getText());
            if (inRange(val)) return val;
        }
        return null;
    }
}
