package ooga.data;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.data.exceptions.LevelNotFoundException;
import org.junit.jupiter.api.Test;

class DataTest {

  private static final String GAME_TO_SAVE_FOLDER = "gameToSave";
  private static final String USER_DEF_GAME_TO_SAVE_FOLDER = "gameToSave";
  private static final String PROFILE_FOLDER = "profileFiles";

  private final String [] LEVELS_TO_LOAD = {"levelOne", "levelTwo", "levelThree", "levelFour", "levelFive"};
  private final String GAME_TO_LOAD = "BejeweledAction";
  private final String MEMORY_GAME = "Memory";
  private final String GUEST = "Guest";
  private final String TEST_PROFILE_USERNAME = "test";
  private final String TEST_PROFILE_PASSWORD = "profile";
  private final String NAME_OF_USER_DEF_GAME = "MyFunnyGame";

  private final String INVALID_LOGINS = "invalidLogins";
  private final String VALID_LOGINS = "validLogins";
  private final String WAVE_MODE = "wavemode";
  private final String DEFAULT = "default";

  private final String[] ALL_GAME_TYPES = {"Memory", "BejeweledEndless", "BejeweledAction", "BejeweledPuzzle", "CandyCrush", "ClassicMemory", "Minesweeper"};


  private Data data = new Data();
  private TextParser tx = new TextParser();

  @Test
  void loadLevels()
  {
    for(int i = 0; i < LEVELS_TO_LOAD.length; i++)
    {
      tx.setFileGroup(LEVELS_TO_LOAD[i]);
      Map<String, String> correctGame = tx.getGameAttributes();

      Map<String,String> gameLevelAttributes = data.getGameLevelAttributes(GUEST, GAME_TO_LOAD, i+1);
      assertEqualsMaps(correctGame, gameLevelAttributes);
      assertNull(data.getGrid());
      assertNull(data.getOpenCells());
    }

    Map<String, String> correctEngine = tx.getEngine();
    Map<String, String> engine = data.getEngineAttributes(GAME_TO_LOAD);
    assertEqualsMaps(correctEngine, engine);

    try{
      Map<String,String> gameLevelAttributes = data.getGameLevelAttributes(GUEST, GAME_TO_LOAD, 5);
    } catch(LevelNotFoundException e)
    {
      System.out.println(e.getMessage());
    }

  }

  @Test
  void saveGame() {

    data.login(TEST_PROFILE_USERNAME, TEST_PROFILE_PASSWORD);
    data.getGameLevelAttributes(TEST_PROFILE_USERNAME, GAME_TO_LOAD, 1);

    tx.setFileGroup(GAME_TO_SAVE_FOLDER);
    int[][] stateGrid = tx.getStateGrid();
    boolean[][] boolGrid = tx.getBooleanGrid();
    Map<String, String> gameAttributes = tx.getGameAttributes();

    data.saveGame(gameAttributes, stateGrid, boolGrid);

    assertEqualsMaps(gameAttributes, data.getGameLevelAttributes(TEST_PROFILE_USERNAME, GAME_TO_LOAD, -1));
    assertTrue(checkIntGridEquality(stateGrid, data.getGrid()));
    assertTrue(checkBooleanGridEquality(boolGrid, data.getOpenCells()));
  }

  @Test
  void createAndLoadNewGameType()
  {
    data.login(TEST_PROFILE_USERNAME, TEST_PROFILE_PASSWORD);
    tx.setFileGroup(USER_DEF_GAME_TO_SAVE_FOLDER);
    Map<String, String> gameAttributes = tx.getGameAttributes();
    Map<String, String> engineAttributes = tx.getEngine();
    boolean[][] uncoveredCells = tx.getBooleanGrid();
    int[][] states = tx.getStateGrid();


    data.saveCreatedGame(NAME_OF_USER_DEF_GAME, engineAttributes, gameAttributes, states, uncoveredCells);
    assertEqualsMaps(gameAttributes, data.getGameLevelAttributes(TEST_PROFILE_USERNAME, NAME_OF_USER_DEF_GAME, -1));
    assertEqualsMaps(engineAttributes, data.getEngineAttributes(NAME_OF_USER_DEF_GAME));
    assertTrue(checkIntGridEquality(states, data.getGrid()));
    assertTrue(checkBooleanGridEquality(uncoveredCells, data.getOpenCells()));
  }



