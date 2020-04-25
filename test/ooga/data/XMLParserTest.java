package ooga.data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import ooga.data.ProfileManager;
import ooga.data.XMLParser;
import org.junit.jupiter.api.Test;

class XMLParserTest {

  private static final String MEMORY_GRID = "memoryGrid";
  private final String ENGINE_PATH = "resources.EngineKeys";
  private final String GAME_PATH = "resources.GameKeys";
  private final String DEFAULT_GAMES_PATH = "resources.DefaultGamePaths";
  private final String [] knownProfiles = new String[]{"todd34", "bobbyBoy"};
  private final String DEFAULT_ENGINE_PATH = "resources.DefaultEnginePaths";
  private final String MEMORY_GAME_TYPE = "Memory";
  private final String PROFILE_TAG = "profile";
  private final String DELIMINATOR = " ";


  private final ResourceBundle myEngineResource = ResourceBundle.getBundle(ENGINE_PATH);
  private Map<String, String> engine = new HashMap<>();

  private final ResourceBundle myGameResource = ResourceBundle.getBundle(GAME_PATH);
  private Map<String, String> game = new HashMap<>();

  private final ResourceBundle myDefaultGamePathResource = ResourceBundle.getBundle(DEFAULT_GAMES_PATH);
  private final ResourceBundle myDefaultEnginePathResource = ResourceBundle.getBundle(DEFAULT_ENGINE_PATH);


  private final String memory_engine_path = "data/defaultEngines/MemoryEngine.xml";
  private final String memory_game_path = "data/defaultGames/Memory/level_1.xml";
  private final String profile_path = "data/RegisteredProfiles.xml";

  private XMLParser parser;
  private TextParser tx = new TextParser();


  @Test
  void testGetMapFromXMLEngine()
  {
    parser = new XMLParser(memory_engine_path);
    engine = parser.getMapFromXML(myEngineResource);

    for(String desiredKey : Collections.list(myEngineResource.getKeys()))
    {
      assertTrue(engine.keySet().contains(desiredKey));
    }

  }

  @Test
  void getMapFromXMLProfileList()
  {
    parser = new XMLParser(profile_path);
    Map<String, List<String>> allProfiles = new HashMap<>();
    for (String user : parser.getListFromXML(PROFILE_TAG, null)) {
      String[] neededParts = user.split(DELIMINATOR);
      String currUsername = neededParts[0];
      String currPassword = neededParts[1];
      allProfiles.put(currUsername, new ArrayList<>());
      allProfiles.get(currUsername).add(currPassword);
    }

    for(String person: Arrays.asList(knownProfiles))
    {
      assertTrue(allProfiles.containsKey(person));
    }

  }

  @Test
  void testGetMapFromXMLGame()
  {
    parser = new XMLParser(memory_game_path);
    game = parser.getMapFromXML(myGameResource);

    for(String desiredKey : Collections.list(myGameResource.getKeys()))
    {
      assertTrue(game.keySet().contains(desiredKey));
    }

  }

  @Test
  void checkEngineResourcePaths()
  {
    for(String game: Collections.list(myDefaultEnginePathResource.getKeys()))
    {
      XMLParser parser = new XMLParser(myDefaultEnginePathResource.getString(game));
    }
  }

  @Test
  void checkDefaultGameResourcePaths()
  {
    for(String game: Collections.list(myDefaultGamePathResource.getKeys()))
    {
      XMLParser parser = new XMLParser(myDefaultGamePathResource.getString(game));
    }
  }



  @Test
  void testGetGrid()
  {
    String savedPath = myDefaultGamePathResource.getString(MEMORY_GAME_TYPE);

    XMLParser gridParser = new XMLParser(savedPath);
    int [][] grid = gridParser.getGrid();

    tx.setFileGroup(MEMORY_GRID);
    int[][] stateGrid = tx.getStateGrid();

    assertTrue(checkIntGridEquality(grid, stateGrid));
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

}