package ooga.player.screens;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import java.util.*;
import ooga.player.Player;
import ooga.player.screens.SuperScreen;

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
   * @param engine the event to create the engine
   */
  public StartScreen(EventHandler engine, Player thisPlayer){
    super(engine, thisPlayer);
  }

  /**
   * Sets up the start/home scene, with the labels, combo box, and buttons
   * @return the final completed scene to be shown
   */
  public Scene setUpScene(){
    Label welcomeLabel = makeWelcomeLabel();
    Node gameChoice = makeGameChoice();
    Node buttonPanel = setUpButtons();
    return styleScene(welcomeLabel, gameChoice, buttonPanel);
  }

  private Label makeWelcomeLabel(){
    return new Label(myStringResources.getString("Welcome") + " " + myPlayer.getUsername());
  }

  private Node makeGameChoice(){
    Label gameChoice = new Label(myStringResources.getString("GameChoice"));
    for(String key : Collections.list(myGameNameResources.getKeys())){
        nameOfGameMapping.put(myGameNameResources.getString(key), key);
        games.getItems().add(myGameNameResources.getString(key));
    }
    return styleContents(gameChoice, games);
  }

  private Node setUpButtons(){
    Button startButton = makeButton("StartCommand", e -> {
      try {
        myPlayer.setGameType(nameOfGameMapping.get(games.getValue()));
        myEventEngine.handle(e);
      } catch (NullPointerException p){
        myErrorMessage.textProperty().setValue(myStringResources.getString("BlankChoice"));
      }
    });
    Button logoutButton = makeButton("LogoutCommand", e -> myPlayer.setUpLoginScreen());
    return styleContents(startButton, logoutButton);
  }
}
