package ooga.data;

import java.util.Map;
import javafx.beans.property.StringProperty;

/**
 * This API allows for the controller to get information for the backend parser and to relay it
 * to the engine and player ends. By using this API, the other ends do not have to have an instance
 * of Data
 */
public interface DataLink {

  /**
   * This method uses the given username and password to return the
   * profile attributes of a player in the form of a DataObject. This is a
   * very borad method that can be implemented any number of ways by
   * the Data interface object which must specify it. The object can
   * choose how it wants to loop through and match usernames and passwords
   * and return values.
   * @param username
   * @param password
   * @return
   */
  UserProfile login(String username, String password);

  /**
   * Using the given username and password, the object can make its own
   * decision about how to deal with duplicates and what kind of format
   * which it will be saving the new information. The only requirement is
   * that a username and password is saved (if valid).
   * @param username
   * @param password
   */
  UserProfile saveNewPlayerProfile(String username, String password);

  /**
   * Method utalized by the Engine which gets all the atributes from the
   * current data file. These can be read from any type of file and can
   * consist of any attributes the specific engine needs. The DataObject
   * will consist of all the attributes read as Strings which the Engine
   * can use reflection to build itself accordingly.
   * @return
   */
  Map<String, String> getEngineAttributes(String gameType);

  /**
   * Given a profile and the current state of the engine, the Data interface
   * must be able to save that information to the profile. The profile can
   * be found any number of ways and the attributes can be saved with whatever data
   * file you choose
   * @param gameAttributes
   */
  void saveGame(Map<String, String> gameAttributes, int [][] grid, boolean[][] uncoveredCells);

  /**
   * Provides that User Defined Games can be saved in order to be reloaded in the future. In addition
   * to just saving the current status of the game in savedGames and the path in the current user
   * profile, this method must save an Engine for the game and the default game configuration
   * @param newGameType
   * @param engineAttributes
   * @param gameAttributes
   * @param grid
   * @param uncoveredCells
   */
  void saveCreatedGame(String newGameType, Map<String, String> engineAttributes, Map<String, String> gameAttributes, int[][] grid, boolean[][] uncoveredCells);


  /**
   * Given a profile, level indicator and a gameType, the Data interface should be able to return
   * the last saved position of that player, a default game level or their user defined game.
   * @param username
   * @param gameType
   * @return
   */
  Map<String, String> getGameLevelAttributes(String username, String gameType, int levelIndicator);

  /**
   * Returns an array of the specified stated for the current game
   * @return
   */
  int[][] getGrid();

  /**
   * If a game has hidden cells, it will need an array of booleans to determine
   * which cells should start open
   * @return
   */
  boolean[][] getOpenCells();

  /**
   * Allows the backend error messages to be binded to anywhere else in the
   * program so users can get helpful feedback without the other ends knowing what
   * exactly the data is catching
   * @return
   */
  StringProperty getErrorMessage();

  /**
   * Returns a map of users with saved high scores fot that game and is sorted so
   * that the first users in the map has the highest score. This is used to create
   * a leader board in the frontend
   * @param gameType
   * @return
   */
  Map<String, Integer> getHighScores(String gameType);
}