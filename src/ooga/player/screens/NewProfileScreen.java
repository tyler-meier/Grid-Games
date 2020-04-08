package ooga.player.screens;

import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import ooga.data.UserProfile;
import ooga.player.Player;

public class NewProfileScreen {

  private static final int DIMENSION = 500;
  private static final String RESOURCES = "ooga/player/Resources/";
  private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
  private static final String BUTTON_STRINGS = DEFAULT_RESOURCE_PACKAGE + "ButtonCreation";
  private static final String STYLESHEET = "default.css";

  private Button newProfButton;
  private ResourceBundle myResources;
  private TextField newUsername, newPassword;
  private Player myPlayer;
  private UserProfile userData;
  // set these guys at some point from user input
  private String usernameString;
  private String passwordString;


  public NewProfileScreen(){
    myPlayer = new Player();
  }

  public Scene setUpScene(){
    setUpTextFields();
    VBox vBox = new VBox();
    vBox.getChildren().addAll(newUsername, newPassword);
    BorderPane myBP = new BorderPane();
    myBP.setCenter(vBox);
    System.out.println("here");
    Scene newProfScene = new Scene(myBP, DIMENSION, DIMENSION);
    return newProfScene;
  }

  private void setUpTextFields(){
    newUsername = new TextField();
    newPassword = new TextField();

    newUsername.setPromptText("Type in what you would like your username to be.");
    newPassword.setPromptText("Type in what you would like your password to be.");

    newUsername.getText();
    newPassword.getText();
  }

}
