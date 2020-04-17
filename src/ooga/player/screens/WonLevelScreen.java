package ooga.player.screens;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ooga.player.Player;
import ooga.player.screens.SuperScreen;

/**
 * The won level screen, which pops up when a player beats the level in a game
 * @author Tyler Meier
 */
public class WonLevelScreen extends SuperScreen {

  /**
   * Constructor of this class, calls super to set up instance variables
   * @param thisPlayer the current player
   */
  public WonLevelScreen(Player thisPlayer) {
    super(thisPlayer);
  }

  /**
   * Sets up the scene once a player won a level with label and buttons
   * @return the final completed scene to be shown
   */
  public Scene setUpScene(){
    Parent contents = setUpContents();
    return finishStyling(contents);
  }

  private Parent setUpContents(){
    Label winLevelLabel = new Label(myStringResources.getString("WonLevel"));
    Button nextLevelButton = makeButton("NextLevelCommand", e -> myPlayer.setUpStartScreen(myErrorMessage.textProperty())); //TODO: fix next level  button
    Button homeButton = makeButton("HomeCommand", e -> myPlayer.setUpStartScreen(myErrorMessage.textProperty()));
    Button saveButton = makeButton("SaveCommand", myEventEngine);
    return (Parent) styleContents(winLevelLabel, nextLevelButton, homeButton, saveButton);
  }
}
