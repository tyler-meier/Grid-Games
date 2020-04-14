package ooga.player.screens;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ooga.controller.UserLogin;
import ooga.data.UserProfile;
import ooga.player.Player;

public class NewProfileScreen extends SuperScreen{

  private TextField newUsername, newPassword;
  private UserProfile userData;

  public NewProfileScreen(UserLogin thisUserLogin, Player thisPlayer){
    super(thisUserLogin, thisPlayer);
  }

  public Scene setUpScene(){
    Node topNewProfPanel = setUpText();
    Node buttonPanel = setUpButtons();
    myNodes.clear();
    myNodes.add(topNewProfPanel);
    myNodes.add(buttonPanel);
    Scene scene = styleScene();
    return scene;
  }

  private Node setUpText(){
    Label profileLabel = new Label(myStringResources.getString("NewProfLabel"));
    newUsername = new TextField();
    newPassword = new TextField();

    newUsername.setPromptText(myStringResources.getString("NewUsername"));
    newPassword.setPromptText(myStringResources.getString("NewPassword"));

    myContents.clear();
    myContents.add(profileLabel);
    myContents.add(newUsername);
    myContents.add(newPassword);
    Node topVBox = styleContents();
    return topVBox;
  }

  private Node setUpButtons(){
    Button newProfButton = makeButton("CreateNewProfileCommand", e -> {
      userData = myUserLogin.getProfile(newUsername.getText(), newPassword.getText());
      if (userData != null){
        myPlayer.setUsername(userData.getUsername());
        myPlayer.setUpStartScreen();
      }
    });
    Button backButton = makeButton("BackButtonCommand", e -> myPlayer.setUpLoginScreen());

    myContents.clear();
    myContents.add(newProfButton);
    myContents.add(backButton);
    Node buttonVBox = styleContents();
    return buttonVBox;
  }
}