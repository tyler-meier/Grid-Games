package ooga.player.screens;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ooga.controller.UserLogin;
import ooga.data.UserProfile;

import java.util.Map;

public class LoginScreen {

  private static final int DIMENSION = 500;

  private Scene loginScreen;
  private Label loginLabel;
  private BorderPane myBorderPane;
  private VBox myCenterVBox;
  private Button goButton, newProfileButton, guestButton;
  private UserProfile userData;
  // set these guys at some point from user input
  private String username;
  private String password;

  public LoginScreen(){
    myCenterVBox = new VBox();
    myBorderPane = new BorderPane();
  }

  public Scene setUpScene(){
    setupLabel();
    setupLogin();
    setUpOptions();
    myCenterVBox.getChildren().addAll(loginLabel, goButton, guestButton, newProfileButton);
    myBorderPane.setCenter(myCenterVBox);
    loginScreen = new Scene(myBorderPane, DIMENSION, DIMENSION);
    return loginScreen;
  }

  public void setLoginButton(UserLogin userLogin){
    goButton.setOnMouseClicked(e -> {
      userData = userLogin.getProfile(username, password);
    });
  }

  private void setupLabel(){
    loginLabel = new Label("LOGIN");
  }

  private void setupLogin(){
    //need to add the username and password type in stuff
  }

  private void setUpOptions(){
    goButton = makeButton("Go!");
    guestButton = makeButton("Continue as Guest"); //need to fix strings
    newProfileButton = makeButton("New? Creat New Profile");

    //need to add the start as guest, new player, and go buttons (need to do stuff with buttons)
  }

  private Button makeButton(String text) {
    Button newButton = new Button();
    newButton.setText(text);
    //newButton.setOnAction(handler);
    return newButton;
  }

}
