package ooga.player.screens;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import java.util.*;
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
    playSound("welcome");
    Label welcomeLabel = makeWelcomeLabel();
    VBox gameChoice = makeGameChoice();
    VBox buttonPanel = setUpButtons();
    return styleScene(welcomeLabel, gameChoice, buttonPanel);
  }

  private Label makeWelcomeLabel(){
    return new Label(myStringResources.getString("Welcome") + myPlayer.getUsername());
  }

  private VBox makeGameChoice(){
    Label gameChoice = new Label(myStringResources.getString("GameChoice"));
    for(String key : Collections.list(myGameNameResources.getKeys())){
        nameOfGameMapping.put(myGameNameResources.getString(key), key);
        games.getItems().add(myGameNameResources.getString(key));
    }
    return styleContents(gameChoice, games);
  }

  private VBox setUpButtons(){
    Button startButton = makeButton("StartCommand", e -> {
      try {
        myPlayer.setGameType(nameOfGameMapping.get(games.getValue()));
        myPlayer.getStartGameButtonEvent().handle(e);
      } catch (NullPointerException p){
        //p.printStackTrace();
        myErrorMessage.textProperty().setValue(myStringResources.getString("BlankChoice"));
      }
    });
    Button makeNewGameButton = makeButton("MakeNewGame", e-> {
        myPlayer.setUpMakeNewGameScreenOne();
    });
    return styleContents(startButton, makeLogoutButton(), makeNewGameButton);
  }
}
