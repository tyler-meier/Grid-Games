package ooga.player.screens;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ooga.player.Player;

public class LossScreen extends SuperScreen {

  public LossScreen(Player thisPlayer){
    super(thisPlayer);
  }

  public Scene setUpScene(){
    Parent contents = setUpContents();
    Scene scene = finishStyling(contents);
    return scene;
  }

  private Parent setUpContents(){
    Label lossLabel = new Label(myStringResources.getString("Loss"));
    Button homeButton = makeButton("HomeCommand", e -> myPlayer.setUpStartScreen());
    Button resetButton = makeButton("ResetLevelCommand", e-> myPlayer.setUpStartScreen()); //TODO: fix reset button

    myContents.clear();
    myContents.add(lossLabel);
    myContents.add(homeButton);
    myContents.add(resetButton);
    Parent myVBox = (Parent) styleContents();
    return myVBox;
  }
}
