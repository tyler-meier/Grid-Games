package ooga.player.screens;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import ooga.player.Player;

/**
 * The won level screen, which pops up when a player beats the level in a game
 * @author Tyler Meier
 */
public class WonLevelScreen extends SuperScreen {
  private static final String LEVEL_UP_SOUND = "LevelUp";

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
    playSound(LEVEL_UP_SOUND);
    VBox contents = setUpContents();
    return finishStyling(contents);
  }

  private VBox setUpContents(){
    Label winLevelLabel = new Label(myStringResources.getString("WonLevel"));
    //TODO: this restarts level?
    Button nextLevelButton = makeButton("NextLevelCommand", e -> {
      try{
        myPlayer.getNextLevelEvent().handle(e);
      }catch (Exception p){
        myPlayer.setUpWonGameScreen();
      }
    });
    return styleContents(winLevelLabel, nextLevelButton, makeHomeButton(), makeResetLevelButton());
  }
}
