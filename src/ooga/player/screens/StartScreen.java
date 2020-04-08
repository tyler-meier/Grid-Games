package ooga.player.screens;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ooga.player.Player;

public class StartScreen {

  private static final int DIMENSION = 600;
  private static final String RESOURCES = "ooga/player/Resources/";
  private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
  private static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES;
  private static final String STYLESHEET = "default.css";

  private ResourceBundle myStringResources, myButtonResources;
  private Player myPlayer;

  public StartScreen(Player thisPlayer){
    myStringResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "BasicStrings");
    myButtonResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "ButtonCreation");
    myPlayer = thisPlayer;
  }

  public Scene setUpScene(String username){
    Label welcomeLabel = makeWelcomeLabel(username);
    Node gameChoice = makeGameChoice();
    Node buttonPanel = setUpButtons();

    VBox mainVBox = new VBox();
    mainVBox.getChildren().addAll(welcomeLabel, gameChoice, buttonPanel);
    mainVBox.setSpacing(50);
    mainVBox.setAlignment(Pos.CENTER);

    Scene startScreen = new Scene(mainVBox, DIMENSION, DIMENSION);
    startScreen.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
    return startScreen;
  }

  private Label makeWelcomeLabel(String username){
    Label welcome = new Label(myStringResources.getString("Welcome") + " " + username);
    return welcome;
  }

  private Node makeGameChoice(){
    VBox gameChoicePanel = new VBox();

    Label gameChoice = new Label(myStringResources.getString("GameChoice"));
    ComboBox games = new ComboBox();
    games.getItems().addAll("Candy Crush", "Bejeweled Action", "Bejeweled Endless", "Bejeweled Puzzle", "Minesweeper", "MemoryGame");
    //TODO fix hardcoded strings and have them be sent to gamescreen

    gameChoicePanel.getChildren().addAll(gameChoice, games);
    gameChoicePanel.setSpacing(10);
    gameChoicePanel.setAlignment(Pos.CENTER);
    return gameChoicePanel;
  }

  private Node setUpButtons(){
    VBox buttonVBox = new VBox();

    Button startButton = makeButton("StartCommand", e -> myPlayer.setUpGameScreen("GameType"));  //TODO need to get game type from data
    Button logoutButton = makeButton("LogoutCommand", e -> myPlayer.setUpLoginScreen());

    buttonVBox.getChildren().addAll(startButton, logoutButton);
    buttonVBox.setSpacing(10);
    buttonVBox.setAlignment(Pos.CENTER);
    return buttonVBox;
  }

  private Button makeButton(String text, EventHandler<ActionEvent> handler) {
    Button newButton = new Button();
    newButton.setText(myButtonResources.getString(text));
    newButton.setOnAction(handler);
    return newButton;
  }

}
