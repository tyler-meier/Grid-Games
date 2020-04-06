package ooga.player.screens;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class LoginScreen {

  private static final int DIMENSION = 500;

  private Scene loginScreen;
  private Label loginLabel;
  private BorderPane myBorderPane;
  private VBox myCenterVBox;

  public LoginScreen(){
    myCenterVBox = new VBox();

  }

  public Scene setUpScene(){
    setupLabel();
    setupLogin();
    setUpOptions();
    myCenterVBox.getChildren().add(loginLabel);
    BorderPane.setAlignment(myCenterVBox, Pos.CENTER);
    myBorderPane = new BorderPane(myCenterVBox);
    loginScreen = new Scene(myBorderPane, DIMENSION, DIMENSION);
    return loginScreen;
  }

  private void setupLabel(){
    loginLabel = new Label("LOGIN");
  }

  private void setupLogin(){
    //need to add the username and password type in stuff
  }

  private void setUpOptions(){
    //need to add the start as guest, new player, and go buttons (need to do stuff with buttons)
  }

  private Button makeButton(String text, EventHandler<ActionEvent> handler) {
    Button newButton = new Button();
    //newButton.setText(myResources.getString(text));
    newButton.setOnAction(handler);
    return newButton;
  }

}
