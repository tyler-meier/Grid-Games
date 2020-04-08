package ooga.player.screens;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
  private static final String STYLESHEET = "default.css";

  private ResourceBundle myStringResources, myButtonResources;
  private Player myPlayer;
  private UserProfile userData;       // set these guys at some point from user input
  private String usernameString;
  private String passwordString;

  public LoginScreen(Player thisPlayer){
    myStringResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "BasicStrings");
    myButtonResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "ButtonCreation");
    myPlayer = thisPlayer;
  }

  public Scene setUpScene(){
    Node topLoginPanel = setupLoginAndLabel();
    Node buttonPanel = setUpButtons();

    VBox myCenterVBox = new VBox();
    myCenterVBox.getChildren().addAll(topLoginPanel, buttonPanel);
    myCenterVBox.setSpacing(50);
    myCenterVBox.setAlignment(Pos.CENTER);

    Scene loginScreen = new Scene(myCenterVBox, DIMENSION, DIMENSION);
    loginScreen.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
    return loginScreen;
  }

  private Node setupLoginAndLabel(){
    VBox topVBox = new VBox();

    Label loginLabel = new Label(myStringResources.getString("Login"));
    TextField username = new TextField();
    TextField password = new TextField();

    username.setPromptText(myStringResources.getString("TypeUsername"));
    password.setPromptText(myStringResources.getString("TypePassword"));

    username.getText();
    password.getText();

    topVBox.getChildren().addAll(loginLabel, username, password);
    topVBox.setSpacing(10);
    topVBox.setAlignment(Pos.CENTER);
    return topVBox;
  }

  private Node setUpButtons(){
    VBox buttonVBox = new VBox();

    Button loginButton = makeButton("LoginButtonCommand", e -> myPlayer.setUpStartScreen("Username")); //TODO access the  actual username
    Button guestButton = makeButton("GuestButtonCommand", e -> myPlayer.setUpStartScreen(myStringResources.getString("Guest")));
    Button newProfileButton = makeButton("NewProfileCommand", e -> myPlayer.setUpNewProfScreen());

    buttonVBox.getChildren().addAll(loginButton, guestButton, newProfileButton);
    buttonVBox.setSpacing(10);
    buttonVBox.setAlignment(Pos.CENTER);
    return buttonVBox;
  }

  private Button makeButton(String text, EventHandler<ActionEvent> handler) {
    Button newButton = new Button();
    newButton.setText(myButtonResources.getString(text));
    newButton.setOnAction(handler);
    return newButton;
  }

  //  public void setLoginButton(UserLogin userLogin){
//    loginButton.setOnMouseClicked(e -> {
//      userData = userLogin.getProfile(usernameString, passwordString);
//    });
//  }
}