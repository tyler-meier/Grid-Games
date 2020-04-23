package ooga.player;

import java.util.Map;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import ooga.controller.UserLogin;
import ooga.data.UserProfile;
import ooga.engine.Grid;
import ooga.player.exceptions.ImageNotFoundException;

public interface PlayerStart {

  /**
   * Gets saved display preference from user profile, and if there is no
   * saved profile, return default
   * @return String of preferred theme
   */
  String getStyle();

  /**
   * Create login screen and set on stage
   */
  void setUpLoginScreen();

  /**
   * Creates the screen for creating a new profile
   * @param dataError the error message to be displayed if an error occurs
   */
  void setUpNewProfScreen(StringProperty dataError);

  /**
   * Creates the start/home screen for a user
   * @param dataError the error message to be displayed if an error occurs
   */
  void setUpStartScreen(StringProperty dataError);

  /**
   * Creates the game screen where games are played
   * @param backendGrid the actual grid to be changed into gridPane and images to be displayed
   * @param dataError the error message to be displayed if an error occurs
   */
  void setUpGameScreen(Grid backendGrid, StringProperty dataError) throws ImageNotFoundException;

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
   * Sets up the preferences page to change the styling of the screen
   */
  void setUpCustomView();

  /**
   * Creates the leader board screen/high score screens
   */
  void setUpLeaderBoardScreen();

  /**
   * Creates the first screen for making new games where you choose images to use
   */
  void setUpMakeNewGameScreenImages();

  /**
   * Creates the second scene in making new games where you
   * start choosing attributes for the new game
   */
  void setUpMakeNewGameScreenOne();

  /**
   * Creates the third scene in making new games where you
   * start choosing attributes for the new game
   */
  void setUpMakeNewGameScreenTwo();

  /**
   * Creates the fourth and final scene in making new games where you
   * start choosing attributes for the new game
   */
  void setUpMakeNewGameScreenThree();

  /**
   * @return map of the selected engine attributes a user chose for their game
   */
  Map<String,String> getUserMadeEngineAttributesMap();

  /**
   * @return map of the specific game attributes the user chose for their game
   */
  Map<String, String> getUserMadeGameAttributesMap();

  /**
   * @return a 2D array of the initial states for the game grid
   */
  int[][] getUserDefinedInitialStates();

  /**
   * Sets the event to start the user defined game
   * @param event the event to do
   */
  void setUserMadeStartButton(EventHandler<ActionEvent> event);

  /**
   * Starts the user created game and sets game type to title and sets the images
   */
  void startUserDefinedGame();

  /**
   * Sets action to create a new window to play
   * @param newWindowAction the action
   */
  void setNewWindow(EventHandler<ActionEvent> newWindowAction);

  /**
   * @return the event to create a new window
   */
  EventHandler<ActionEvent> getNewWindowEvent();

  /**
   * Sets an already created userLogin with the information needed
   * @param userLogin the specific userLogin
   */
  void setUserLogin(UserLogin userLogin);

  /**
   * @return the currently set userLogin with info
   */
  UserLogin getMyUserLogin();

  /**
   * Sets a new userLogin when creating one
   * @param userLogin the specific new userLogin
   */
  void setNewLogin(UserLogin userLogin);

  /**
   * @return the new userLogin that was created and all of its information
   */
  UserLogin getMyNewUserLogin();

  /**
   * Sets action for what happens when start button is clicked
   * @param event
   */
  void setStartGameButton(EventHandler<ActionEvent> event);

  /**
   * @return the event that happens when start button is clicked
   */
  EventHandler<ActionEvent> getStartGameButtonEvent();

  /**
   * Sets action for what happens when reset game button is pressed
   * @param event
   */
  void setResetGameButton(EventHandler<ActionEvent> event);

  /**
   * @return the event for resetting a game
   */
  EventHandler<ActionEvent> getResetGameButtonEvent();

  /**
   * Sets action for what happens when reset level button is pressed
   * @param event
   */
  void setResetLevelButton(EventHandler<ActionEvent> event);

  /**
   * @return the event for resetting the level of a game
   */
  EventHandler<ActionEvent> getResetLevelButtonEvent();

  /**
   * Sets action for what happens when save button is pressed
   * @param event
   */
  void setSaveButton(EventHandler<ActionEvent> event);

  /**
   * @return the event that takes place when save button is pressed
   */
  EventHandler<ActionEvent> getSaveButtonEvent();

  /**
   * Sets action for when next level button is pressed
   * @param event
   */
  void setNextLevel(EventHandler<ActionEvent> event);

  /**
   * @return the event for pressing next level/proceed
   */
  EventHandler<ActionEvent> getNextLevelEvent();

  /**
   * Sets the userProfile to the current profile that is being played on
   * @param thisUserProfile current profile
   */
  void setUserProfile(UserProfile thisUserProfile);

  /**
   * @return the current profile of the user playing
   */
  UserProfile getMyUserProfile();

  /**
   * Sets the map of all users that have high scores for a game
   * @param map the being set to
   */
  void setHighScoreMap(Map<String, Integer> event);

  /**
   * @return the high score map of users and high scores
   */
  Map<String, Integer> getHighScoreMap();

  /**
   * Changes css style of all screens
   * @param modeType
   */
  void setMode(String modeType);

  /**
   * An instance variable gameType is set based off of what the  game chosen to play was
   * @param type the current game chosen to  be played
   */
  void setGameType(String type);

  /**
   * @return a string of the current game being played/accessed
   */
  String getGameType();

  /**
   * Sets the username to be the current username of the player playing
   * @param username is the current username of the player
   */
  void setUsername(String username);

  /**
   * @return the current username of the player
   */
  String getUsername();

  /**
   * Sets the error message for if there was an incorrect username or such
   * @param errorMessage the message that is to be displayed
   */
  void setErrorMessage(StringProperty errorMessage);
}
