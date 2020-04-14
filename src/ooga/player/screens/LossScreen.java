package ooga.player.screens;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ooga.player.Player;

/**
 * The loss screen, which pops up when a player loses a level of a game
 * @author Tyler Meier
 */
public class LossScreen extends SuperScreen {

  /**
   * Constructor of this class, calls super to set up instance variables
   * @param thisPlayer the current player
   */
  public LossScreen(Player thisPlayer){
    super(thisPlayer);
  }

  /**
   * Sets up the scene once a player lost a level with label and buttons
   * @return the final completed scene to be shown
   */
  public Scene setUpScene(){
    Parent contents = setUpContents();
    return finishStyling(contents);
  }

  private Parent setUpContents(){
    Label lossLabel = new Label(myStringResources.getString("Loss"));
    Button homeButton = makeButton("HomeCommand", e -> myPlayer.setUpStartScreen());
    Button resetButton = makeButton("ResetLevelCommand", e-> myPlayer.setUpStartScreen()); //TODO: fix reset button
    return (Parent) styleContents(lossLabel, homeButton, resetButton);
  }
}
