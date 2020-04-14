package ooga.player.screens;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ooga.player.Player;

public class WonGameScreen extends SuperScreen{

  public WonGameScreen(Player player) {
    super(player);
  }

  public Scene setUpScene(){
    Parent contents = setUpContents();
    Scene scene = finishStyling(contents);
    return scene;
  }

  private Parent setUpContents(){
    Label winGameLabel = new Label(myStringResources.getString("WonGame"));
    Button homeButton = makeButton("HomeCommand", e -> myPlayer.setUpStartScreen());
    Button saveButton = makeButton("SaveCommand", e-> myPlayer.setUpStartScreen()); //TODO: fix save button, do i need this here?
    Button resetGameButton = makeButton("ResetGameCommand", e-> myPlayer.setUpStartScreen()); //TODO: fix the reset button

    myContents.clear();
    myContents.add(winGameLabel);
    myContents.add(homeButton);
    myContents.add(saveButton);
    myContents.add(resetGameButton);
    Parent myVBox = (Parent) styleContents();
    return myVBox;
  }
}
