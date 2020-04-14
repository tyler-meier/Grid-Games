package ooga.player.screens;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ooga.player.Player;

public class WonLevelScreen extends SuperScreen{

  public WonLevelScreen(Player player) {
    super(player);
  }

  public Scene setUpScene(){
    Node contents = setUpContents();
    myNodes.clear();
    myNodes.add(contents);
    Scene scene = styleScene();
    return scene;
  }

  public Node setUpContents(){
    Label winLevelLabel = new Label(myStringResources.getString("WonLevel"));
    Button nextLevelButton = makeButton("NextLevelCommand", e -> myPlayer.setUpStartScreen()); //TODO: fix next level  button
    Button homeButton = makeButton("HomeCommand", e -> myPlayer.setUpStartScreen());
    Button saveButton = makeButton("SaveCommand", e-> myPlayer.setUpStartScreen()); //TODO: fix save button

    myContents.clear();
    myContents.add(winLevelLabel);
    myContents.add(nextLevelButton);
    myContents.add(homeButton);
    myContents.add(saveButton);
    Node myVBox = styleContents();
    return myVBox;
  }
}
