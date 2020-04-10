package ooga.player.screens;

import java.io.FileNotFoundException;
import java.util.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ooga.engine.grid.Grid;
import ooga.player.Player;
import ooga.player.TimeKeeper;
import ooga.player.GridView;

public class GameScreen {
  private static final String RESOURCES = "ooga/player/Resources/";
  private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
  private static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES;
  private static final String STYLESHEET = "default.css";
  private ResourceBundle myButtonResources, myStringResources;
  private Player myPlayer;
  private int myHeight;
  private int myWidth;
  private GridView myGrid;
  private BorderPane myRoot;
  private String myGameType;
  IntegerProperty myHighScore = new SimpleIntegerProperty();
  IntegerProperty myScore = new SimpleIntegerProperty();
  IntegerProperty myLives = new SimpleIntegerProperty();
  IntegerProperty myLevel = new SimpleIntegerProperty();
  String myTime = "00:00:000";

  public GameScreen(String gameType, Player player){
    myButtonResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "ButtonCreation");
    myStringResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "BasicStrings");
    myPlayer = player;
    myGrid = new GridView(gameType, 400);
    myRoot = new BorderPane();
  }

  /**
   * returns scene for game
   * @param height
   * @param width
   * @return
   */
  public Scene makeScene(String gameType, String currUsername, int height, int width) {
    myHeight = height;
    myWidth = width;
    myRoot.setPadding(new Insets(10, 20, 10, 20));
    myGameType = gameType;

    Node toolBar = makeToolBar(currUsername);
    myRoot.setTop(toolBar);

    VBox verticalPanel = new VBox();
    Node buttonPanel = makeButtonPanel(currUsername);
    Node statsPanel = makeStatsPanel();
    verticalPanel.setSpacing(30);
    verticalPanel.setAlignment(Pos.CENTER);
    verticalPanel.getChildren().addAll(buttonPanel, statsPanel);
    myRoot.setRight(verticalPanel);

    Scene scene = new Scene(myRoot, height, width);
    scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());

    return scene;
  }

  public void setGrid(Grid backendGrid){
    GridPane gameGrid = myGrid.setGrid(backendGrid);
    gameGrid.setAlignment(Pos.CENTER);
    myRoot.setCenter(gameGrid);
  }

  //returns a button with correct text, associated event handler
  private Button makeButton(String text, EventHandler<ActionEvent> handler) {
    Button newButton = new Button();
    newButton.setText(myButtonResources.getString(text));
    newButton.setOnAction(handler);
    return newButton;
  }

  //make panel of buttons for screen
  private Node makeButtonPanel(String username) {
    Button loginButton = makeButton("LogoutCommand", e-> myPlayer.setUpLoginScreen());
    Button resetButton = makeButton("ResetCommand", e-> makeScene(myGameType, username, myHeight, myWidth));

    VBox buttons = new VBox();
    buttons.getChildren().addAll(loginButton, resetButton);
    buttons.setSpacing(10);
    buttons.setAlignment(Pos.CENTER);

    return buttons;
  }

  private Node makeToolBar(String username) {
    HBox toolBar = new HBox();
    Button homeButton = makeButton("HomeCommand", e-> myPlayer.setUpStartScreen(username));

    TimeKeeper timer = new TimeKeeper();
    timer.addTimeline();
    String time = timer.getText();
    Label stopWatch = new Label(time);

    toolBar.getChildren().addAll(homeButton, stopWatch);
    toolBar.setSpacing(45);

    return toolBar;
  }

  private Node makeStatsPanel() {
    VBox stats = new VBox();
    Label highScore = new Label();
    highScore.textProperty().bind(myHighScore.asString());
    Label score = new Label();
    score.textProperty().bind(myScore.asString());
    Label lives = new Label();
    lives.textProperty().bind(myLives.asString());
    Label level = new Label();
    level.textProperty().bind(myLevel.asString());

    stats.getChildren().addAll(highScore, score, lives, level);
    stats.setSpacing(10);
    stats.setAlignment(Pos.CENTER);

    return stats;
  }

  public void setStats(Map<String, IntegerProperty> gameStats){
    //TODO: how do you get high score of profile?
    System.out.println(gameStats.get("Score").getValue());
    myHighScore.bind(gameStats.get("Score"));
    myScore.bind(gameStats.get("Score"));
    //TODO: get number of lives
    myLives.bind(gameStats.get("Level"));
    myLevel.bind(gameStats.get("Level"));
    //TODO: implement timekeeper
//    gameStats.get("Time").bind(myTime);
  }

}
