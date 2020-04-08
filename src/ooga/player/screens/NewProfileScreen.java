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
import ooga.data.UserProfile;
import ooga.player.Player;

public class NewProfileScreen {

  private static final int DIMENSION = 600;
  private static final String RESOURCES = "ooga/player/Resources/";
  private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
  private static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES;
  private static final String BUTTON_STRINGS = DEFAULT_RESOURCE_PACKAGE + "ButtonCreation";
  private static final String STYLESHEET = "default.css";

  private ResourceBundle myButtonResources, myStringResources;
  private Player myPlayer;
  private UserProfile userData;  // set these guys at some point from user input
  private String usernameString;
  private String passwordString;


  public NewProfileScreen(Player thisPlayer){
    myPlayer = thisPlayer;
    myButtonResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "ButtonCreation");
    myStringResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "BasicStrings");
  }

  public Scene setUpScene(){
    Node topNewProfPanel = setUpText();
    Node buttonPanel = setUpButtons();

    VBox myVBox = new VBox();
    myVBox.getChildren().addAll(topNewProfPanel, buttonPanel);
    myVBox.setSpacing(50);
    myVBox.setAlignment(Pos.CENTER);

    Scene newProfScene = new Scene(myVBox, DIMENSION, DIMENSION);
    newProfScene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
    return newProfScene;
  }

  private Node setUpText(){
    VBox topVBox = new VBox();

    Label profileLabel = new Label(myStringResources.getString("NewProfLabel"));
    TextField newUsername = new TextField();
    TextField newPassword = new TextField();

    newUsername.setPromptText(myStringResources.getString("NewUsername"));
    newPassword.setPromptText(myStringResources.getString("NewPassword"));

    newUsername.getText();
    newPassword.getText();

    topVBox.getChildren().addAll(profileLabel, newUsername, newPassword);
    topVBox.setSpacing(10);
    topVBox.setAlignment(Pos.CENTER);
    return topVBox;
  }

  private Node setUpButtons(){
    VBox buttonVBox = new VBox();

    Button newProfButton = makeButton("CreateNewProfileCommand", e -> myPlayer.setUpStartScreen("Username"));
    Button backButton = makeButton("BackButtonCommand", e -> myPlayer.setUpLoginScreen());

    buttonVBox.getChildren().addAll(newProfButton, backButton);
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

}
