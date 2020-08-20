package ooga.player.screens;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
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

/**
 * Tests the button vbox and the top vbox to see if all contents are correct and if the text matches up
 * @author Tyler Meier
 */
class LoginScreenTest extends DukeApplicationTest {

  @Override
  public void start(Stage stage){
    Player player = new Player(stage);
    LoginScreen myLoginScreen = new LoginScreen(player);
    Scene myScene = myLoginScreen.setUpScene();
    stage.setScene(myScene);
    stage.show();
  }

  @Test
  void testButtons() {
    VBox buttons = lookup("#buttonPanel").query();
    Button login = lookup("#login").queryButton();
    Button guest = lookup("#guest").queryButton();
    Button window = lookup("#window").queryButton();
    Button newprof = lookup("#newprof").queryButton();
    ArrayList<Node> but = new ArrayList<>();
    but.add(login);
    but.add(guest);
    but.add(newprof);
    but.add(window);

    assertEquals(but, buttons.getChildren());
    assertEquals(Pos.CENTER, buttons.getAlignment());
    assertEquals(10, buttons.getSpacing());
    assertEquals("Login", login.getText());
    assertEquals("Continue As Guest", guest.getText());
    assertEquals("Add New Window", window.getText());
    assertEquals("New? Create New Profile", newprof.getText());
  }

  @Test
  void testTopVBox(){
    VBox topPanel = lookup("#topPanel").query();
    Label thisLabel = lookup("#logIN").query();
    TextField one = lookup("#use").query();
    TextField two = lookup("#pas").query();
    ArrayList<Node> lis= new ArrayList<>();
    lis.add(thisLabel);
    lis.add(one);
    lis.add(two);

    assertEquals(Pos.CENTER, topPanel.getAlignment());
    assertEquals(10, topPanel.getSpacing());
    assertEquals(lis, topPanel.getChildren());
    assertEquals("LOGIN", thisLabel.getText());
    assertEquals("Enter Username", one.getPromptText());
    assertEquals("Enter Password", two.getPromptText());
  }
}
