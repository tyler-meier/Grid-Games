package ooga.data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import org.junit.jupiter.api.Test;

class DataTest {

  private final String GAME_PATH = "resources.GameKeys";
  private final ResourceBundle myGameResource = ResourceBundle.getBundle(GAME_PATH);
  private final String OUTPUT_SKELETON = "%s vs. %s failed at row: %d column %d";
  private final String KEY_FILLER = "Whatever";
  private final String NEW_GAME_PATH_SKELETON = "data/profiles/%s%s.xml";

  private final int[][] knownLevelOne = { {1, 5, 6, 6, 1}, {1, 5, 4, 3, 6}, {2, 4, 3, 4, 1}, {1, 5, 2, 4, 1}, {2, 4, 3, 2, 2}, {2, 3, 4, 3, 3}};
  private final int[][] knownLevelTwo = { {1, 6, 5, 4, 2}, {5, 1, 3, 1, 5}, {4, 4, 6, 6, 1}, {2, 3, 4, 3, 2}, {1, 1, 2, 1, 2}};
  private final int[][] knownLevelThree = { {1, 6, 5, 4, 2, 1}, {5, 1, 3, 1, 5, 1}, {4, 4, 6, 6, 1, 2}, {2, 3, 4, 3, 2, 1}, {2, 3, 4, 3, 2, 1}};
  private final int[][] knownLevelFour = { {1, 6, 5, 4, 2, 3}, {5, 1, 3, 1, 5, 1}, {4, 4, 6, 6, 1, 6}, {2, 3, 4, 3, 2, 4}, {2, 1, 3, 4, 5, 4}, {6, 6, 1, 3, 2, 5}};

  private final List<int[][]> allGrids = new ArrayList<>();


  private Data data = new Data();

//  @Test
//  void saveGame() {
//    int[][] grid = new int [4][4];
//    Map<String, String > dataToWrite = new HashMap<>();
//    fillMapAndGrid(grid, dataToWrite);
//    String path = String.format(NEW_GAME_PATH_SKELETON, "jay18", "Memory");
//    data.saveGame(path, dataToWrite, grid);
//
//    XMLParser printingParser = new XMLParser(path);
//    assertEqualsGrids(grid, printingParser.getGrid());
//    assertEqualsMaps(dataToWrite, printingParser.getMapFromXML(myGameResource));
//  }

  @Test
  void loadLevels()
  {
    allGrids.add(knownLevelOne);
    allGrids.add(knownLevelTwo);
    allGrids.add(knownLevelThree);
    allGrids.add(knownLevelFour);

    for(int[][] grid : allGrids)
    {
      Map<String,String> gameLevelAttributes = data.getGameLevelAttributes("Guest", "BejeweledPuzzle", allGrids.indexOf(grid)+1);
      int[][] currentLevelGrid = data.getGrid();
      assertEquals(true, checkGridEquality(grid, currentLevelGrid));
    }

    Map<String,String> gameLevelAttributes = data.getGameLevelAttributes("Guest", "BejeweledPuzzle", 5);


  }


  @Test
  void editProfileTest()
  {
    UserProfile jay = data.login("jay18", "boob");
    jay.addHighScore("Memory", 55);

    UserProfile tyler = data.login("tylerm", "yown");
    tyler.addHighScore("Memory", 4);
    tyler.setDarkMode(true);
    tyler.setParentalControls(true);

    UserProfile juju = data.login("juju", "123");
    assertEquals(null, juju);

  }

  private void assertEqualsMaps(Map<String, String> dataToWrite, Map<String, String> mapFromXML) {
    for(String key: dataToWrite.keySet())
    {
      assertEquals(true, mapFromXML.containsKey(key));
      assertEquals(dataToWrite.get(key), mapFromXML.get(key));
      System.out.println(String.format(OUTPUT_SKELETON, dataToWrite.get(key), mapFromXML.get(key)));
    }
  }

  private boolean checkGridEquality(int[][] grid, int[][] readingGrid) {
    assertEquals(grid.length, readingGrid.length);
    assertEquals(grid[0].length, readingGrid[0].length);

    for(int r = 0; r < grid.length; r ++)
    {
      for(int c = 0; c < grid[0].length; c++)
      {
        if(grid[r][c] != readingGrid[r][c])
        {
          System.out.println(String.format(OUTPUT_SKELETON, grid[r][c], readingGrid[r][c], r, c));
          return false;
        }
      }
    }
    return true;
  }

  private void fillMapAndGrid(int [][] grid, Map<String, String> data)
  {
    for(int i = 0; i < grid.length; i ++)
    {
      for(int a = 0; a < grid[0].length; a++)
      {
        grid[i][a] = i+a;
      }
    }
    for(String key: myGameResource.keySet())
    {
      data.put(key, KEY_FILLER);
    }
  }
}