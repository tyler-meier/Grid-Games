package ooga.player;

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
  void setResetButton(EventHandler<ActionEvent> event);

  /**
   *
   * @return
   */
  EventHandler<ActionEvent> getResetButtonEvent();

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
   * @param thisUserProfile
   */
  void setUserProfile(UserProfile thisUserProfile);

  /**
   *
   * @return
   */
  UserProfile getMyUserProfile();

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

//    /**
//   * An instance variable boolean keeps track of whether most recent progress of player is saved.
//   * The boolean is returned in this method.
//   * @return
//   */
//  boolean isGameSaved();

//  /**
//   * When a player creates a new profile, their password is saved as a String instance variable playerPassword.
//   * The String is returned in this method.
//   * @return
//   */
//  String getPassword();
//
//  /**
//   * Takes in name of XMLfile that corresponds to the progress of the player and displays view
//   * @param fileName
//   */
//  void loadProfile(String fileName);
//
//  /**
//   * Check to see if the login actually works
//   * @param username the username for the profile
//   * @param password the password of the profile
//   */
//  boolean tryLogin(String username, String password);
//
//  /**
//   * when the user wants to create a new profile
//   * @param username the username the user wants to use
//   * @param password the password the user wants to use
//   */
//  void createNewProfile(String username, String password);
//
//  /**
//   * starts a new game given the information of the default data method
//   * @param defaultData default data for the given game
//   */
//  void startNewGame(DataObject defaultData);
//
//  /**
//   * starts a agame based off of the saved data
//   * @param myData the data that is for a saved game
//   */
//  void loadSaveGame(DataObject myData);
//
//  /**
//   * sets the profile info of each player
//   */
//  void setProfileInfo();
//
//  /**
//   * gets the preferences of the user (dark mode, colors, etc)
//   */
//  void getPreferences();
//
//  /**
//   * loads the profile and the given info based off of the username chosen
//   */
//  void loadProfile();
}
