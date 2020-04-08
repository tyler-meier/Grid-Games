package ooga.player.screens;

import java.util.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
  private static final String RESOURCES = "ooga/player/Resources/Properties";
  private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
  private static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES;
  private static final String STYLESHEET = "default.css";
  private ResourceBundle myResources;
  private Player myPlayer;
  private int myHeight;
  private int myWidth;
  private int myHighScore;
  private int myScore;
  private int myLives;
  private GridView myGrid;

  public GameScreen(String gameType, Player player){
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + gameType);
    myPlayer = player;
    myGrid = new GridView(gameType);

//    //TODO: retrieve stats from user profile
//    myHighScore = 12;
//    myScore = 0;
//    myLives = 5;
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

    HBox toolBar = new HBox();
    Button homeButton = makeButton(myResources.getString("HomeCommand"), e-> myPlayer.setUpStartScreen(""));

    TimeKeeper timer = new TimeKeeper();
    Text stopwatch = timer.getText();

    toolBar.getChildren().addAll(homeButton, stopwatch);
    root.setTop(toolBar);

    Node buttonPanel = makeButtonPanel();
    root.setRight(buttonPanel);

    GridPane gameGrid = myGrid.makeGrid(myHeight/2, myWidth/2);
    root.setCenter(gameGrid);

    Scene scene = new Scene(root, height, width);
    scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
    return scene;
  }

  //returns a button with correct text, associated event handler
  private Button makeButton(String text, EventHandler<ActionEvent> handler) {
    Button newButton = new Button();
    newButton.setText(myResources.getString(text));
    newButton.setOnAction(handler);
    return newButton;
  }

  //make panel of buttons for screen
  private Node makeButtonPanel() {
    Button loginButton = makeButton(myResources.getString("LoginCommand"), e-> myPlayer.setUpLoginScreen());
    Button resetButton = makeButton(myResources.getString("ResetCommand"), e-> makeScene(myHeight, myWidth));

    VBox buttons = new VBox();
    buttons.getChildren().addAll(loginButton, resetButton);
    buttons.setSpacing(10);
    buttons.setAlignment(Pos.CENTER);

    return buttons;
  }

}
