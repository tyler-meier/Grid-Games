package ooga.data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import org.junit.jupiter.api.Test;

class DataTest {

  private final String GAME_PATH = "resources.GameKeys";
  private final ResourceBundle myGameResource = ResourceBundle.getBundle(GAME_PATH);
  private final String OUTPUT_SKELETON = "%s vs. %s";

  private Data data = new Data();

  @Test
  void saveGame() {
    int[][] grid = new int [4][4];
    Map<String, String > dataToWrite = new HashMap<>();
    fillMapAndGrid(grid, dataToWrite);
    String path = "data/jay18CandyCrush.xml";
    data.saveGame(path, dataToWrite, grid);

    XMLParser printingParser = new XMLParser(path);
    assertEqualsGrids(grid, printingParser.getGrid());
    assertEqualsMaps(dataToWrite, printingParser.getMapFromXML(myGameResource));
  }

  private void assertEqualsMaps(Map<String, String> dataToWrite, Map<String, String> mapFromXML) {
    for(String key: dataToWrite.keySet())
    {
      assertEquals(true, mapFromXML.containsKey(key));
      assertEquals(dataToWrite.get(key), mapFromXML.get(key));
      System.out.println(String.format(OUTPUT_SKELETON, dataToWrite.get(key), mapFromXML.get(key)));
    }
  }

  private void assertEqualsGrids(int[][] grid, int[][] readingGrid) {
    assertEquals(grid.length, readingGrid.length);
    assertEquals(grid[0].length, readingGrid[0].length);

    for(int r = 0; r < grid.length; r ++)
    {
      for(int c = 0; c < grid[0].length; c++)
      {
        System.out.println(String.format(OUTPUT_SKELETON, grid[r][c], readingGrid[r][c]));
        assertEquals(grid[r][c], readingGrid[r][c]);
      }
    }
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
      data.put(key, "something");
    }
  }
}