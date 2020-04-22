package ooga.player.screens;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ooga.data.UserProfile;
import ooga.player.Player;

/**
 * Login Screen class that sets up the login screen for a player
 * @author Tyler Meier
 */
public class LoginScreen extends SuperScreen {

  private TextField username = new TextField();
  private TextField password = new TextField();

  /**
   * Constructor of this class, calls super to set up instance variables
   * @param thisPlayer current player
   */
  public LoginScreen(Player thisPlayer){
    super(thisPlayer);
  }

  /**
   * Sets up the login scene, with the labels, text fields, and buttons
   * @return the final completed scene to be shown
   */
  public Scene setUpScene(){
    playSound("welcome");
    VBox topLoginPanel = setupText();
    topLoginPanel.setId("topPanel");  //for testing
    VBox buttonPanel = setUpButtons();
    myErrorMessage.textProperty().setValue("");
    return styleScene(topLoginPanel, buttonPanel);
  }

  private VBox setupText(){
    Label loginLabel = new Label(myStringResources.getString("Login"));
    username.setPromptText(myStringResources.getString("TypeUsername"));
    password.setPromptText(myStringResources.getString("TypePassword"));
    return styleContents(loginLabel, username, password);
  }

  private VBox setUpButtons(){
    Button loginButton = makeButton("LoginButtonCommand", e -> {
      UserProfile myUserProfile = myPlayer.getMyUserLogin().getProfile(username.getText(), password.getText());
      if(myUserProfile != null){
        myPlayer.setUserProfile(myUserProfile);
        myPlayer.setUsername(myUserProfile.getUsername());
        myPlayer.setUpStartScreen(myErrorMessage.textProperty());
      }
    });
    Button guestButton = makeButton("GuestButtonCommand", e -> {
      myPlayer.setUsername(myStringResources.getString("Guest"));
      myPlayer.setUpStartScreen(myErrorMessage.textProperty());
    });
    Button newWindowButton = makeButton("NewWindowCommand", e -> myPlayer.getNewWindowEvent().handle(e));
    Button newProfileButton = makeButton("NewProfileCommand", e -> myPlayer.setUpNewProfScreen(myErrorMessage.textProperty()));
    setForTesting(loginButton, guestButton, newWindowButton, newProfileButton);
    return styleContents(loginButton, guestButton, newProfileButton, newWindowButton);
  }

  private void setForTesting(Button loginButton, Button guestButton, Button newWindowButton, Button newProfileButton){
    //setting ID for testing
    loginButton.setId("login");
    guestButton.setId("guest");
    newWindowButton.setId("window");
    newProfileButton.setId("newprof");
  }
}