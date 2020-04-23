package ooga.player;

import java.util.Map;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ooga.controller.UserLogin;
import ooga.data.UserProfile;
import ooga.engine.grid.Grid;

public interface PlayerStart {

  /**
   *
   */
  String getStyle();

  /**
   *
   */
  void setUpLoginScreen();

  /**
   *
   * @param dataError
   */
  void setUpNewProfScreen(StringProperty dataError);

  /**
   *
   * @param dataError
   */
  void setUpStartScreen(StringProperty dataError);

  /**
   *
   * @param backendGrid
   * @param dataError
   */
  void setUpGameScreen(Grid backendGrid, StringProperty dataError);

  /**
   * Creates scene when game is lost, sets on stage
   */
  void setUpLossScreen();

  /**
   * Creates screen when level of game is won, sets on stage
   */
  void setUpWonLevelScreen();

  /**
   * Creates screen when whole game is won, sets on stage
   */
  void setUpWonGameScreen();

  /**
   *
   */
  void setUpCustomView();

  /**
   *
   */
  void setUpLeaderBoardScreen();

  /**
   *
   */
  void setUpMakeNewGameScreenImages();

  /**
   *
   */
  void setUpMakeNewGameScreenOne();

  /**
   *
   */
  void setUpMakeNewGameScreenTwo();

  /**
   *
   */
  void setUpMakeNewGameScreenThree();

  /**
   *
   * @return
   */
  Map<String,String> getUserMadeEngineAttributesMap();

  /**
   *
   * @return
   */
  Map<String, String> getUserMadeGameAttributesMap();

  /**
   *
   * @return
   */
  int[][] getUserDefinedInitialStates();

  /**
   * Sets the event to start the user define game
   * @param event the event to do
   */
  void setUserMadeStartButton(EventHandler<ActionEvent> event);

  /**
   * Starts the user created game and sets game type to title and sets the images
   */
  void startUserDefinedGame();

  /**
   *
   * @param newWindowAction
   */
  void setNewWindow(EventHandler<ActionEvent> newWindowAction);

  /**
   *
   * @return
   */
  EventHandler<ActionEvent> getNewWindowEvent();

  /**
   *
   * @param userLogin
   */
  void setUserLogin(UserLogin userLogin);

  /**
   *
   * @return
   */
  UserLogin getMyUserLogin();

  /**
   *
   * @param userLogin
   */
  void setNewLogin(UserLogin userLogin);

  /**
   *
   * @return
   */
  UserLogin getMyNewUserLogin();

  /**
   *
   * @param event
   */
  void setStartGameButton(EventHandler<ActionEvent> event);

  /**
   *
   * @return
   */
  EventHandler<ActionEvent> getStartGameButtonEvent();

  /**
   *
   * @param event
   */
  void setResetGameButton(EventHandler<ActionEvent> event);

  /**
   *
   * @return
   */
  EventHandler<ActionEvent> getResetGameButtonEvent();

  /**
   *
   * @param event
   */
  void setResetLevelButton(EventHandler<ActionEvent> event);

  /**
   *
   * @return
   */
  EventHandler<ActionEvent> getResetLevelButtonEvent();

  /**
   *
   * @param event
   */
  void setSaveButton(EventHandler<ActionEvent> event);

  /**
   *
   * @return
   */
  EventHandler<ActionEvent> getSaveButtonEvent();

  /**
   *
   * @param event
   */
  void setNextLevel(EventHandler<ActionEvent> event);

  /**
   *
   * @return
   */
  EventHandler<ActionEvent> getNextLevelEvent();

  /**
   *
   * @param thisUserProfile
   */
  void setUserProfile(UserProfile thisUserProfile);

  /**
   *
   * @return
   */
  UserProfile getMyUserProfile();

  /**
   *
   * @param event
   */
  void setHighScoreMap(Map<String, Integer> event);

  /**
   *
   * @return
   */
  Map<String, Integer> getHighScoreMap();

  /**
   * Changes css style of all screens
   * @param modeType
   */
  void setMode(String modeType);

  /**
   * An instance variable gameType is set based off of what the  game chosen to play was
   */
  void setGameType(String type);

  /**
   * An instance variable String gameType is set as the name of the game type being currently played.
   * The String is returned in this method.
   * @return current game string
   */
  String getGameType();

  /**
   * Sets the username to be the current username of the player playing
   * @param username is the current username of the player
   */
  void setUsername(String username);

  /**
   * When a player creates a new profile, their username is saved as a String instance variable playerUsername.
   * The String is returned in this method.
   * @return current username of player
   */
  String getUsername();

  /**
   * Sets the error message for if there was an incorrect username or such
   * @param errorMessage the message that is to be displayed
   */
  void setErrorMessage(StringProperty errorMessage);
}
