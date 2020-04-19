package ooga.player.screens;

import java.util.ResourceBundle;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import ooga.player.Player;

/**
 * The Super Screen class, which is a super class of all of the screen classes and holds methods that they all utilize,
 * as well as instance variables that they use in their classes
 * @author Tyler Meier and Alyssa Shin
 */
public abstract class SuperScreen {

  protected static final String RESOURCES = "ooga/player/Resources/";
  protected static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
  protected static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES;
  private static final int DIMENSION = 650;
  private static final int MAIN_SPACING = 50;

  protected ResourceBundle myButtonResources, myStringResources, myGameNameResources;
  protected Label myErrorMessage;
  protected Player myPlayer;
  protected String myGameType;
  protected String styleSheet = "default.css";
  protected Scene myScene;
  protected IntegerProperty highScore = new SimpleIntegerProperty();

  /**
   * Constructor for super screen class that sets the instance variables
   * @param thisPlayer the current player
   */
  public SuperScreen(Player thisPlayer) {
    setCommonVariables(thisPlayer);
  }

  /**
   * Constructor for super screen class that sets the instance variables
   * @param gameType the current game being played
   * @param thisPlayer the current player
   */
  public SuperScreen(String gameType, Player thisPlayer){
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
    myCenterVBox.setSpacing(MAIN_SPACING);
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
    myScene = new Scene(contents, DIMENSION, DIMENSION);
    myScene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + styleSheet).toExternalForm());
    return myScene;
  }

  /**
   * Styles each individual root nodes of the scene into VBoxes
   * @param myContents endless parameters of each individual root node
   * @return this node VBox
   */
  public VBox styleContents(Node ... myContents){
    VBox myButtonVBox = new VBox();
    for (Node b : myContents){
      myButtonVBox.getChildren().add(b);
    }
    myButtonVBox.setSpacing(10);
    myButtonVBox.setAlignment(Pos.CENTER);
    return myButtonVBox;
  }

  /**
   * Allows an error message to be displayed if there  is an error by setting the message
   * binds with the backend to know when an error is thrown
   * @param message the message that will be displayed
   */
  public void setError(StringProperty message){
    myErrorMessage.textProperty().bindBidirectional(message);
    myErrorMessage.textProperty().setValue("");
  }

  /**
   * Sets the stylesheet to be used (either dark mode or regular mode)
   * @param styleSheet specific one
   */
  public void setStyle(String styleSheet) {
    myScene.getStylesheets().clear();
    this.styleSheet = styleSheet;
    myScene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + styleSheet).toExternalForm());
  }

  /**
   * Sets up the generic save button
   * @return save button
   */
  public Button makeSaveButton(){
    return makeButton("SaveCommand", e->{
      if(myPlayer.getMyUserProfile() != null) {
        myPlayer.getMyUserProfile().addHighScore(myGameType, highScore.getValue());
        myPlayer.getSaveButtonEvent().handle(e);
      }
      else {
        myErrorMessage.textProperty().setValue(myStringResources.getString("GuestSave"));
        myErrorMessage.setWrapText(true);
      }
    });
  }

  /**
   * Sets up the generic home button
   * @return home button
   */
  public Button makeHomeButton(){
    return makeButton("HomeCommand", e -> myPlayer.setUpStartScreen(myErrorMessage.textProperty()));
  }

  /**
   * Sets up the generic logout button
   * @return logout button
   */
  public Button makeLogoutButton(){
    return makeButton("LogoutCommand", e -> myPlayer.setUpLoginScreen());
  }

  /**
   * Sets up the generic reset game button
   * @return reset game button
   */
  public Button makeResetGameButton(){
    return makeButton("ResetGameCommand", myPlayer.getResetButtonEvent());
  }

  /**
   * Creates the button given the parameters
   * @param text the text to be displayed on the button (key to find text)
   * @param handler the event(s) that the button will do
   * @return the newly created button
   */
  public Button makeButton(String text, EventHandler<ActionEvent> handler) {
    Button newButton = new Button();
    newButton.setText(myButtonResources.getString(text));
    newButton.setOnAction(handler);
    return newButton;
  }
}