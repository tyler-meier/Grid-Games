package ooga.player.screens;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ooga.controller.UserLogin;
import ooga.data.UserProfile;
import ooga.player.Player;

/**
 * New Profile class that sets up the new profile screen when a user wants to create one
 * @author Tyler Meier
 */
public class NewProfileScreen extends SuperScreen{

  private TextField newUsername, newPassword;
  private UserProfile userData;

  /**
   * Constructor of this class, calls super to set up instance variables
   * @param thisPlayer current player
   * @param thisUserLogin the userLogin info for the created profile
   */
  public NewProfileScreen(UserLogin thisUserLogin, Player thisPlayer){
    super(thisUserLogin, thisPlayer);
  }

  /**
   * Sets up the new profile scene, with the label, text fields, and buttons
   * @return the final completed scene to be shown
   */
  public Scene setUpScene(){
    Node topNewProfPanel = setUpText();
    Node buttonPanel = setUpButtons();
    return styleScene(topNewProfPanel, buttonPanel);
  }

  /**
   * Sets up the login text fields and the label
   * @return a vBox containing these nodes
   */
  private Node setUpText(){
    Label profileLabel = new Label(myStringResources.getString("NewProfLabel"));
    newUsername = new TextField();
    newPassword = new TextField();

    newUsername.setPromptText(myStringResources.getString("NewUsername"));
    newPassword.setPromptText(myStringResources.getString("NewPassword"));

    return styleContents(profileLabel, newUsername, newPassword);
  }

  /**
   * Sets up the buttons for this scene and their events
   * @return a vBox containing all of the buttons
   */
  private Node setUpButtons(){
    Button newProfButton = makeButton("CreateNewProfileCommand", e -> {
      userData = myUserLogin.getProfile(newUsername.getText(), newPassword.getText());
      if (userData != null){
        myPlayer.setUsername(userData.getUsername());
        myPlayer.setUpStartScreen();
      }
    });
    Button backButton = makeButton("BackButtonCommand", e -> myPlayer.setUpLoginScreen());

    return styleContents(newProfButton, backButton);
  }
}