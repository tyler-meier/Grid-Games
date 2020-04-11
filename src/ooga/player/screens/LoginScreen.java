package ooga.player.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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

public class LoginScreen extends SuperScreen{

  private Player myPlayer;
  private TextField username, password;
  private UserLogin myUserLogin;
  private UserProfile userData;
  private ResourceBundle myStringResources;
  private List<Node> myNodes;

  public LoginScreen(Player thisPlayer){
    super(thisPlayer);
    myPlayer = thisPlayer;
    myNodes = new ArrayList<>();
    myStringResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "BasicStrings");
  }

  public Scene setUpScene(){
    Node topLoginPanel = setupLoginAndLabel();
    Node buttonPanel = setUpButtons();
    myNodes.clear();
    myNodes.add(topLoginPanel);
    myNodes.add(buttonPanel);
    Scene scene = styleScene(myNodes);
    return scene;
  }

  public void giveMeUserLogin(UserLogin thisUserLogin){
    myUserLogin = thisUserLogin;
  }

  private Node setupLoginAndLabel(){
    VBox topVBox = new VBox();

    Label loginLabel = new Label(myStringResources.getString("Login"));
    username = new TextField();
    password = new TextField();

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

    Button loginButton = makeButton("LoginButtonCommand", e -> {
      userData = myUserLogin.getProfile(username.getText(), password.getText());
      if(userData != null){
        myPlayer.setUpStartScreen(username.getText());
      }
    });
    Button guestButton = makeButton("GuestButtonCommand", e -> myPlayer.setUpStartScreen(myStringResources.getString("Guest")));
    Button newProfileButton = makeButton("NewProfileCommand", e -> myPlayer.setUpNewProfScreen());

    buttonVBox.getChildren().addAll(loginButton, guestButton, newProfileButton);
    buttonVBox.setSpacing(10);
    buttonVBox.setAlignment(Pos.CENTER);
    return buttonVBox;
  }
}

