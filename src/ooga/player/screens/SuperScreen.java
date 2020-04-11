package ooga.player.screens;

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
  private static final int DIMENSION = 600;
  String styleSheet = "default.css";

  private ResourceBundle myButtonResources;
  private Label myErrorMessage;

  public SuperScreen(Player thisPlayer) {
    myButtonResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "ButtonCreation");
    myErrorMessage = new Label();
  }

  public SuperScreen(UserLogin myUserLogin, Player player){
    myButtonResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "ButtonCreation");
    myErrorMessage = new Label();
  }

  public SuperScreen(EventHandler engine, Player thisPlayer){
    myButtonResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "ButtonCreation");
    myErrorMessage = new Label();
  }

  public SuperScreen(String gameType, Player player){
    myButtonResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "ButtonCreation");
    myErrorMessage = new Label();
  }

  public Scene styleScene(List<Node> myNodes){
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

  //returns a button with correct text, associated event handler
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
