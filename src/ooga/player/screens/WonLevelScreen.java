package ooga.player.screens;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ooga.player.Player;

public class WonLevelScreen extends SuperScreen{

  public WonLevelScreen(Player player) {
    super(player);
  }

  public Scene setUpScene(){
    Parent contents = setUpContents();
    Scene scene = finishStyling(contents);
    return scene;
  }

  private Parent setUpContents(){
    Label winLevelLabel = new Label(myStringResources.getString("WonLevel"));
    Button nextLevelButton = makeButton("NextLevelCommand", e -> myPlayer.setUpStartScreen()); //TODO: fix next level  button
    Button homeButton = makeButton("HomeCommand", e -> myPlayer.setUpStartScreen());
    Button saveButton = makeButton("SaveCommand", e-> myPlayer.setUpStartScreen()); //TODO: fix save button

    myContents.clear();
    myContents.add(winLevelLabel);
    myContents.add(nextLevelButton);
    myContents.add(homeButton);
    myContents.add(saveButton);
    Parent myVBox = (Parent) styleContents();
    return myVBox;
  }
}
