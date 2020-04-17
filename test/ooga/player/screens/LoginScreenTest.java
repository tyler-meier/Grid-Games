package ooga.player.screens;

import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import ooga.player.Player;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;

class LoginScreenTest extends ApplicationTest {

  private Player player = new Player();
  private LoginScreen myLoginScreen = new LoginScreen(player);
  private Button login, guest, window, newprof;

  @Override
  public void start(Stage stage){
    Scene myScene = myLoginScreen.setUpScene();
    stage.setScene(myScene);
    stage.show();
    login = lookup("#login").queryButton();
    guest = lookup("#guest").queryButton();
    window = lookup("#window").queryButton();
    newprof = lookup("#newprof").queryButton();
  }

  @Test
  void testTextOnButtons() {
    assertEquals("Login", login.getText());
    assertEquals("", guest.getText());
    assertEquals("", window.getText());
    assertEquals("New? Create New Profile", newprof.getText());
  }
}