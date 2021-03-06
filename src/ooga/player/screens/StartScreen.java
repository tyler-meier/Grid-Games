package ooga.player.screens;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ooga.player.Player;

/**
 * Start screen class which serves as the home screen of a player's profile and where a game is chosen
 * @author Tyler Meier
 */
public class StartScreen extends SuperScreen {

  private ComboBox<String> games = new ComboBox<>();
  private Map<String, String> nameOfGameMapping = new HashMap<>();

  /**
   * Constructor of this class, calls super to set up instance variables
   * @param thisPlayer the current player
   */
  public StartScreen(Player thisPlayer){
    super(thisPlayer);
  }

  /**
   * Sets up the start/home scene, with the labels, combo box, and buttons
   * @return the final completed scene to be shown
   */
  public Scene setUpScene(){
    Label welcomeLabel = makeWelcomeLabel();
    VBox gameChoice = makeGameChoice();
    VBox buttonPanel = setUpButtons();
    return styleScene(welcomeLabel, gameChoice, buttonPanel);
  }

  private Label makeWelcomeLabel(){
    Label welcome = new Label(myStringResources.getString("Welcome") + myPlayer.getUsername());
    welcome.setId("welcome-label");
    return welcome;
  }

  private VBox makeGameChoice(){
    Label gameChoice = new Label(myStringResources.getString("GameChoice"));
    for(String key : Collections.list(myGameNameResources.getKeys())){
        nameOfGameMapping.put(myGameNameResources.getString(key), key);
        games.getItems().add(myGameNameResources.getString(key));
    }
    if(myPlayer.getMyUserProfile() != null && !myPlayer.getMyUserProfile().getAllSavedGamed().isEmpty()) {
      for(String userDefinedGame : myPlayer.getMyUserProfile().getAllSavedGamed().keySet()) {
        if(isNewGame(userDefinedGame)) {
          games.getItems().add(userDefinedGame);
        }
      }
    }
    return styleContents(gameChoice, games);
  }

  private VBox setUpButtons(){
    if (isGuest()){
      return styleContents(makeStartButton(), makeLogoutButton());
    }
    Button makeNewGameButton = makeButton("MakeNewGame", e-> myPlayer.setUpMakeNewGameScreenImages());
    return styleContents(makeStartButton(), makeLogoutButton(), makeNewGameButton);
  }

  private Button makeStartButton(){
    return makeButton("StartCommand", e -> {
      if(nameOfGameMapping.containsKey(games.getValue())){
        myPlayer.setGameType(nameOfGameMapping.get(games.getValue()));
      }
      else if (!games.getSelectionModel().isEmpty()){
        myPlayer.setGameType(games.getValue());
      }
      myPlayer.getStartGameButtonEvent().handle(e);
      if(games.getSelectionModel().isEmpty()) {
        myErrorMessage.setText(myStringResources.getString("BlankChoice"));
      }
    });
  }
}
