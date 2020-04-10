package ooga.player.screens;

import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
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

public class LoginScreen extends SuperScreen{

  private static final int DIMENSION = 600;

  private ResourceBundle myStringResources, myButtonResources;
  private Player myPlayer;
  private TextField username, password;
  private UserLogin myUserLogin;
  private UserProfile userData;
  private Label myErrorMessage;

  public LoginScreen(Player thisPlayer){
    myErrorMessage = new Label();
    myPlayer = thisPlayer;
  }

  @Override
  public Scene setUpScene(){
    Node topLoginPanel = setupLoginAndLabel();
    Node buttonPanel = setUpButtons();

    VBox myCenterVBox = new VBox();
    myCenterVBox.getChildren().addAll(topLoginPanel, buttonPanel, myErrorMessage);
    myCenterVBox.setSpacing(50);
    myCenterVBox.setAlignment(Pos.CENTER);

    Scene loginScreen = new Scene(myCenterVBox, DIMENSION, DIMENSION);
    loginScreen.getStylesheets().add(getClass().getResource(super.DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
    return loginScreen;
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

  private Button makeButton(String text, EventHandler<ActionEvent> handler) {
    Button newButton = new Button();
    newButton.setText(myButtonResources.getString(text));
    newButton.setOnAction(handler);
    return newButton;
  }

  public void giveMeUserLogin(UserLogin thisUserLogin){
    myUserLogin = thisUserLogin;
  }

  public void setError(StringProperty message){
    myErrorMessage.textProperty().bindBidirectional(message);
  }

}