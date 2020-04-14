package ooga.player.screens;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import ooga.player.Player;

public class StartScreen extends SuperScreen{

  private ComboBox games;

  public StartScreen(EventHandler engine, Player thisPlayer){
    super(engine, thisPlayer);
  }

  public Scene setUpScene(){
    Label welcomeLabel = makeWelcomeLabel();
    Node gameChoice = makeGameChoice();
    Node buttonPanel = setUpButtons();
    myNodes.clear();
    myNodes.add(welcomeLabel);
    myNodes.add(gameChoice);
    myNodes.add(buttonPanel);
    Scene scene = styleScene();
    return scene;
  }

  private Label makeWelcomeLabel(){
    Label welcome = new Label(myStringResources.getString("Welcome") + " " + myPlayer.getUsername());
    return welcome;
  }

  private Node makeGameChoice(){
    Label gameChoice = new Label(myStringResources.getString("GameChoice"));
    games = new ComboBox();
    games.getItems().addAll("CandyCrush", "BejeweledAction", "BejeweledEndless", "BejeweledPuzzle", "Minesweeper", "Memory");
    //TODO fix hardcoded strings and have them be sent to gamescreen, also do styling for this, reflection?

    myContents.clear();
    myContents.add(gameChoice);
    myContents.add(games);
    Node gameChoicePanel = styleContents();
    return gameChoicePanel;
  }

  private Node setUpButtons(){
    Button startButton = makeButton("StartCommand", e -> {
      myPlayer.setGameType((String) games.getValue());  //TODO check for the empty chosen thing
      myEventEngine.handle(e);
    });
    Button logoutButton = makeButton("LogoutCommand", e -> myPlayer.setUpLoginScreen());

    myContents.clear();
    myContents.add(startButton);
    myContents.add(logoutButton);
    Node buttonVBox = styleContents();
    return buttonVBox;
  }
}
