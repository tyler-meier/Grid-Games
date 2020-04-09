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
        System.out.println("these are the keys in the map for engine: " + myEngineAttributes.keySet());
        System.out.println("this is the value of validator in the map: " + myEngineAttributes.get("Validator"));
        System.out.println("this is the value of match finder in the map: " + myEngineAttributes.get("MatchFinder"));
        Engine engine = new Engine(myEngineAttributes, data.getErrorMessage());
        System.out.println("THESE ARE THE VALUES IN THE GAME MAP " + myGameAttributes.keySet());
        System.out.println("this is the value of SCORE in the map: " + myGameAttributes.get("Score"));
        System.out.println("this is the value of LEVEL" +
                " in the map: " + myGameAttributes.get("Level"));
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


