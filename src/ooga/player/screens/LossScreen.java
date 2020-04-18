package ooga.player.screens;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
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
    VBox contents = setUpContents();
    return finishStyling(contents);
  }

  private VBox setUpContents(){
    Label lossLabel = new Label(myStringResources.getString("Loss"));
    return styleContents(lossLabel, makeHomeButton(), makeSaveButton(), makeResetGameButton());
  }
}
