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
        DataObject myData = data.getEngineAttributes(type); //rename DataObject to something more clear
        if (savedGame) myData.getSavedGridFrom(player.getUsername(), type); //changes initial config grid stored in myData from default to saved game state
        Engine engine = new Engine(myData);
        player.setGrid(engine.getGrid());
<<<<<<< HEAD
        player.setNewMoveButton(e -> engine.newMove());
        player.setSaveGameButton(e -> data.saveConfigurationFile(player.getUsername(), engine.getGameState())); //not sure what getGameState's type is here: should have grid but also like lives left and score
=======
        player.setNewMoveButton(e -> engine.updateBoard());
        player.setSaveGameButton(e -> data.saveGame(player.getUsername(), engine.getGameState())); //not sure what getGameState's type is here: should have grid but also like lives left and score
>>>>>>> 8c7959b3c9993f27f77b88d33cda9b76df1ca017
    }

    private void getAndLoadProfile(Player player)
    {
        DataObject profile = data.getPlayerProfile(player.getUsername(), player.getPassword());
        player.loadProfile(profile);
    }

}


