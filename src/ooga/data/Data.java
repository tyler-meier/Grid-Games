package ooga.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ooga.data.buildingXML.XMLBuilder;
import ooga.data.buildingXML.XMLEngineBuilder;
import ooga.data.buildingXML.XMLGameBuilder;
import ooga.data.exceptions.EmptyEntryException;
import ooga.data.exceptions.IncorrectPasswordException;
import ooga.data.exceptions.InvalidCharacterEntryException;
import ooga.data.exceptions.LevelNotFoundException;
import ooga.data.exceptions.NaughtyNameException;
import ooga.data.exceptions.NoUserExistsException;
import ooga.data.exceptions.ParserException;
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
  private final String MAIN_ENGINE_TAG = "engine";
  private final String NEW_GAME_PATH_SKELETON = "data/savedGames/%s%s.xml";
  private final String DEFAULT_CREATED_GAME_PATH_SKELETON = "data/defaultGames/%s%s.xml";
  private final String USER_DEFINED_ENGINE_PATH_SKELETON = "data/userDefinedEngines/%s%s.xml";
  private final String LEVEL_PATH_SKELETON = "data/defaultGames/%s/level_%d.xml";
  private final String GAME_SAVED_FEEDBACK = "Game Saved!";

  private final int LOAD_SAVED_GAME = -1;
  private final int LEVEL_ONE = 1;
  private final List<String> KNOWN_GAME_TYPES = new ArrayList<>(List.of("Memory", "BejeweledEndless", "BejeweledAction", "BejeweledPuzzle", "CandyCrush", "ClassicMemory", "Minesweeper"));

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

  /**
   * Allows us to bind the Data and PLayer error messages so the
   * backend Data can give helpful user feedback
   * @return
   */
  @Override
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
    String enginePath;
    if(!KNOWN_GAME_TYPES.contains(gameType))
    {
      enginePath = String.format(USER_DEFINED_ENGINE_PATH_SKELETON, currentUser.getUsername(), gameType);
    }
    else{
      enginePath = myDefaultEnginePathResource.getString(gameType);
    }
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
      XMLBuilder newSavedGame = new XMLGameBuilder(MAIN_GAME_TAG, path, gameAttributes, grid, uncoveredCells);
      currentUser.addSavedGame(gameType, path);
      myProfileManager.updatePLayerXML(currentUser);
      errorMessage.setValue(GAME_SAVED_FEEDBACK);
  }

  /**
   * Allows user defined games to be saved. In addition to saving the game and the path, a user
   * defined game needs to save a new engine and save a new default game config
   * @param newGameType
   * @param engineAttributes
   * @param gameAttributes
   * @param grid
   * @param uncoveredCells
   */
  @Override
  public void saveCreatedGame(String newGameType, Map<String, String> engineAttributes, Map<String, String> gameAttributes, int[][] grid, boolean[][] uncoveredCells)
  {
      gameType = newGameType;
      saveEngineToXML(engineAttributes);
      saveGame(gameAttributes, grid, uncoveredCells);
      String path = String.format(DEFAULT_CREATED_GAME_PATH_SKELETON, currentUser.getUsername(), gameType);
      XMLBuilder newDefaultGame = new XMLGameBuilder(MAIN_GAME_TAG, path, gameAttributes, grid, uncoveredCells);
  }


  /**
   * Gets the grid of states from the previously identified gamePath in getGameAttributes
   * @return
   */
  @Override
  public int[][] getGrid()
  {
    return gameParser.getGrid();
  }

  /**
   * Get grid of which cells are hidden, will return null if it is
   * a game without hidden cells
   * @return
   */
  @Override
  public boolean[][] getOpenCells()
  {
    return gameParser.getUncoveredCellGrid();
  }

  /**
   * Gets the game attribute map. Works for loading a saved game, both resets, loading a default game level
   * and loading a user defined game
   * @param username
   * @param gameType
   * @param level
   * @return
   * @throws LevelNotFoundException
   */
  @Override
  public Map<String, String> getGameLevelAttributes(String username, String gameType, int level) throws LevelNotFoundException{
    this.gameType = gameType;
    gamePath = String.format(LEVEL_PATH_SKELETON, gameType, level);
    if(!KNOWN_GAME_TYPES.contains(gameType)  && username.equals(GUEST_USER))
    {
      gamePath = String.format(DEFAULT_CREATED_GAME_PATH_SKELETON, currentUser.getUsername(), gameType);
    }
    else if(!KNOWN_GAME_TYPES.contains(gameType) | (!username.equals(GUEST_USER) && level == LOAD_SAVED_GAME))
    {
      gamePath = currentUser.getSavedGame(gameType);
    }
    else if(level == LOAD_SAVED_GAME)
    {
      gamePath = String.format(LEVEL_PATH_SKELETON, gameType, LEVEL_ONE);
    }
    try{
      gameParser = new XMLParser(gamePath);
      return gameParser.getMapFromXML(myGameResource);
    } catch(Exception e)
    {
      throw new LevelNotFoundException(e, level, gameType);
    }
  }

  /**
   * Used to create leader board for given game in frontend
   * @param gameType
   * @return
   */
  @Override
  public Map<String, Integer> getHighScores(String gameType)
  {
    return myProfileManager.getHighScores(gameType);
  }

  /**
   * Current user is set whenever you log in and adds a listener to
   * always update the player XML whenever it is changed
   * @param newCurrent
   */
  private void setCurrentUser(UserProfile newCurrent)
  {
    currentUser = newCurrent;
    currentUser.setSaveAction(e -> myProfileManager.updatePLayerXML(currentUser));
  }

  private void saveEngineToXML(Map<String, String> engineAttributes)
  {
    String newEnginePath = String.format(USER_DEFINED_ENGINE_PATH_SKELETON, currentUser.getUsername(), gameType);
    XMLBuilder newEngine = new XMLEngineBuilder(MAIN_ENGINE_TAG, newEnginePath, engineAttributes);
  }

}
