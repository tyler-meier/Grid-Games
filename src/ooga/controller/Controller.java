package ooga.controller;

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
        Map<String, String> myGameAttributes = data.getGameAttributes(username, type);
        //Map<String, String> myGameAttributes = data.getGameLevelAttributes(username, type, -1);
        int[][] initialStates = data.getGrid();
        boolean[][] openCellConfiguration = data.getOpenCells();
        engine.setupGame(initialStates, myGameAttributes, openCellConfiguration);
        player.setSaveButton(e -> data.saveGame(engine.getGameAttributes(), engine.getGridConfiguration(), engine.getOpenCellConfiguration()));
        player.setResetGameButton(e -> {
            Map<String, String> newGameAttributes = data.getGameAttributes("Guest", type);
            int[][] newInitialStates = data.getGrid();
            boolean[][] newOpenCells = data.getOpenCells();
            engine.setupGame(newInitialStates, newGameAttributes, newOpenCells);
            player.setUpGameScreen(engine.getGrid(), data.getErrorMessage());
        });
        /*
        player.setNextLevel( e-> {
            Map<String, String> newGameAttributes = data.getGameLevelAttributes(username, type, engine.getNextLevel());
            int[][] newInitialStates = data.getGrid();
            boolean[][] newOpenCells = data.getOpenCells();
            engine.setupGame(newInitialStates, newGameAttributes, newOpenCells);
            player.setUpGameScreen(engine.getGrid(), data.getErrorMessage());
        });
         */
        player.setUpGameScreen(engine.getGrid(), data.getErrorMessage());
    }
}


