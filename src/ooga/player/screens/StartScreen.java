package ooga.player.screens;

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

  private ComboBox games;

  public StartScreen(EventHandler engine, Player thisPlayer){
    super(engine, thisPlayer);
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
    //TODO fix hardcoded strings and have them be sent to gamescreen, also do styling for this

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
