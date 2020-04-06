package ooga.player.screens;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class LoginScreen {

  private Scene loginScreen;

  public LoginScreen(){
  }

  public Scene setUpScene(){
    loginScreen = new Scene(new BorderPane(), 1000, 1000);
    return loginScreen;
  }
}
