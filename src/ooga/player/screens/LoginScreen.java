package ooga.player.screens;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ooga.controller.UserLogin;
import ooga.data.UserProfile;
import ooga.player.Player;


/**
 * Login Screen class that sets up the login screen for a player
 * @author Tyler Meier
 */
public class LoginScreen extends SuperScreen{

  private TextField username, password;
  private UserLogin myUserLogin;
  private UserProfile userData;
  private Button newWindowButton;

  /**
   * Constructor of this class, calls super to set up instance variables
   * @param thisPlayer
   */
  public LoginScreen(Player thisPlayer){
    super(thisPlayer);
  }

  /**
   * Sets up the login scene, with the labels, text fields, and buttons
   * @return the final completed scene to be shown
   */
  public Scene setUpScene(){
    Node topLoginPanel = setupText();
    Node buttonPanel = setUpButtons();
    return styleScene(topLoginPanel, buttonPanel);
  }

  /**
   * Gets the userLogin info of the player trying to log in
   * @param thisUserLogin the data info of the player trying to log in
   */
  public void giveMeUserLogin(UserLogin thisUserLogin){
    myUserLogin = thisUserLogin;
  }

  public void setNewWindow(EventHandler<ActionEvent> newWindowAction){
    newWindowButton.setOnAction(newWindowAction);
  }

  private Node setupText(){
    Label loginLabel = new Label(myStringResources.getString("Login"));
    username = new TextField();
    password = new TextField();

    username.setPromptText(myStringResources.getString("TypeUsername"));
    password.setPromptText(myStringResources.getString("TypePassword"));

    username.getText();
    password.getText();

    return styleContents(loginLabel, username, password);
  }

  private Node setUpButtons(){
    Button loginButton = makeButton("LoginButtonCommand", e -> {
      userData = myUserLogin.getProfile(username.getText(), password.getText());
      if(userData != null){
        myPlayer.setUsername(userData.getUsername());
        myPlayer.setUpStartScreen();
      }
    });
    Button guestButton = makeButton("GuestButtonCommand", e -> {
      myPlayer.setUsername(myStringResources.getString("Guest"));
      myPlayer.setUpStartScreen();
    });
    Button newProfileButton = makeButton("NewProfileCommand", e -> myPlayer.setUpNewProfScreen());
    newWindowButton = new Button("Add New Window");

    return styleContents(loginButton, guestButton, newProfileButton, newWindowButton);
  }
}