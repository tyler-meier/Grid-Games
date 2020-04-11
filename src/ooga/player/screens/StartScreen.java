package ooga.player.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ooga.player.Player;

public class StartScreen extends SuperScreen{

  private Player myPlayer;
  private EventHandler myEngine;
  private ComboBox games;
  private List<Node> myNodes;
  private ResourceBundle myStringResources;

  public StartScreen(EventHandler engine, Player thisPlayer){
    super(engine, thisPlayer);
    myPlayer = thisPlayer;
    myEngine = engine;
    myNodes = new ArrayList<>();
    myStringResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "BasicStrings");
  }

  public Scene setUpScene(String username){
    Label welcomeLabel = makeWelcomeLabel(username);
    Node gameChoice = makeGameChoice();
    Node buttonPanel = setUpButtons();
    myNodes.clear();
    myNodes.add(welcomeLabel);
    myNodes.add(gameChoice);
    myNodes.add(buttonPanel);
    Scene scene = styleScene(myNodes);
    return scene;
  }

  private Label makeWelcomeLabel(String username){
    Label welcome = new Label(myStringResources.getString("Welcome") + " " + username);
    return welcome;
  }

  private Node makeGameChoice(){
    VBox gameChoicePanel = new VBox();

    Label gameChoice = new Label(myStringResources.getString("GameChoice"));
    games = new ComboBox();
    games.getItems().addAll("CandyCrush", "BejeweledAction", "BejeweledEndless", "BejeweledPuzzle", "Minesweeper", "Memory");
    //TODO fix hardcoded strings and have them be sent to gamescreen

    gameChoicePanel.getChildren().addAll(gameChoice, games);
    gameChoicePanel.setSpacing(10);
    gameChoicePanel.setAlignment(Pos.CENTER);
    return gameChoicePanel;
  }

  private Node setUpButtons(){
    VBox buttonVBox = new VBox();

    Button startButton = makeButton("StartCommand", e -> {
      myPlayer.setGameType((String) games.getValue());  //TODO check for the empty chosen thing
      myEngine.handle(e);
    });
    Button logoutButton = makeButton("LogoutCommand", e -> myPlayer.setUpLoginScreen());

    buttonVBox.getChildren().addAll(startButton, logoutButton);
    buttonVBox.setSpacing(10);
    buttonVBox.setAlignment(Pos.CENTER);
    return buttonVBox;
  }
}
