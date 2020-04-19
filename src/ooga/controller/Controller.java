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
        player.setUserMadeStartButton(e -> buildNewEngineUserMade(player, data));
        player.setErrorMessage(data.getErrorMessage());
    }

    private void buildNewEngine(Player player, Data data){
        String type = player.getGameType();
        String username = player.getUsername();
        Map<String, String> myEngineAttributes = data.getEngineAttributes(type);
        Map<String, String> myGameAttributes = data.getGameAttributes(username, type);
        int[][] initialStates = data.getGrid();
        boolean[][] openCellConfiguration = data.getOpenCells();
        Engine engine = new Engine(myEngineAttributes, data.getErrorMessage());
        engine.setupGame(initialStates, myGameAttributes, openCellConfiguration);
        player.setSaveButton(e -> data.saveGame(engine.getGameAttributes(), engine.getGridConfiguration(), engine.getOpenCellConfiguration()));
        player.setResetButton(e -> {
            Map<String, String> newGameAttributes = data.getGameAttributes("Guest", type);
            int[][] newInitialStates = data.getGrid();
            boolean[][] newOpenCells = data.getOpenCells();
            engine.setupGame(newInitialStates, newGameAttributes, newOpenCells);
            player.setUpGameScreen(engine.getGrid(), data.getErrorMessage());
        });
        player.setUpGameScreen(engine.getGrid(), data.getErrorMessage());
    }

    private void buildNewEngineUserMade(Player player, Data data){
        String type = player.getGameType();
        String username = player.getUsername();
        Map<String, String> myEngineAttributes = player.getUserMadeEngineAttributesMap();
        Map<String, String> myGameAttributes = player.getUserMadeGameAttributesMap();
        // TODO: need to make the initial states dynamic
        int[][] initialStates = {{1,6, 5, 4, 2}, {5, 1, 3, 1, 5}, {4, 4, 4, 6, 1}, {2, 3, 4, 3, 2}};
        // TODO: need to make open cell configuration dynamic
        boolean[][] openCellConfiguration = { {true ,true, true, true, true}, {true, true, true, true, true}, {true, true, true, true, true}, {true, true, true, true, true}};
        Engine engine = new Engine(myEngineAttributes, data.getErrorMessage());
        engine.setupGame(initialStates, myGameAttributes, openCellConfiguration);
        /*
        player.setSaveButton(e -> data.saveGame(engine.getGameAttributes(), engine.getGridConfiguration(), engine.getOpenCellConfiguration()));
        player.setResetButton(e -> {
            Map<String, String> newGameAttributes = data.getGameAttributes("Guest", type);
            int[][] newInitialStates = data.getGrid();
            boolean[][] newOpenCells = data.getOpenCells();
            engine.setupGame(newInitialStates, newGameAttributes, newOpenCells);
            player.setUpGameScreen(engine.getGrid(), data.getErrorMessage());
        });
         */
        player.setUpGameScreen(engine.getGrid(), data.getErrorMessage());
    }
}


