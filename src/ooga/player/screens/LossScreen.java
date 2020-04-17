package ooga.player.screens;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ooga.player.Player;
import ooga.player.screens.SuperScreen;

/**
 * The loss screen, which pops up when a player loses a level of a game
 * @author Tyler Meier
 */
public class LossScreen extends SuperScreen {

  /**
   * Constructor of this class, calls super to set up instance variables
   * @param thisPlayer the current player
   */
  public LossScreen(EventHandler<ActionEvent> event, Player thisPlayer){
    super(event, thisPlayer);
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
    Button homeButton = makeButton("HomeCommand", e -> myPlayer.setUpStartScreen(myErrorMessage.textProperty()));
    Button resetButton = makeButton("ResetLevelCommand", myEventEngine);
    return (Parent) styleContents(lossLabel, homeButton, resetButton);
  }
}
