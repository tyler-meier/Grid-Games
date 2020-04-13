package ooga.player.screens;

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
    Scene scene = styleScene(myNodes);
    return scene;
  }

  private Node setUpText(){
    VBox topVBox = new VBox();

    Label profileLabel = new Label(myStringResources.getString("NewProfLabel"));
    newUsername = new TextField();
    newPassword = new TextField();

    newUsername.setPromptText(myStringResources.getString("NewUsername"));
    newPassword.setPromptText(myStringResources.getString("NewPassword"));

    topVBox.getChildren().addAll(profileLabel, newUsername, newPassword);
    topVBox.setSpacing(10);
    topVBox.setAlignment(Pos.CENTER);
    return topVBox;
  }

  private Node setUpButtons(){
    VBox buttonVBox = new VBox();

    Button newProfButton = makeButton("CreateNewProfileCommand", e -> {
      userData = myUserLogin.getProfile(newUsername.getText(), newPassword.getText());
      if (userData != null){
        myPlayer.setUpStartScreen(userData.getUsername());
      }
    });
    Button backButton = makeButton("BackButtonCommand", e -> myPlayer.setUpLoginScreen());

    buttonVBox.getChildren().addAll(newProfButton, backButton);
    buttonVBox.setSpacing(10);
    buttonVBox.setAlignment(Pos.CENTER);
    return buttonVBox;
  }

}
