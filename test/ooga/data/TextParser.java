package ooga.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextParser {

  private static final String DELIMINATOR = " ";
  private final String TEXT_PATH_SKELETON = "TestResources/dataTestingFiles/%s/%s.txt";

  private String myFileGroup;
  private File myFile;

  public TextParser()
  {

  }

  public void setFileGroup(String fileGroup)
  {
    myFileGroup = String.format(TEXT_PATH_SKELETON, fileGroup, "%s");
  }

  public int[][] getStateGrid()
  {
    setMyFile("states");
    List<ArrayList<String>> myStatesAsList = getStringListFromTxt();
    int[][] stateGrid = new int[myStatesAsList.size()][myStatesAsList.get(0).size()];
    for(int r = 0; r < stateGrid.length; r++)
    {
      for(int c = 0; c < stateGrid[0].length; c++)
      {
          stateGrid[r][c] = Integer.parseInt(myStatesAsList.get(r).get(c));
      }
    }
    return stateGrid;
  }

  public boolean[][] getBooleanGrid()
  {
    setMyFile("booleans");
    List<ArrayList<String>> myStatesAsList = getStringListFromTxt();
    boolean[][] booleanGrid = new boolean[myStatesAsList.size()][myStatesAsList.get(0).size()];
    for(int r = 0; r < booleanGrid.length; r++)
    {
      for(int c = 0; c < booleanGrid[0].length; c++)
      {
        booleanGrid[r][c] = Boolean.parseBoolean(myStatesAsList.get(r).get(c));
      }
    }
    return booleanGrid;
  }

  public Map<String, String> getEngine()
  {
    setMyFile("engine");
    return getMap();
  }

  public Map<String, String> getGameAttributes()
  {
    setMyFile("gameAttributes");
    return getMap();
  }

  public Map<String, String> getLogins(String whichLogins)
  {
    setMyFile(whichLogins);
    return getMap();
  }


  private Map<String, String> getMap()
  {
    Map<String, String> myMap = new HashMap<>();
    try{
      BufferedReader br = new BufferedReader(new FileReader(myFile));
      String st;
      while ((st = br.readLine()) != null)
      {
        String [] row = st.split(DELIMINATOR);
        myMap.put(row[0], row[1]);
      }
      return myMap;
    } catch(Exception e)
    {
      return null;
    }
  }




  private List<ArrayList<String>> getStringListFromTxt()
  {
    List<ArrayList<String>> allStateList = new ArrayList<>();

    try{
      BufferedReader br = new BufferedReader(new FileReader(myFile));
      String st;
      while ((st = br.readLine()) != null)
      {
        allStateList.add(new ArrayList<>());
        String [] row = st.split(DELIMINATOR);
        for(String state: row)
        {
          allStateList.get(allStateList.size()-1).add(state);
        }
      }
      return allStateList;
    } catch(Exception e)
    {
      return null;
    }
  }

  private void setMyFile(String whichFile)
  {
    try {
      String path = String.format(myFileGroup, whichFile);
      myFile = new File(path);
    } catch (Exception e) {
      throw e;
    }
  }

  private void printStateGrid(int[][] grid)
  {
    for(int r = 0; r < grid.length; r++)
    {
      for(int c = 0; c < grid[0].length; c++)
      {
        System.out.print(grid[r][c] + DELIMINATOR);
      }
      System.out.println();
    }
  }

  private void printBooleanGrid(boolean[][] grid)
  {
    for(int r = 0; r < grid.length; r++)
    {
      for(int c = 0; c < grid[0].length; c++)
      {
        System.out.print(grid[r][c] + DELIMINATOR);
      }
      System.out.println();
    }
  }

  private void printStringMap(Map<String, String> myMap)
  {
    for(String key: myMap.keySet())
    {
      System.out.println(key + DELIMINATOR + myMap.get(key));
    }
  }

}
