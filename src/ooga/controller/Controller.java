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
        Player player = new Player();
        player.startView(stage);
        player.setLoginAction((username, password) -> data.login(username, password)); //takes a UserLogin functional interface
        player.setNewLoginAction(((username, password) -> data.saveNewPlayerProfile(username,password)));
        player.setStartGameButton(e -> buildNewEngine(player));
        player.setErrorMessage(data.getErrorMessage());
    }

    private void buildNewEngine(Player player){
        String type = player.getGameType();
        String username = player.getUsername();
        Map<String, String> myEngineAttributes = data.getEngineAttributes(type); //rename DataObject to something more clear
        Map<String, String> myGameAttributes = data.getGameAttributes(username, type);
        int[][] initialStates = data.getGrid(username, type);
        Engine engine = new Engine(myEngineAttributes, data.getErrorMessage());
        engine.setupGame(initialStates, myGameAttributes);
        //player.setGameStats(engine.getGameStats());
        player.setGrid(engine.getGrid()); // need to change param type of set grid
        //player.setInProgressProperty(engine.getInProgressProperty());
//        player.setSaveGameButton(e -> data.saveGame(username, type, engine.getGameAttributes(), engine.getGridConfiguration()));
//        player.setResetButton(e -> {
//              Map<String, String> newGameAttributes = data.getGameAttributes("guest", type);
//              int[][] newInitialStates = data.getGrid("guest", type);
//              engine.setupGame(newInitialStates, newGameAttributes);
//              player.setGameStats(engine.getGameStats());
//        });
    }
}


