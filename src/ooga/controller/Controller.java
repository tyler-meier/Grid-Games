package ooga.controller;

import javafx.application.Application;
import javafx.stage.Stage;
import ooga.data.Data;
import ooga.data.DataObject;
import ooga.engine.Engine;
import ooga.player.Player;

import java.util.Map;


public class Controller extends Application {
    private Data data = new Data();

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Allows us to set up the initial stage and animation.
     *
     * @param primaryStage stage for initial game2
     */
   @Override
    public void start(Stage primaryStage) {
        newWindow(primaryStage);
    }

    private void newWindow(Stage stage){
        Player player = new Player(stage);
        player.setLoginAction((username, password) -> data.getPlayerProfile(username, password)); //takes a UserLogin functional interface
//        player.setStartGameButton(e -> buildNewEngine(player));
//        player.setErrorMessage(data.getErrorMessage());
    }

//    private void buildNewEngine(Player player){
//        String type = player.getGameType();
//        String username = player.getUsername();
//        Map myData = data.getEngineAttributes(player, type); //rename DataObject to something more clear
//        int[][] initialStates = data.getIntialStates(username);
//        Engine engine = new Engine(myData, initialStates);
//        player.setGrid(engine.getGrid()); // need to change param type of set grid
//        player.setSaveGameButton(e -> data.saveGame(username, engine.getGameState())); //not sure what getGameState's type is here: should have grid but also like lives left and score
//        player.setResetButton(e -> engine.resetGrid(data.getInitialStates("guest")));
//    }
}


