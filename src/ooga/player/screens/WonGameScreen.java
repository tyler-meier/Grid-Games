package ooga.player.screens;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ooga.player.Player;

/**
 * The won game screen, which pops up when a player has completely beat a game
 * @author Tyler Meier
 */
public class WonGameScreen extends SuperScreen {

  /**
   * Constructor of this class, calls super to set up instance variables
   * @param thisPlayer the current player
   */
  public WonGameScreen(Player thisPlayer) {
    super(thisPlayer);
  }

  /**
   * Sets up the scene once a player won a game with label and buttons
   * @return the final completed scene to be shown
   */
  public Scene setUpScene(){
    VBox contents = setUpContents();
    return finishStyling(contents);
  }

  private VBox setUpContents(){
    Label winGameLabel = new Label(myStringResources.getString("WonGame"));
    return styleContents(winGameLabel, makeHomeButton(), makeResetGameButton(), makeResetLevelButton(), makeSaveButton());
  }
}
