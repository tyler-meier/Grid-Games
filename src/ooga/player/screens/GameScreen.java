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
import ooga.player.Player;
import ooga.player.TimeKeeper;

public class GameScreen {
  private static final String RESOURCES = "ooga/player/Resources/";
  private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
  private static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES;
  private static final String STYLESHEET = "default.css";
  private ResourceBundle myResources;
  private Player myPlayer;
  private int myHeight;
  private int myWidth;
  private String initialTime = "0";

  public GameScreen(String gameType, Player player){
    //should be defined by what type of game
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + gameType);
    myPlayer = player;
  }

  //needs dimensions from dataobject
  public Scene makeScene(int height, int width) {
    myHeight = height;
    myWidth = width;
    BorderPane root = new BorderPane();

    HBox toolBar = new HBox();
    Button homeButton = makeButton("HomeCommand", e-> myPlayer.setUpStartScreen(""));

    //TODO: write code for timer
    TimeKeeper timer = new TimeKeeper();
    Text stopwatch = timer.getText();

    toolBar.getChildren().addAll(homeButton, stopwatch);
    root.setTop(toolBar);

    Node buttonPanel = makeButtonPanel();
    root.setRight(buttonPanel);

    Scene scene = new Scene(root, height, width);
    scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
    return scene;
  }

  //make buttons
  private Button makeButton(String text, EventHandler<ActionEvent> handler) {
    Button newButton = new Button();
    newButton.setText(myResources.getString(text));
    newButton.setOnAction(handler);
    return newButton;
  }

  //make panel of buttons
  private Node makeButtonPanel() {
    VBox buttons = new VBox();
    Button loginButton = makeButton("LoginCommand", e-> myPlayer.setUpLoginScreen());
    Button resetButton = makeButton("ResetCommand", e-> makeScene(myHeight, myWidth));

    buttons.setSpacing(20);
    buttons.getChildren().addAll(loginButton, resetButton);
    buttons.setAlignment(Pos.CENTER);

    return buttons;
  }

}
