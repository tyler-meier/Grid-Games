package ooga.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.application.Application;
import javafx.stage.Stage;
import ooga.data.Data;
import ooga.player.Player;
import java.util.Map;
import ooga.engine.Engine;
import ooga.player.exceptions.ImageNotFoundException;


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
        player.setStartGameButton(e -> {
            try{ buildNewEngine(player, data);
            } catch (Exception p){ data.getErrorMessage().set(p.toString());
            }
        });
        player.setUserMadeStartButton(e ->
        {
          data.saveCreatedGame(player.getGameType(), player.getUserMadeEngineAttributesMap(), player.getUserMadeGameAttributesMap(), player.getUserDefinedInitialStates(), null);
            try{ buildNewEngine(player, data);
            } catch (Exception p){ data.getErrorMessage().set(p.toString());
            }
        });
        player.setErrorMessage(data.getErrorMessage());
    }

    private void buildNewEngine(Player player, Data data) throws ImageNotFoundException {
        String type = player.getGameType();
        String username = player.getUsername();
        Map<String, String> myEngineAttributes = data.getEngineAttributes(type);
        Engine engine = new Engine(myEngineAttributes);
        Map<String, String> myGameAttributes = data.getGameLevelAttributes(username, type, engine.getLevel());
        int[][] initialStates = data.getGrid();
        boolean[][] openCellConfiguration = data.getOpenCells();
        engine.setupGame(initialStates, myGameAttributes, openCellConfiguration);
        player.setSaveButton(e -> data.saveGame(engine.getGameAttributes(), engine.getGridConfiguration(), engine.getOpenCellConfiguration()));
        player.setResetLevelButton(goToNewLevel("Guest", data, player, engine, 0));
        Map<String, Integer> highScores = data.getHighScores(type);
        player.setHighScoreMap(highScores);
        player.setResetGameButton(goToNewLevel("Guest", data, player, engine, -1));
        player.setNextLevel(goToNewLevel(username, data, player, engine, 1));
        player.setUpGameScreen(engine.getGrid(), data.getErrorMessage());
    }

    private EventHandler<ActionEvent> goToNewLevel(String username, Data data, Player player, Engine engine, Integer levelAdder) {
        return event -> {
            Map<String, String> newGameAttributes;
            if (levelAdder >= 0) newGameAttributes= data.getGameLevelAttributes(username, player.getGameType(), engine.getLevel()+levelAdder);
            else  newGameAttributes = data.getGameLevelAttributes(username, player.getGameType(), -1);
            int[][] newInitialStates = data.getGrid();
            boolean[][] newOpenCells = data.getOpenCells();
            engine.setupGame(newInitialStates, newGameAttributes, newOpenCells);
            try{ player.setUpGameScreen(engine.getGrid(), data.getErrorMessage());
            } catch (Exception p){ data.getErrorMessage().set(p.toString());
            }
        };
    }

}


