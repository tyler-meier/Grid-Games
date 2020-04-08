package ooga.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ooga.data.buildingXML.XMLBuilder;
import ooga.data.exceptions.IncorrectPasswordException;
import ooga.data.exceptions.NoUserExistsException;

/**
 * The purpose of this class is to hold all the methods which need to be accessed
 * by the Controller. This class will not perform all the work, but delegate
 * functions to other objects, such as a Parser and DataObject.
 */
public class Data implements DataLink {

  private static final String ENGINE_PATH_SKELETON = "data/%sGameDefault.xml";
  private final String PROFILE_KEY_PATH = "resources.ProfileKeys";
  private final String GRID_KEY_PATH = "resources.GridKeys";
  private final String ENGINE_KEY_PATH = "resources.EngineKeys";
  private final String GAME_KEY_PATH = "resources.GameKeys";
  private final String DEFAULT_GAMES_PATH = "resources.DefaultGamePaths";

  private final String LIST_OF_PREVIOUS_GAMES = "previousGame";
  private final int GAME_INDEX = 0;
  private final int PATH_INDEX = 1;
  private static final String DELIMINATOR = ":";

  private final ResourceBundle myGridResource = ResourceBundle.getBundle(GRID_KEY_PATH);
  private final ResourceBundle myProfileKeyResource = ResourceBundle.getBundle(PROFILE_KEY_PATH);
  private final ResourceBundle myEngineResource = ResourceBundle.getBundle(ENGINE_KEY_PATH);
  private final ResourceBundle myGameResource = ResourceBundle.getBundle(GAME_KEY_PATH);
  private final ResourceBundle myDefaultGamePathResource = ResourceBundle.getBundle(DEFAULT_GAMES_PATH);


  private String gamePath;
  private ProfileManager myProfileManager = new ProfileManager();
  private StringProperty errorMessage = new SimpleStringProperty();
  private XMLBuilder xmlBuilder;
  private UserProfile currentUser;


  public Data()
  {

  }

  public StringProperty getErrorMessage()
  {
    return errorMessage;
  }




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
  public UserProfile login(String username, String password) {
    try{
      if(myProfileManager.isValid(username, password))
      {
        currentUser = myProfileManager.getProfile(username);
        return currentUser;
      }
    } catch (IncorrectPasswordException incorrectPassword)
    {
      errorMessage.setValue(incorrectPassword.getMessage());
    }
    catch(NoUserExistsException incorrectUsername)
    {
      errorMessage.setValue(incorrectUsername.getMessage());
    }
    return new UserProfile();
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
  public UserProfile saveNewPlayerProfile(String username, String password) {
    currentUser = myProfileManager.addProfile(username, password);
    return currentUser;
  }


  /**
   * Returns the current DataObject of EngineAttributes which have been set
   * according to whatever datafile was last read. This DataObject can contain
   * whatever you want, you just must specify that within the DataObject
   * @return
   */
  @Override
  public Map<String, String> getEngineAttributes(String gameType) {
    String enginePath = String.format(ENGINE_PATH_SKELETON, gameType);
    XMLParser gameParser = new XMLParser(enginePath);
    return gameParser.getMapFromXML(myEngineResource);
  }

  /**
   * Given a profile and the current engine attributes, the method will
   * translate that object into Strings, utilize a parser to create a new XML
   * document and then save the path of that document to the profile of that user
   * @param username
   * @param engineAttributes
   */
  @Override
  public void saveGame(String username, DataObject engineAttributes) {

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
  public Map<String, String> getGameAttributes(String username, String gameType) {
    UserProfile user = myProfileManager.getProfile(username);
    gamePath = user.getSavedGame(gameType);
    XMLParser gameParser = new XMLParser(gamePath);
    return gameParser.getMapFromXML(myGameResource);
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
  public Map<String, String> loadConfigurationFile(String gameType) {
    gamePath = myDefaultGamePathResource.getString(gameType);
    XMLParser gameParser = new XMLParser(gamePath);
    return gameParser.getMapFromXML(myGameResource);
  }

  /**
   *
   * @param user
   * @return
   */
  @Override
  public int[][] getGrid(String user, String gameType)
  {
    //TODO: How do we know which configuration to do?
    //XMLParser gridParser = new XMLParser(gridPath);
    //return gridParser.getGrid();
    return null;
  }

}
