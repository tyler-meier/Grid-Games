package ooga.player.screens;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ooga.data.UserProfile;
import ooga.player.Player;

public class NewProfileScreen {

  private static final int DIMENSION = 500;
  private static final String RESOURCES = "ooga/player/Resources/";
  private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
  private static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES;
  private static final String BUTTON_STRINGS = DEFAULT_RESOURCE_PACKAGE + "ButtonCreation";
  private static final String STYLESHEET = "default.css";

  private Button newProfButton, backButton;
  private ResourceBundle myResources;
  private Label profileLabel;
  private TextField newUsername, newPassword;
  private Player myPlayer;
  private UserProfile userData;
  // set these guys at some point from user input
  private String usernameString;
  private String passwordString;


  public NewProfileScreen(Player thisPlayer){
    myPlayer = thisPlayer;
    myResources = ResourceBundle.getBundle(BUTTON_STRINGS);
  }

  public Scene setUpScene(){
    setupLabel();
    setUpTextFields();
    setUpButtons();

    VBox myVBox = new VBox();
    myVBox.getChildren().addAll(profileLabel, newUsername, newPassword, newProfButton, backButton);
    myVBox.setSpacing(7);
    myVBox.setAlignment(Pos.CENTER);

    Scene newProfScene = new Scene(myVBox, DIMENSION, DIMENSION);
    newProfScene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + STYLESHEET).toExternalForm());
    return newProfScene;
  }

  private void setupLabel(){
    profileLabel = new Label("CHOOSE USERNAME AND PASSWORD");
  }

  private void setUpTextFields(){
    newUsername = new TextField();
    newPassword = new TextField();

    newUsername.setPromptText("Type in your username.");
    newPassword.setPromptText("Type in your password.");

    newUsername.getText();
    newPassword.getText();
  }

  private void setUpButtons(){
    newProfButton = makeButton("CreateNewProfileCommand", e -> myPlayer.setUpStartScreen("Username"));
    backButton = makeButton("BackButtonCommand", e -> myPlayer.setUpLoginScreen());
  }

  private Button makeButton(String text, EventHandler<ActionEvent> handler) {
    Button newButton = new Button();
    newButton.setText(myResources.getString(text));
    newButton.setOnAction(handler);
    return newButton;
  }

}
