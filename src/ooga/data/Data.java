package ooga.data;

import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ooga.data.buildingXML.XMLBuilder;
import ooga.data.buildingXML.XMLGameBuilder;
import ooga.data.exceptions.EmptyEntryException;
import ooga.data.exceptions.IncorrectPasswordException;
import ooga.data.exceptions.InvalidCharacterEntryException;
import ooga.data.exceptions.NaughtyNameException;
import ooga.data.exceptions.NoUserExistsException;
import ooga.data.exceptions.UserAlreadyExistsException;

/**
 * The purpose of this class is to hold all the methods which need to be accessed
 * by the Controller. This class will not perform all the work, but delegate
 * functions to other objects, such as a Parser and DataObject.
 */
public class Data implements DataLink {

  private final String ENGINE_KEY_PATH = "resources.EngineKeys";
  private final String GAME_KEY_PATH = "resources.GameKeys";
  private final String DEFAULT_GAMES_PATH = "resources.DefaultGamePaths";
  private final String DEFAULT_ENGINE_PATH = "resources.DefaultEnginePaths";
  private final String GUEST_USER = "Guest";
  private final String MAIN_GAME_TAG = "game";
  private final String NEW_GAME_PATH_SKELETON = "data/profiles/%s%s.xml";
  private final String LEVEL_PATH_SKELETON = "data/defaultGames/%s/level_%d.xml";
  private final int LOAD_SAVED_GAME = -1;

  private final ResourceBundle myEngineResource = ResourceBundle.getBundle(ENGINE_KEY_PATH);
  private final ResourceBundle myGameResource = ResourceBundle.getBundle(GAME_KEY_PATH);
  private final ResourceBundle myDefaultGamePathResource = ResourceBundle.getBundle(DEFAULT_GAMES_PATH);
  private final ResourceBundle myDefaultEnginePathResource = ResourceBundle.getBundle(DEFAULT_ENGINE_PATH);

  private String gamePath;
  private ProfileManager myProfileManager;
  private StringProperty errorMessage;
  private UserProfile currentUser;
  private String gameType;
  private XMLParser gameParser;


  public Data()
  {
      myProfileManager = new ProfileManager();
      errorMessage = new SimpleStringProperty();
  }

  public StringProperty getErrorMessage()
  {
    return errorMessage;
  }

  public void updateCurrentPlayer()
  {
    myProfileManager.updatePLayerXML(currentUser);
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
        setCurrentUser(myProfileManager.getProfile(username));
        return currentUser;
      }
    } catch (NoUserExistsException | IncorrectPasswordException e)
    {
      errorMessage.setValue(e.getMessage());
    }
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
  public UserProfile saveNewPlayerProfile(String username, String password) {
    try{
      setCurrentUser(myProfileManager.addProfile(username, password));
      return currentUser;
    }
    catch(EmptyEntryException | UserAlreadyExistsException
        | NaughtyNameException | InvalidCharacterEntryException e)
    {
      errorMessage.setValue(e.getMessage());
      System.out.println(errorMessage.get());
    }
    return null;
  }


  /**
   * Returns the current DataObject of EngineAttributes which have been set
   * according to whatever datafile was last read. This DataObject can contain
   * whatever you want, you just must specify that within the DataObject
   * @return
   */
  @Override
  public Map<String, String> getEngineAttributes(String gameType) {
    String enginePath = myDefaultEnginePathResource.getString(gameType);
    XMLParser engineParser = new XMLParser(enginePath);
    return engineParser.getMapFromXML(myEngineResource);
  }

  /**
   * Given a profile and the current engine attributes, the method will
   * translate that object into Strings, utilize a parser to create a new XML
   * document and then save the path of that document to the profile of that user
   * @param gameAttributes
   */
  @Override
  public void saveGame(Map<String, String> gameAttributes, int[][] grid, boolean[][] uncoveredCells) {
      String path = String.format(NEW_GAME_PATH_SKELETON, currentUser.getUsername(), gameType);
      XMLBuilder newGame = new XMLGameBuilder(MAIN_GAME_TAG, path, gameAttributes, grid, uncoveredCells);
      currentUser.addSavedGame(gameType, path);
      myProfileManager.updatePLayerXML(currentUser);
      errorMessage.setValue("Game Saved!");
  }

  public void saveGameLevel(Map<String, String> gameAttributes, int[][] grid, boolean[][] uncoveredCells) {
    String path = String.format(NEW_GAME_PATH_SKELETON, currentUser.getUsername(), gameType);
    XMLBuilder newGame = new XMLGameBuilder(MAIN_GAME_TAG, path, gameAttributes, grid, uncoveredCells);
    currentUser.addSavedGame(gameType, path);
    myProfileManager.updatePLayerXML(currentUser);
    errorMessage.setValue("Game Saved!");
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
    this.gameType = gameType;
    gamePath = myDefaultGamePathResource.getString(gameType);
    if(!username.equals(GUEST_USER))
    {
      gamePath = currentUser.getSavedGame(gameType);
    }
    gameParser = new XMLParser(gamePath);
    return gameParser.getMapFromXML(myGameResource);
  }

  /**
   * Gets the grid from the previously identified gamePath in getGameAttributes
   * @return
   */
  @Override
  public int[][] getGrid()
  {
    return gameParser.getGrid();
  }

  public boolean[][] getOpenCells()
  {
    return gameParser.getUncoveredCellGrid();
  }


  public Map<String, String> getGameLevelAttributes(String username, String gameType, int level) {
    this.gameType = gameType;
    gamePath = String.format(LEVEL_PATH_SKELETON, gameType, level);
    if(!username.equals(GUEST_USER) && level == LOAD_SAVED_GAME)
    {
      gamePath = currentUser.getSavedGame(gameType);
    }
    gameParser = new XMLParser(gamePath);
    return gameParser.getMapFromXML(myGameResource);
  }



  private void setCurrentUser(UserProfile newCurrent)
  {
    currentUser = newCurrent;
    currentUser.setSaveAction(e -> myProfileManager.updatePLayerXML(currentUser));
  }


}
