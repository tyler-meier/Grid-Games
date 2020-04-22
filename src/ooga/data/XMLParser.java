package ooga.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import ooga.data.exceptions.ParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {

  private final int ZERO_INDEX = 0;
  private final int EMPTY_TAG = 0;
  private final String GRID_DELIMINATOR = " ";
  private final String ROW_TAG = "row";
  private final String ZERO_DEFAULT_VALUE= "0";
  private final String NUM_ROWS_TAG= "numRows";
  private final String NUM_COLUMNS_TAG= "numColumns";
  private final String UNCOVERED_CELL_TAG = "uncoveredCellRow";

  private Document doc;

  public XMLParser(String path) {
    try {
      File file = new File(path);
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();

      doc = db.parse(file);
      doc.getDocumentElement().normalize();
    } catch (Exception e) {
      ParserException error = new ParserException(e, path);
      //System.out.println(error.getMessage());
    }
  }

  /**
   * Gets a map based on the keys specified in the given
   * resource bundle
   * @param keysAndDefaults
   * @return
   */
  public Map<String, String> getMapFromXML(ResourceBundle keysAndDefaults) {
    Map<String, String> mapToFill = new HashMap<>();
    for (String key : keysAndDefaults.keySet()) {
      mapToFill.put(key, getStringElementByTag(key, keysAndDefaults.getString(key)));
    }
    return mapToFill;
  }

  /**
   * Allows us to get a list of all the values
   * with the same tag name
   * @param tagName
   * @param defaultVal
   * @return
   */
  public List<String> getListFromXML(String tagName, String defaultVal) {
    return getAllInstances(tagName, defaultVal);
  }

  /**
   * Allows us to get a boolean element specified by the tag
   * @param tagName
   * @param defaultVal
   * @return
   */
  public boolean getBooleanElementByTag(String tagName, String defaultVal)
  {
    return Boolean.parseBoolean(getStringElementByTag(tagName, defaultVal));
  }

  /**
   * Allows us to get an integer that was encoded as a string
   * @param tagName
   * @param defaultVal
   * @return
   */
  public int getIntegerElementByTag(String tagName, String defaultVal)
  {
    return Integer.parseInt(getStringElementByTag(tagName, defaultVal));
  }

  /**
   *
   * @return
   */
  public int[][] getGrid()
  {
    int numRows = getIntegerElementByTag(NUM_ROWS_TAG, ZERO_DEFAULT_VALUE);
    int numCols = getIntegerElementByTag(NUM_COLUMNS_TAG, ZERO_DEFAULT_VALUE);
    int[][] grid = new int[numRows][numCols];

    NodeList nodeList = doc.getElementsByTagName(ROW_TAG);

    for (int r = ZERO_INDEX; r < nodeList.getLength(); r++)
    {
      Node node = nodeList.item(r);
      try{
        String [] states = node.getTextContent().split(GRID_DELIMINATOR);
        for(int c = ZERO_INDEX; c < numCols; c++)
        {
          grid[r][c] = Integer.parseInt(states[c]);
        }
      }
      catch(Exception e)
      {
        throw new ParserException(e, ROW_TAG);
      }
    }
    return grid;
  }

  public boolean[][] getUncoveredCellGrid()
  {

    int numRows = getIntegerElementByTag(NUM_ROWS_TAG, ZERO_DEFAULT_VALUE);
    int numCols = getIntegerElementByTag(NUM_COLUMNS_TAG, ZERO_DEFAULT_VALUE);
    boolean[][] grid = new boolean[numRows][numCols];

    NodeList nodeList = doc.getElementsByTagName(UNCOVERED_CELL_TAG);
    if (nodeList.getLength()==0) return null;

    for (int r = ZERO_INDEX; r < nodeList.getLength(); r++)
    {
      Node node = nodeList.item(r);
      try{
        String [] states = node.getTextContent().split(GRID_DELIMINATOR);
        for(int c = ZERO_INDEX; c < numCols; c++)
        {
          grid[r][c] = Boolean.parseBoolean(states[c]);
        }
      }
      catch(Exception e)
      {
        return null;
      }
    }
    return grid;

  }


  /**
   * Gets the string within tag tagName
   * @param tagName
   * @return
   */
  public String getStringElementByTag(String tagName, String defaultVal)
  {
    if(doc.getElementsByTagName(tagName).getLength() == EMPTY_TAG)
    {
      return defaultVal;
    }
    String ret = doc.getElementsByTagName(tagName).item(ZERO_INDEX).getTextContent();
    if(ret.isEmpty())
    {
      return defaultVal;
    }
    return ret;
  }

  private List<String> getAllInstances(String tagName, String defaultVal) {
    List<String> ret = new ArrayList<>();
    NodeList nodeList = doc.getElementsByTagName(tagName);
    for (int itr = ZERO_INDEX; itr < nodeList.getLength(); itr++) {
      Node node = nodeList.item(itr);
      try {
        ret.add(node.getTextContent());
      } catch (Exception e) {
        ParserException error = new ParserException(e, tagName);
        //System.out.println(error.getMessage());
        ret.add(defaultVal);
      }
    }
    if (ret.isEmpty()) {
      ret.add(defaultVal);
    }
    return ret;
  }

}

