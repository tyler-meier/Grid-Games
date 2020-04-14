package ooga.player.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ooga.controller.UserLogin;
import ooga.player.Player;

public abstract class SuperScreen {

  protected static final String RESOURCES = "ooga/player/Resources/";
  protected static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
  protected static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES;
  protected static String styleSheet = "default.css";
  private static final int DIMENSION = 600;

  protected ResourceBundle myButtonResources, myStringResources;
  protected Label myErrorMessage;
  protected Player myPlayer;
  protected EventHandler myEngine;
  protected List<Node> myNodes;
  protected List<Node> myContents;
  protected String myGameType;
  protected UserLogin myUserLogin;

  public SuperScreen(Player thisPlayer) {
    setCommonVariables(thisPlayer);
  }

  public SuperScreen(UserLogin thisUserLogin, Player thisPlayer){
    myUserLogin = thisUserLogin;
    setCommonVariables(thisPlayer);
  }

  public SuperScreen(EventHandler engine, Player thisPlayer){
    myEngine = engine;
    setCommonVariables(thisPlayer);
  }

  public SuperScreen(String gameType, Player thisPlayer){
    myGameType = gameType;
    setCommonVariables(thisPlayer);
  }

  private void setCommonVariables(Player thisPlayer){
    myStringResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "BasicStrings");
    myButtonResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "ButtonCreation");
    myErrorMessage = new Label();
    myPlayer = thisPlayer;
    myNodes = new ArrayList<>();
    myContents = new ArrayList<>();
  }

  public Scene styleScene(){
    VBox myCenterVBox = new VBox();
    for (Node a : myNodes){
      myCenterVBox.getChildren().add(a);
    }
    myCenterVBox.setSpacing(50);
    myCenterVBox.setAlignment(Pos.CENTER);

    Scene scene = new Scene(myCenterVBox, DIMENSION, DIMENSION);
    scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + styleSheet).toExternalForm());
    return scene;
  }

  public Node styleContents(){
    VBox myButtonVBox = new VBox();
    for (Node b : myContents){
      myButtonVBox.getChildren().add(b);
    }
    myButtonVBox.setSpacing(10);
    myButtonVBox.setAlignment(Pos.CENTER);
    return myButtonVBox;
  }

  public Button makeButton(String text, EventHandler<ActionEvent> handler) {
    Button newButton = new Button();
    newButton.setText(myButtonResources.getString(text));
    newButton.setOnAction(handler);
    return newButton;
  }

  public void setError(StringProperty message){
    myErrorMessage.textProperty().bindBidirectional(message);
  }

  public void setStyle(String styleSheet) {
    this.styleSheet = styleSheet;
  }

}
