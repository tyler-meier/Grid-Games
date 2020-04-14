package ooga.player.screens;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ooga.player.Player;

public class LossScreen extends SuperScreen {

  public LossScreen(Player thisPlayer){
    super(thisPlayer);
  }

  public Scene setUpScene(){
    Node contents = setUpContents();
    myNodes.clear();
    myNodes.add(contents);
    Scene scene = styleScene();
    return scene;
  }

  public Node setUpContents(){
    Label lossLabel = new Label(myStringResources.getString("Loss"));
    Button homeButton = makeButton("HomeCommand", e -> myPlayer.setUpStartScreen());
    Button resetButton = makeButton("ResetLevelCommand", e-> myPlayer.setUpStartScreen()); //TODO: fix reset button

    myContents.clear();
    myContents.add(lossLabel);
    myContents.add(homeButton);
    myContents.add(resetButton);
    Node myVBox = styleContents();
    return myVBox;
  }
}
