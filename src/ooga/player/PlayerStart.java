package ooga.player;

import ooga.engine.Cell;
import ooga.data.DataObject;
public interface PlayerStart {

  /**
   * An instance variable boolean keeps track of whether most recent progress of player is saved.
   * The boolean is returned in this method.
   * @return
   */
  boolean isGameSaved();

  /**
   * An instance variable String gameType is set as the name of the game type being currently played.
   * The String is returned in this method.
   * @return
   */
  String getGameType();

  /**
   * When a player creates a new profile, their username is saved as a String instance variable playerUsername.
   * The String is returned in this method.
   * @return
   */
  String getUsername();

  /**
   * When a player creates a new profile, their password is saved as a String instance variable playerPassword.
   * The String is returned in this method.
   * @return
   */
  String getPassword();

  /**
   * 2D array of grid is taken in as parameter to generate the corresponding view of the grid.
   * @param grid
   */
  void setGrid(Cell[][] grid);

  /**
   * Takes in name of XMLfile that corresponds to the progress of the player and displays view
   * @param fileName
   */
  void loadProfile(String fileName);

  /**
   * Check to see if the login actually works
   * @param username the username for the profile
   * @param password the password of the profile
   */
  boolean tryLogin(String username, String password);

  /**
   * when the user wants to create a new profile
   * @param username the username the user wants to use
   * @param password the password the user wants to use
   */
  void createNewProfile(String username, String password);

  /**
   * starts a new game given the information of the default data method
   * @param defaultData default data for the given game
   */
  void startNewGame(DataObject defaultData);

  /**
   * starts a agame based off of the saved data
   * @param myData the data that is for a saved game
   */
  void loadSaveGame(DataObject myData);

  /**
   * sets the profile info of each player
   */
  void setProfileInfo();

  /**
   * gets the preferences of the user (dark mode, colors, etc)
   */
  void getPreferences();

  /**
   * loads the profile and the given info based off of the username chosen
   */
  void loadProfile();

  /**
   * Sets the error message for if there was an incorrect username or such
   * @param errorMessage the message that is to be displayed
   */
  void setErrorMessage(String errorMessage);


}
