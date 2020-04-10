package ooga.data;

import java.util.List;
import java.util.Map;

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
   * @param username
   * @param engineAttributes
   */
  void saveGame(String username, Map<String, String> engineAttributes);

  /**
   * Given a profile and a gameType, the Data interface should be able to return
   * the last saved position of that player. Might be helpful to call the below
   * method to achieve this or to have them both make similar calls on the same object
   * @param username
   * @param gameType
   * @return
   */
  Map<String, String> getGameAttributes(String username, String gameType);

  /**
   *
   * @return
   */
  int[][] getGrid();

  /**
   *
   *
   */
  void updateCurrentPlayer();


}