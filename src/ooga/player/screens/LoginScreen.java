package ooga.player.screens;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ooga.controller.UserLogin;
import ooga.data.UserProfile;
import ooga.player.Player;

public class LoginScreen extends SuperScreen{

  private TextField username, password;
  private UserLogin myUserLogin;
  private UserProfile userData;

  public LoginScreen(Player thisPlayer){
    super(thisPlayer);
  }

  public Scene setUpScene(){
    Node topLoginPanel = setupLoginAndLabel();
    Node buttonPanel = setUpButtons();
    myNodes.clear();
    myNodes.add(topLoginPanel);
    myNodes.add(buttonPanel);
    Scene scene = styleScene();
    return scene;
  }

  public void giveMeUserLogin(UserLogin thisUserLogin){
    myUserLogin = thisUserLogin;
  }

  private Node setupLoginAndLabel(){
    Label loginLabel = new Label(myStringResources.getString("Login"));
    username = new TextField();
    password = new TextField();

    username.setPromptText(myStringResources.getString("TypeUsername"));
    password.setPromptText(myStringResources.getString("TypePassword"));

    username.getText();
    password.getText();

    myContents.clear();
    myContents.add(loginLabel);
    myContents.add(username);
    myContents.add(password);
    Node topVBox = styleContents();
    return topVBox;
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

    myContents.clear();
    myContents.add(loginButton);
    myContents.add(guestButton);
    myContents.add(newProfileButton);
    Node buttonVBox = styleContents();
    return buttonVBox;
  }
}