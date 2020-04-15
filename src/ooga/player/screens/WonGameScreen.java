package ooga.player.screens;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import ooga.player.Player;

/**
 * The won game screen, which pops up when a player has completely beat a game
 * @author Tyler Meier
 */
public class WonGameScreen extends SuperScreen{

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
    Parent contents = setUpContents();
    return finishStyling(contents);
  }

  private Parent setUpContents(){
    Label winGameLabel = new Label(myStringResources.getString("WonGame"));
    Button homeButton = makeButton("HomeCommand", e -> myPlayer.setUpStartScreen());
    Button saveButton = makeButton("SaveCommand", e-> myPlayer.setUpStartScreen()); //TODO: fix save button, do i need this here?
    Button resetGameButton = makeButton("ResetGameCommand", e-> myPlayer.setUpStartScreen()); //TODO: fix the reset button
    return (Parent) styleContents(winGameLabel, homeButton, saveButton, resetGameButton);
  }
}