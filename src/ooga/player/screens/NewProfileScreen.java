package ooga.player.screens;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ooga.data.UserProfile;
import ooga.player.Player;

/**
 * New Profile class that sets up the new profile screen when a user wants to create one
 * @author Tyler Meier
 */
public class NewProfileScreen extends SuperScreen {

  private TextField newUsername, newPassword;

  /**
   * Constructor of this class, calls super to set up instance variables
   * @param thisPlayer current player
   */
  public NewProfileScreen(Player thisPlayer){
    super(thisPlayer);
  }

  /**
   * Sets up the new profile scene, with the label, text fields, and buttons
   * @return the final completed scene to be shown
   */
  public Scene setUpScene(){
    VBox topNewProfPanel = setUpText();
    VBox buttonPanel = setUpButtons();
    return styleScene(topNewProfPanel, buttonPanel);
  }

  private VBox setUpText(){
    Label profileLabel = new Label(myStringResources.getString("NewProfLabel"));
    newUsername = new TextField();
    newPassword = new TextField();
    newUsername.setPromptText(myStringResources.getString("NewUsername"));
    newPassword.setPromptText(myStringResources.getString("NewPassword"));
    return styleContents(profileLabel, newUsername, newPassword);
  }

  private VBox setUpButtons(){
    Button newProfButton = makeButton("CreateNewProfileCommand", e -> {
      UserProfile userData = myPlayer.getMyNewUserLogin().getProfile(newUsername.getText(), newPassword.getText());
      if (userData != null){
        myPlayer.setUsername(userData.getUsername());
        myPlayer.setUpStartScreen(myErrorMessage.textProperty());
      }
    });
    Button backButton = makeButton("BackButtonCommand", e -> myPlayer.setUpLoginScreen());
    return styleContents(newProfButton, backButton);
  }
}