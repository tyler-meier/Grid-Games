package ooga.player.screens;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ooga.controller.UserLogin;
import ooga.data.UserProfile;
import ooga.player.Player;

public class LoginScreen {

  private static final int DIMENSION = 600;
  private static final String RESOURCES = "ooga/player/Resources/";
  private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
  private static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES;
  private static final String BUTTON_STRINGS = DEFAULT_RESOURCE_PACKAGE + "ButtonCreation";
  private static final String STYLESHEET = "default.css";

  private Label loginLabel;
  private Button loginButton, newProfileButton, guestButton;
  private ResourceBundle myResources;
  private TextField username, password;
  private Player myPlayer;
  private UserProfile userData;
  // set these guys at some point from user input
  private String usernameString;
  private String passwordString;

  public LoginScreen(Player thisPlayer){
    myResources = ResourceBundle.getBundle(BUTTON_STRINGS);
    myPlayer = thisPlayer;
  }

  public Scene setUpScene(){
    setupLabel();
    setupLogin();
    setUpButtons();

    VBox myCenterVBox = new VBox();
    myCenterVBox.getChildren().addAll(loginLabel, username, password, loginButton, guestButton, newProfileButton);
    myCenterVBox.setSpacing(7);
    myCenterVBox.setAlignment(Pos.CENTER);

    Scene loginScreen = new Scene(myCenterVBox, DIMENSION, DIMENSION);
    loginScreen.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
    return loginScreen;
  }

  public void setLoginButton(UserLogin userLogin){
    loginButton.setOnMouseClicked(e -> {
      userData = userLogin.getProfile(usernameString, passwordString);
    });
  }

  private void setupLabel(){
    loginLabel = new Label("LOGIN");
  }

  private void setupLogin(){
    username = new TextField();
    password = new TextField();

    username.setPromptText("Enter Username");
    password.setPromptText("Enter Password");

    username.getText();
    password.getText();
  }

  private void setUpButtons(){
    loginButton = makeButton("LoginButtonCommand", e -> myPlayer.setUpStartScreen("Username"));
    guestButton = makeButton("GuestButtonCommand", e -> myPlayer.setUpStartScreen("Guest"));
    newProfileButton = makeButton("NewProfileCommand", e -> myPlayer.setUpNewProfScreen());
  }

  private Button makeButton(String text, EventHandler<ActionEvent> handler) {
    Button newButton = new Button();
    newButton.setText(myResources.getString(text));
    newButton.setOnAction(handler);
    return newButton;
  }
}