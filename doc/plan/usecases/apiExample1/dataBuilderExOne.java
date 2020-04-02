package ooga.data;

import javafx.beans.property.StringProperty;

/**
 * The purpose of this class is to hold all the methods which need to be accessed
 * by the Controller. This class will not perform all the work, but delegate
 * functions to other objects, such as a Parser and DataObject.
 */
public class Data implements DataBuilder {

  StringProperty loginMessage;
  DataObject currentEngineAttributes;
  /**
   * Uses the username and password to loop through the existing profiles.
   * These values are contained in a ResourceBundle. If a username and matching
   * password are found, the DataObject representing that player is returned. If
   * the username or password in invalid, the method changes the bidirectionally
   * binded loginMessage to give helpful user feedback.
   * @param username
   * @param password
   * @return
   */
  @Override
  public DataObject getPlayerProfile(String username, String password) {
    return null;
  }

  /**
   * Checks the given username other usernames contained in the ResourceBundle.
   * If another profile exists with that username, the loginMessage is changed
   * to give a helpful message to change the username. If not, the username and
   * password are written to the ResourceBundle.
   * @param username
   * @param password
   */
  @Override
  public void saveNewPlayerProfile(String username, String password) {

  }

  /**
   * Returns the current DataObject of EngineAttributes which have been set
   * according to whatever datafile was last read. This DataObject can contain
   * whatever you want, you just must specify that within the DataObject
   * @return
   */
  @Override
  public DataObject getEngineAttributes() {
    return null;
  }

  /**
   * Given a profile and the current engine attributes, the method will
   * translate that object into Strings, utilize a parser to create a new XML
   * document and then save the path of that document to the profile of that user
   * @param username
   * @param engineAttributes
   */
  @Override
  public void saveConfigurationFile(String username, DataObject engineAttributes) {

  }

  /**
   * Given a profile and the type of game, this method will go into that
   * profile and grab the path of where the last saved value of that game exists.
   * Then, it will utilize a Parser to create a data object and return
   * the configuration of the game so it can be played. Within this,
   * currentEngineAttributes is set.
   * @param username
   * @param gameType
   * @return
   */
  @Override
  public DataObject loadPreviousGame(String username, String gameType) {
    return null;
  }

  /**
   * Similar to the above method but does not have to go into a profile to get
   * the needed path. In this case, the level 1 paths for each game exists in
   * a ResourceBundle. After grabbing the matching path to the given gametype, this
   * method uses the Parser to get the needed attributes just like the above method.
   * Within this, currentEngineAttributes is set.
   * @param gameType
   * @return
   */
  @Override
  public DataObject loadConfigurationFile(String gameType) {
    return null;
  }
}