package ooga.player.screens;

import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ooga.controller.UserLogin;
import ooga.data.UserProfile;
import ooga.player.Player;

import javax.swing.*;

/**
 * The Super Screen class, which is a super class of all of the screen classes and holds methods that they all utilize,
 * as well as instance variables that they use in their classes
 * @author Tyler Meier and Alyssa Shin
 */
public abstract class SuperScreen {

  protected static final String RESOURCES = "ooga/player/Resources/";
  protected static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
  protected static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES;
  protected String styleSheet = "default.css";
  private static final int DIMENSION = 600;

  protected ResourceBundle myButtonResources, myStringResources, myGameNameResources;
  protected Label myErrorMessage;
  protected Player myPlayer;
  protected EventHandler<ActionEvent> myEventEngine;
  protected String myGameType;
  protected UserLogin myUserLogin;

  /**
   * Constructor for super screen class that sets the instance variables
   * @param thisPlayer the current player
   */
  public SuperScreen(Player thisPlayer) {
    setCommonVariables(thisPlayer);
  }

  /**
   * Constructor for super screen class that sets the instance variables
   * @param thisUserLogin data from the current player
   * @param thisPlayer the current player
   */
  public SuperScreen(UserLogin thisUserLogin, Player thisPlayer){
    myUserLogin = thisUserLogin;
    setCommonVariables(thisPlayer);
  }

  /**
   * Constructor for super screen class that sets the instance variables
   * @param engine the event handler to create the engine
   * @param thisPlayer the current player
   */
  public SuperScreen(EventHandler<ActionEvent> engine, Player thisPlayer){
    myEventEngine = engine;
    setCommonVariables(thisPlayer);
  }

  /**
   * Constructor for super screen class that sets the instance variables
   * @param gameType the current game being played
   * @param thisPlayer the current player
   */
  public SuperScreen(EventHandler<ActionEvent> engine, String gameType, Player thisPlayer){
    myEventEngine = engine;
    myGameType = gameType;
    setCommonVariables(thisPlayer);
  }

  private void setCommonVariables(Player thisPlayer){
    myStringResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "BasicStrings");
    myButtonResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "ButtonCreation");
    myGameNameResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "NamesOfGames");
    myErrorMessage = new Label();
    myPlayer = thisPlayer;
  }

  /**
   * Styles the scene so that each group of nodes in the scene are aligned right
   * @param myNodes endless param of nodes being added to the main vBox
   * @return the completed styled scene to be shown
   */
  public Scene styleScene(Node ... myNodes){
    VBox myCenterVBox = new VBox();
    for (Node a : myNodes){
      myCenterVBox.getChildren().add(a);
    }
    myCenterVBox.setSpacing(50);
    myCenterVBox.setAlignment(Pos.CENTER);
    myCenterVBox.getChildren().add(myErrorMessage);

    return finishStyling(myCenterVBox);
  }

  /**
   * Finishes styling the scene by adding stylesheets, this method is here for screens that only have one node on the screen and to
   * create parent node for screen
   * @param contents the parent node that will be shown the screen
   * @return completed scene to be shown
   */
  public Scene finishStyling(Parent contents){
    Scene scene = new Scene(contents, DIMENSION, DIMENSION);
    scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + styleSheet).toExternalForm());
    return scene;
  }

  /**
   * Styles each individual root nodes of the scene into VBoxes
   * @param myContents endless parameters of each individual root node
   * @return this node VBox
   */
  public Node styleContents(Node ... myContents){
    VBox myButtonVBox = new VBox();
    for (Node b : myContents){
      myButtonVBox.getChildren().add(b);
    }
    myButtonVBox.setSpacing(10);
    myButtonVBox.setAlignment(Pos.CENTER);
    return myButtonVBox;
  }

  /**
   * Creates the button given the parameters
   * @param text the text to be displayed on the button (key to find text)
   * @param handler the event(s) that the button will do
   * @return the newly created buttons
   */
  public Button makeButton(String text, EventHandler<ActionEvent> handler) {
    Button newButton = new Button();
    newButton.setText(myButtonResources.getString(text));
    newButton.setOnAction(handler);
    return newButton;
  }

  /**
   * Allows an error message to be displayed if there  is an error by setting the message
   * binds with the backend to know when an error is thrown
   * @param message the message that will be displayed
   */
  public void setError(StringProperty message){
    myErrorMessage.textProperty().bindBidirectional(message);
  }

  /**
   * Sets the stylesheet to be used (either dark mode or regular mode)
   * @param styleSheet specific one
   */
  public void setStyle(String styleSheet) {
    this.styleSheet = styleSheet;
  }
}