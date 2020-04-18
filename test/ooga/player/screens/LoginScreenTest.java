package ooga.player.screens;

import static org.junit.jupiter.api.Assertions.*;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ooga.player.Player;
import ooga.util.DukeApplicationTest;
import org.junit.jupiter.api.Test;

class LoginScreenTest extends DukeApplicationTest {

  private Player player;
  private LoginScreen myLoginScreen = new LoginScreen(player);
  private Button login, guest, window, newprof;
  private Node topPanel;

  @Override
  public void start(Stage stage){
    player = new Player(stage);
    Scene myScene = myLoginScreen.setUpScene();
    stage.setScene(myScene);
    stage.show();
    login = lookup("#login").queryButton();
    guest = lookup("#guest").queryButton();
    window = lookup("#window").queryButton();
    newprof = lookup("#newprof").queryButton();
    topPanel = lookup("#topPanel").query();
  }

  @Test
  void testTextOnButtons() {
    assertEquals("Login", login.getText());
    assertEquals("Continue As Guest", guest.getText());
    assertEquals("Add New Window", window.getText());
    assertEquals("New? Create New Profile", newprof.getText());
  }

//  @Test
//  void testTopVBox(){
//    VBox thisOne = new VBox();
//    Label thisLabel = new Label("Login");
//    TextField one = new TextField();
//    one.setPromptText("Type in a new username");
//    TextField two = new TextField();
//    two.setPromptText("Type in a new password");
//    thisOne.getChildren().addAll(thisLabel, one, two);
//    thisOne.setSpacing(10);
//    thisOne.setAlignment(Pos.CENTER);
//    Node actual = myLoginScreen.setupText();
//
//    assertEquals(thisOne, actual);
//  }
}