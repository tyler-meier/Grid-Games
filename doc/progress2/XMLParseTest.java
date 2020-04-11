package data;

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
  private final String PROFILE_PATH = "resources.ProfileKeys";
  private final String GRID_PATH = "resources.GridKeys";
  private final String ENGINE_PATH = "resources.EngineKeys";
  private final String GAME_PATH = "resources.GameKeys";
  private final String DEFAULT_GAMES_PATH = "resources.DefaultGamePaths";
  private final String [] knownProfiles = new String[]{"todd34", "bobbyBoy"};
  private final int[][] knownGrid = new int[4][5];
  private final String DEFAULT_ENGINE_PATH = "resources.DefaultEnginePaths";


  private final ResourceBundle myProfileResource = ResourceBundle.getBundle(PROFILE_PATH);
  private Map<String, String> profile = new HashMap<>();

  private final ResourceBundle myGridResource = ResourceBundle.getBundle(GRID_PATH);
  private Map<String, String> grid = new HashMap<>();

  private final ResourceBundle myEngineResource = ResourceBundle.getBundle(ENGINE_PATH);
  private Map<String, String> engine = new HashMap<>();

  private final ResourceBundle myGameResource = ResourceBundle.getBundle(GAME_PATH);
  private Map<String, String> game = new HashMap<>();

  private final ResourceBundle myDefaultGamePathResource = ResourceBundle.getBundle(DEFAULT_GAMES_PATH);
  private final ResourceBundle myDefaultEnginePathResource = ResourceBundle.getBundle(DEFAULT_ENGINE_PATH);


  private final String memory_engine_path = "data/defaultEngines/MemoryEngine.xml";
  private final String memory_game_path = "data/defaultGames/MemoryGameDefault.xml";
  private final String profile_path = "data/RegisteredProfiles.xml";

  private XMLParser parser;
  private ProfileManager pm = new ProfileManager();


  @Test
  void testGetMapFromXMLEngine()
  {
    parser = new XMLParser(memory_engine_path);
    engine = parser.getMapFromXML(myEngineResource);

    for(String desiredKey : Collections.list(myEngineResource.getKeys()))
    {
      assertEquals(true, engine.keySet().contains(desiredKey));
    }
    for (String key : engine.keySet()) {
      System.out.println(key + ": " + engine.get(key));
    }

  }

  @Test
  void getMapFromXMLProfileList()
  {
    parser = new XMLParser(profile_path);
    Map<String, List<String>> allProfiles = new HashMap<>();
    for (String user : parser.getListFromXML("profile", null)) {
      String[] neededParts = user.split(" ");
      String currUsername = neededParts[0];
      String currPassword = neededParts[1];
      allProfiles.put(currUsername, new ArrayList<>());
      allProfiles.get(currUsername).add(currPassword);
    }

    for(String person: Arrays.asList(knownProfiles))
    {
      assertEquals(true, allProfiles.containsKey(person));
    }

    for (String key : allProfiles.keySet()) {
      System.out.println(key + ": " + allProfiles.get(key));
    }

  }

  @Test
  void testGetMapFromXMLGame()
  {
    parser = new XMLParser(memory_game_path);
    game = parser.getMapFromXML(myGameResource);

    for(String desiredKey : Collections.list(myGameResource.getKeys()))
    {
      assertEquals(true, game.keySet().contains(desiredKey));
    }
    for (String key : game.keySet()) {
      System.out.println(key + ": " + game.get(key));
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


  /*
  @Test
  void testGetGrid()
  {
    UserProfile currentUser = pm.getProfile("bobbyBoy");
    String gameType = "Memory";
    //TODO: How do we know which configuration to do?
    String savedPath = currentUser.getSavedGame(gameType);
    if(savedPath.isEmpty())
    {
      savedPath = myDefaultConfigPathResource.getString(gameType);
    }
    XMLParser gridParser = new XMLParser(savedPath);
    int [][] grid = gridParser.getGrid();
    for(int r = 0; r < grid.length; r++)
    {
      for(int c = 0; c < grid[0].length; c++)
      {
        System.out.print(grid[r][c] + " ");
      }
      System.out.println();
    }
  }*/

}