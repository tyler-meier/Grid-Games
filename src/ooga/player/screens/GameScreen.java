package ooga.player.screens;

import java.util.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ooga.engine.grid.Grid;
import ooga.player.Player;
import ooga.player.TimeKeeper;
import ooga.player.GridView;

public class GameScreen extends SuperScreen{
  private static final String RESOURCES = "ooga/player/Resources/";
  private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
  private static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES;
  private static final String STYLESHEET = "darkmode.css";
  private ResourceBundle myStringResources;
  private Player myPlayer;
  private int myHeight;
  private int myWidth;
  private GridView myGrid;
  private GridPane myGridPane;
  private BorderPane myRoot;
  private String myGameType;
  private Scene thisScene;
  IntegerProperty myHighScore = new SimpleIntegerProperty();
  IntegerProperty myScore = new SimpleIntegerProperty();
  IntegerProperty myLives = new SimpleIntegerProperty();
  IntegerProperty myLevel = new SimpleIntegerProperty();
  IntegerProperty myMovesLeft = new SimpleIntegerProperty();
  String myTime = "00:00:000";

  public GameScreen(String gameType, Player player){
    super(gameType, player);
    myStringResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "BasicStrings");
    myPlayer = player;
    myGrid = new GridView(gameType, 400);
  }

  /**
   * returns scene for game
   * @param height
   * @param width
   * @return
   */
  public Scene makeScene(String gameType, String currUsername, int height, int width) {
    myRoot = new BorderPane();
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

    thisScene = scene;
    return scene;
  }

  public void setGrid(Grid backendGrid){
    myGridPane = myGrid.setGrid(backendGrid);
    myGridPane.setAlignment(Pos.CENTER);
    myRoot.setCenter(myGridPane);
  }

  //make panel of buttons for screen
  private Node makeButtonPanel(String username) {
    Button loginButton = makeButton("LogoutCommand", e-> myPlayer.setUpLoginScreen());
    //Button resetButton = makeButton("ResetCommand", e-> mak); //TODO: fix reset button

    VBox buttons = new VBox();
    buttons.getChildren().addAll(loginButton);
    //, resetButton
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
    //TODO: refactor this, use keys from the gamestats to display correct stirng
    VBox stats = new VBox();
    Label highScore = new Label();
    highScore.textProperty().bind(myHighScore.asString());
    Label score = new Label();
    score.textProperty().bind(myScore.asString());
    Label lives = new Label();
    lives.textProperty().bind(myLives.asString());
    Label level = new Label();
    level.textProperty().bind(myLevel.asString());
    Label movesLeft = new Label();
    movesLeft.textProperty().bind(myLevel.asString());

    stats.getChildren().addAll(highScore, score, lives, level, movesLeft);
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
    myMovesLeft.bind(gameStats.get("MovesUsed"));
    //TODO: implement timekeeper
//    gameStats.get("Time").bind(myTime);
  }

}