  @Test
  void getHighScoresTest()
  {
    ProfileManager pm = new ProfileManager();
    for(String gameType: ALL_GAME_TYPES)
    {
      Map<String, Integer> sortedHighScores = data.getHighScores(gameType);
      List<Integer> scores = new ArrayList<>();
      for(String username: sortedHighScores.keySet())
      {
        assertNotNull(pm.getProfile(username));
        scores.add(sortedHighScores.get(username));
      }
      List<Integer> sorted = new ArrayList<>(scores);
      List<Integer> reverse = new ArrayList<>(scores);
      Collections.sort(sorted);
      Collections.reverse(reverse);
      assertEqualsLists(sorted, reverse);

    }

  }


  @Test
  void editProfileTest()
  {
    tx.setFileGroup(PROFILE_FOLDER);
    Map<String, String> validLogins = tx.getLogins(VALID_LOGINS);
    for(String username: validLogins.keySet())
    {
      UserProfile temp = data.login(username, validLogins.get(username));
      assertNotNull(temp);
      boolean previousDarkMode = temp.getDarkMode();
      boolean newDarkMode = !previousDarkMode;
      temp.setDarkMode(newDarkMode);
      String previousDisplayPrefs = temp.getDisplayPreference();
      String newDisplayPref = WAVE_MODE;
      if(!previousDisplayPrefs.equals(WAVE_MODE))
      {
        newDisplayPref = DEFAULT;
      }
      temp.setDisplayPreference(newDisplayPref);
      int newHighScore = Integer.parseInt(temp.getHighScore(MEMORY_GAME)) + 1;
      temp.addHighScore(MEMORY_GAME, newHighScore);

      data.logout();

      temp = data.login(username, validLogins.get(username));
      assertEquals(newDarkMode, temp.getDarkMode());
      assertEquals(newDisplayPref, temp.getDisplayPreference());
      assertEquals(newHighScore, Integer.parseInt(temp.getHighScore(MEMORY_GAME)));
    }

    tx.setFileGroup(PROFILE_FOLDER);
    Map<String, String> invalidLogins = tx.getLogins(INVALID_LOGINS);
    for(String username : invalidLogins.keySet())
    {
      UserProfile temp = data.login(username, invalidLogins.get(username));
      assertNull(temp);
    }

  }

  private void assertEqualsLists(List<Integer> listOne, List<Integer> listTwo)
  {
    assertEquals(listOne.size(), listTwo.size());
    for(int i = 0; i < listOne.size(); i++)
    {
      assertEquals(listOne.get(i), listTwo.get(i));
    }
  }


  private void assertEqualsMaps(Map<String, String> dataToWrite, Map<String, String> mapFromXML) {
    for(String key: dataToWrite.keySet())
    {
      assertEquals(true, mapFromXML.containsKey(key));
      assertEquals(dataToWrite.get(key), mapFromXML.get(key));
    }
  }

  private boolean checkIntGridEquality(int[][] grid, int[][] readingGrid) {
    assertEquals(grid.length, readingGrid.length);
    assertEquals(grid[0].length, readingGrid[0].length);

    for(int r = 0; r < grid.length; r ++)
    {
      for(int c = 0; c < grid[0].length; c++)
      {
        if(grid[r][c] != readingGrid[r][c])
        {
          return false;
        }
      }
    }
    return true;
  }

  private boolean checkBooleanGridEquality(boolean[][] grid, boolean[][] readingGrid) {
    assertEquals(grid.length, readingGrid.length);
    assertEquals(grid[0].length, readingGrid[0].length);

    for(int r = 0; r < grid.length; r ++)
    {
      for(int c = 0; c < grid[0].length; c++)
      {
        if(grid[r][c] != readingGrid[r][c])
        {
          return false;
        }
      }
    }
    return true;
  }

}