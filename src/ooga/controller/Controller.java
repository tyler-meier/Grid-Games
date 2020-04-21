package ooga.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import ooga.data.Data;
import ooga.engine.Engine;
import ooga.player.Player;
import java.util.Map;


public class Controller extends Application {
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
        Data data = new Data();
        Player player = new Player(stage);
        player.setNewWindow(e->newWindow(new Stage()));
        player.setUserLogin(data::login);
        player.setNewLogin(data::saveNewPlayerProfile);
        player.setStartGameButton(e -> buildNewEngine(player, data));
        player.setErrorMessage(data.getErrorMessage());
    }

    private void buildNewEngine(Player player, Data data){
        String type = player.getGameType();
        String username = player.getUsername();
        Map<String, String> myEngineAttributes = data.getEngineAttributes(type);
        Engine engine = new Engine(myEngineAttributes, data.getErrorMessage());
        Map<String, String> myGameAttributes = data.getGameLevelAttributes(username, type, engine.getLevel());
        int[][] initialStates = data.getGrid();
        boolean[][] openCellConfiguration = data.getOpenCells();
        engine.setupGame(initialStates, myGameAttributes, openCellConfiguration);
        player.setSaveButton(e -> data.saveGame(engine.getGameAttributes(), engine.getGridConfiguration(), engine.getOpenCellConfiguration()));
        player.setResetLevelButton(goToNewLevel("Guest", data, player, engine, 0));
        //List<String> highScores = data.getHighScores(String gameType);
        //TODO: fix reset game button 
        player.setResetGameButton(goToNewLevel(username, data, player, engine, -1));
        player.setNextLevel(goToNewLevel(username, data, player, engine, 1));
        player.setUpGameScreen(engine.getGrid(), data.getErrorMessage());
    }

    private EventHandler<ActionEvent> goToNewLevel(String username, Data data, Player player, Engine engine, Integer levelAdder)
    {
        EventHandler<ActionEvent> e = event -> {
          Map<String, String> newGameAttributes = data.getGameLevelAttributes(username, player.getGameType(), -1);
          if (levelAdder >= 0){
            newGameAttributes = data.getGameLevelAttributes(username, player.getGameType(), engine.getLevel()+levelAdder);
          }
          int[][] newInitialStates = data.getGrid();
          boolean[][] newOpenCells = data.getOpenCells();
          engine.setupGame(newInitialStates, newGameAttributes, newOpenCells);
          player.setUpGameScreen(engine.getGrid(), data.getErrorMessage());
        };
        return e;
    }

}


