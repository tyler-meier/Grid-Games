package ooga.data;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
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
      System.out.println(error.getMessage());
    }
  }


  public Map<String, String> getMapFromXML(ResourceBundle keysAndDefaults) {
    List<String> keys = Collections.list(keysAndDefaults.getKeys());
    Map<String, String> mapToFill = new HashMap<>();
    for (String key : keys) {
      mapToFill.put(key, getStringElementByTag(key, keysAndDefaults.getString(key)));
    }
    return mapToFill;
  }

  public List<String> getListFromXML(String tagName, String defaultVal) {
    return getAllInstances(tagName, defaultVal);
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
        System.out.println(error.getMessage());
        ret.add(defaultVal);
      }
    }
    if (ret.isEmpty()) {
      ret.add(defaultVal);
    }
    return ret;
  }

  public boolean getBooleanElementByTag(String tagName, String defaultVal)
  {
    return Boolean.parseBoolean(getStringElementByTag(tagName, defaultVal));
  }

  public int getIntegerElementByTag(String tagName, String defaultVal)
  {
    return Integer.parseInt(getStringElementByTag(tagName, defaultVal));
  }

  public int[][] getGrid()
  {
    int numRows = getIntegerElementByTag("numRows", "0");
    int numCols = getIntegerElementByTag("numColumns", "0");
    int[][] grid = new int[numRows][numCols];

    String [] rows = new String [numRows];
    NodeList nodeList = doc.getElementsByTagName("row");

    // nodeList is not iterable, so we are using for loop
    for (int r = 0; r < nodeList.getLength(); r++)
    {
      Node node = nodeList.item(r);
      try{
        String [] states = node.getTextContent().split(" ");
        for(int c = 0; c < numCols; c++)
        {
          grid[r][c] = Integer.parseInt(states[c]);
        }
      }
      catch(Exception e)
      {
        throw new ParserException(e, "row");
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
    if(doc.getElementsByTagName(tagName).getLength() == 0)
    {
      return defaultVal;
    }
    String ret = doc.getElementsByTagName(tagName).item(0).getTextContent();
    if(ret.equals(""))
    {
      return defaultVal;
    }
    return ret;
  }
}

