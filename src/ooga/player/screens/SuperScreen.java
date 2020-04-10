package ooga.player.screens;

import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public abstract class SuperScreen {

  public static final String RESOURCES = "ooga/player/Resources/";
  public static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
  public static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES;
  public static final String STYLESHEET = "default.css";

  private ResourceBundle myButtonResources, myStringResources;

  public SuperScreen() {
    myButtonResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "ButtonCreation");
    myStringResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "BasicStrings");
  }

  public Scene setUpScene(){

  }

  public void switchStyle(){

  }

  private Node setUpButtons(){

  }

  //returns a button with correct text, associated event handler
  private Button makeButton(String text, EventHandler<ActionEvent> handler) {
    Button newButton = new Button();
    newButton.setText(myButtonResources.getString(text));
    newButton.setOnAction(handler);
    return newButton;
  }

  public void setError(StringProperty message){
  }

}
