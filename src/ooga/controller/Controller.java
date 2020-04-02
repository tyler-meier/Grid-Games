package ooga.controller;

import javafx.application.Application;
import javafx.stage.Stage;
import ooga.data.Data;
import ooga.data.DataObject;
import ooga.engine.Engine;
import ooga.player.Player;


public class Controller extends Application {
    private Data data = new Data();

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Allows us to set up the initial stage and animation.
     *
     * @param primaryStage stage for initial game
     */
    @Override
    public void start(Stage primaryStage) {
        newWindow(primaryStage);
    }

    private void newWindow(Stage stage){
        Player player = new Player(stage);
        player.setLoginButton((UserLogin) (username, password) -> data.validUser(username, password)); //takes a UserLogin functional interface
        player.setCreateUserButton((UserLogin) (username, password) -> data.createUser(username, password)); //takes a UserLogin functional interface
        player.setGameTypeButton((UserLogin) (username, gameType) -> data.hasSavedGame(username, gameType));
        player.setStartNewGameButton(e -> buildNewEngine(player, false));
        player.setStartSavedGameButton(e -> buildNewEngine(player, true));
        player.setSavePreferencesButton(e -> data.savePreferences(player.getPreferences())); //not sure what type preferences comes in here -- tbd by front end
    }

    private void buildNewEngine(Player player, boolean savedGame){
        String type = player.getGameType();
        DataObject myData = data.getEngineData(type); //rename DataObject to something more clear
        if (savedGame) myData.getSavedGridFrom(player.getUsername(), type); //changes initial config grid stored in myData from default to saved game state
        Engine engine = new Engine(myData);
        player.setGrid(engine.getGrid());
        player.setNewMoveButton(e -> engine.newMove());
        player.setSaveGameButton(e -> data.saveGame(player.getUsername(), engine.getGameState())); //not sure what getGameState's type is here: should have grid but also like lives left and score
    }

    private void getAndLoadProfile(Player player, String username, String password)
    {
        DataObject profile = data.getPlayerProfile(username, password);
        player.loadProfile(profile);
    }

}


