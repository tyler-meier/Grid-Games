package ooga.player.screens;

import java.util.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ooga.player.Player;

public class GameScreen {
  private static final String RESOURCES = "ooga/player/Resources/";
  private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
  private static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES;
  private static final String STYLESHEET = "default.css";

  private ResourceBundle myResources;

  private int myHeight;
  private int myWidth;

  public GameScreen(String gameType){
    //should be defined by what type of game
    myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + gameType);
  }

  //needs dimensions from dataobject
  public Scene makeScene(int height, int width) {
    myHeight = height;
    myWidth = width;

    BorderPane root = new BorderPane();

    HBox toolBar = new HBox();
    //Button homeButton = makeButton("HomeCommand" e-> );
    //write code for timer
    //timeKeeper = ;
    //toolBar.getChildren().addAll(homeButton, timeKeeper);
    root.setTop(toolBar);

    //VBox buttonPanel = makeButtonPanel();
    //root.setRight(buttonPanel);

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
    //lambda notation to trigger correct event
    //TODO check which classes need to be instantiated to pass to event handler
    //Button loginButton = makeButton("LoginCommand", e-> );
    Button resetButton = makeButton("ResetCommand", e-> makeScene(myHeight, myWidth));
    //Button newButton = makeButton("NewCommand", e-> );

    buttons.setSpacing(20);
    //buttons.getChildren().addAll(loginButton, resetButton, newButton);
    buttons.setAlignment(Pos.CENTER);

    return buttons;
  }



}
