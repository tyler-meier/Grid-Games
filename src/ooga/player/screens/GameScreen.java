package ooga.player.screens;

import java.util.*;
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
  private int myHighScore;
  private int myScore;
  private int myLives;
  private GridView myGrid;

  public GameScreen(String gameType, Player player){
    myButtonResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "ButtonCreation");
    myStringResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "BasicStrings");
    myPlayer = player;
    myGrid = new GridView(gameType, 400);

    //TODO: retrieve stats from user profile
    myHighScore = 12;
    myScore = 0;
    myLives = 5;
  }

  /**
   * returns scene for game
   * @param height
   * @param width
   * @return
   */
  public Scene makeScene(int height, int width) {
    myHeight = height;
    myWidth = width;
    BorderPane root = new BorderPane();
    root.setPadding(new Insets(10, 20, 10, 20));

    Node toolBar = makeToolBar();
    root.setTop(toolBar);

    VBox verticalPanel = new VBox();
    Node buttonPanel = makeButtonPanel();
    Node statsPanel = makeStatsPanel();
    verticalPanel.setSpacing(30);
    verticalPanel.setAlignment(Pos.CENTER);
    verticalPanel.getChildren().addAll(buttonPanel, statsPanel);
    root.setRight(verticalPanel);

    //TODO: setGrid
//    GridPane gameGrid = myGrid.makeGrid(10, 10);
//    gameGrid.setAlignment(Pos.CENTER);
//    root.setCenter(gameGrid);

    Scene scene = new Scene(root, height, width);
    scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());

    return scene;
  }

  //returns a button with correct text, associated event handler
  private Button makeButton(String text, EventHandler<ActionEvent> handler) {
    Button newButton = new Button();
    newButton.setText(myButtonResources.getString(text));
    newButton.setOnAction(handler);
    return newButton;
  }

  //make panel of buttons for screen
  private Node makeButtonPanel() {
    Button loginButton = makeButton("LogoutCommand", e-> myPlayer.setUpLoginScreen());
    Button resetButton = makeButton("ResetCommand", e-> makeScene(myHeight, myWidth));

    VBox buttons = new VBox();
    buttons.getChildren().addAll(loginButton, resetButton);
    buttons.setSpacing(10);
    buttons.setAlignment(Pos.CENTER);

    return buttons;
  }

  private Node makeToolBar() {
    HBox toolBar = new HBox();
    Button homeButton = makeButton("HomeCommand", e-> myPlayer.setUpStartScreen(""));

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
    Label highScore = new Label(Integer.toString(myHighScore));
    Label score = new Label(Integer.toString(myScore));
    Label lives = new Label(Integer.toString(myLives));

    stats.getChildren().addAll(highScore, score, lives);
    stats.setSpacing(10);
    stats.setAlignment(Pos.CENTER);

    return stats;
  }

}
