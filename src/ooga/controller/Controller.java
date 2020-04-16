package ooga.controller;

import javafx.application.Application;
import javafx.stage.Stage;
import ooga.data.Data;
import ooga.data.DataObject;
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
        Player player = new Player();
        player.startView(stage);
        player.setNewWindow(e->newWindow(new Stage()));
        player.setLoginAction(data::login);
        player.setNewLoginAction(data::saveNewPlayerProfile);
        player.setStartGameButton(e -> buildNewEngine(player, data));
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
        System.out.println("GAME HAS BEEN SET UP CORRECTLY");
        player.setSaveButton(e -> data.saveGame(player.getUsername(), engine.getGameAttributes(), engine.getGridConfiguration(), engine.getOpenCellConfiguration()));
        player.setUpGameScreen(engine.getGrid(), data.getErrorMessage());
        player.setResetButton(e -> {
              Map<String, String> newGameAttributes = data.getGameAttributes("Guest", type);
              //TODO: is this grid from the last identified path or the one set above?
              int[][] newInitialStates = data.getGrid();
                boolean[][] newOpenCells = data.getOpenCells();
              engine.setupGame(newInitialStates, newGameAttributes, newOpenCells);
              player.setUpGameScreen(engine.getGrid(), data.getErrorMessage());
        });
    }
}


