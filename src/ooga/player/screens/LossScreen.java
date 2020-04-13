package ooga.player.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.Scene;
import ooga.controller.UserLogin;
import ooga.data.UserProfile;
import ooga.player.Player;

public class LossScreen extends SuperScreen {

  private Player myPlayer;
  private UserLogin myUserLogin;
  private UserProfile userData;
  private ResourceBundle myStringResources;
  private List<Node> myNodes;

  public LossScreen(Player thisPlayer){
    super(thisPlayer);
    myPlayer = thisPlayer;
    myNodes = new ArrayList<>();
    myStringResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "BasicStrings");
  }

  public Scene setUpScene(){
    //Node topLoginPanel = setupLoginAndLabel();
    //Node buttonPanel = setUpButtons();
    myNodes.clear();
    myNodes.add(topLoginPanel);
    myNodes.add(buttonPanel);
    Scene scene = styleScene(myNodes);
    return scene;
  }

}
