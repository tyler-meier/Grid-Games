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
import ooga.data.buildingXML.XMLBuilder;
import ooga.data.buildingXML.XMLGameBuilder;
import ooga.data.exceptions.ParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {

  private final int ZERO_INDEX = 0;

  private Document doc;

  /*
  private static final String PROFILE_PATH = "resources.ProfileKeys";
  private static final String GRID_PATH = "resources.GridKeys";
  private static final String ENGINE_PATH = "resources.EngineKeys";
  private static final String GAME_PATH = "resources.GameKeys";

  private static final ResourceBundle myProfileResource = ResourceBundle.getBundle(PROFILE_PATH);
  private static Map<String, List<String>> profile = new HashMap<>();

  private static final ResourceBundle myGridResource = ResourceBundle.getBundle(GRID_PATH);
  private static Map<String, List<String>> grid = new HashMap<>();

  private static final ResourceBundle myEngineResource = ResourceBundle.getBundle(ENGINE_PATH);
  private static Map<String, List<String>> engine = new HashMap<>();

  private static final ResourceBundle myGameResource = ResourceBundle.getBundle(GAME_PATH);
  private static Map<String, List<String>> game = new HashMap<>();
  */

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


  public Map<String, List<String>> getMapFromXML(ResourceBundle keysAndDefaults) {
    List<String> keys = Collections.list(keysAndDefaults.getKeys());
    Map<String, List<String>> mapToFill = new HashMap<>();
    for (String key : keys) {
      mapToFill.put(key, getAllInstances(key, keysAndDefaults.getString(key)));
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

}
/*
  //Needed instance variables to run main method
  private static final int ZERO_INDEX = 0;

  private static Document doc;

  private static final String PROFILE_PATH = "resources.ProfileKeys";
  private static final String GRID_PATH = "resources.GridKeys";
  private static final String ENGINE_PATH = "resources.EngineKeys";
  private static final String GAME_PATH = "resources.GameKeys";

  private static final ResourceBundle myProfileResource = ResourceBundle.getBundle(PROFILE_PATH);
  private static Map<String, List<String>> profile = new HashMap<>();

  private static final ResourceBundle myGridResource = ResourceBundle.getBundle(GRID_PATH);
  private static Map<String, List<String>> grid = new HashMap<>();

  private static final ResourceBundle myEngineResource = ResourceBundle.getBundle(ENGINE_PATH);
  private static Map<String, List<String>> engine = new HashMap<>();

  private static final ResourceBundle myGameResource = ResourceBundle.getBundle(GAME_PATH);
  private static Map<String, List<String>> game = new HashMap<>();

*/

/*
  public static void main(String[] args)
  {
    Map<String, List<String>> dataToWrite = new HashMap<>();
    dataToWrite.put("AddNewCells", new ArrayList<>());
    dataToWrite.get("AddNewCells").add("what");

    dataToWrite.put("Validator", new ArrayList<>());
    dataToWrite.get("Validator").add("PairValidator");

    dataToWrite.put("MatchFinder", new ArrayList<>());
    dataToWrite.get("MatchFinder").add("FlippedFinder");

    XMLGameBuilder gameBuilder = new XMLGameBuilder("game", "data/newGame.xml", dataToWrite);

    XMLParser parser = new XMLParser("data/newGame.xml");
    engine = parser.getMapFromXML(myEngineResource);

    for(String key : engine.keySet())
    {
      System.out.println(key);
      System.out.println(engine.get(key));
    }

    System.out.println();


  }
  }
    /*
    String profile_path = "data/RegisteredProfiles.xml";
    XMLParser parser = new XMLParser(profile_path);

    Map<String, List<String>> allProfiles = new HashMap<>();
    for(String user : parser.getListFromXML("profile", null))
    {
      String [] neededParts = user.split("::");
      String currUsername = neededParts[2];
      String currPassword = neededParts[0];
      String currPath = neededParts[1];
      allProfiles.put(currUsername, new ArrayList<>());
      allProfiles.get(currUsername).add(currPassword);
      allProfiles.get(currUsername).add(currPath);
    }

    for(String key : allProfiles.keySet())
    {
      System.out.println(key);
      System.out.println(allProfiles.get(key));
    }

    System.out.println();

    String bob_path = "data/bobbyBoy.xml";
    parser = new XMLParser(bob_path);
    profile = parser.getMapFromXML(myProfileResource);

    for(String key : profile.keySet())
    {
      System.out.println(key);
      System.out.println(profile.get(key));
    }

    System.out.println();

    String memory_path = "data/MemoryGame.xml";
    parser = new XMLParser(memory_path);
    grid = parser.getMapFromXML(myGridResource);

    for(String key : grid.keySet())
    {
      System.out.println(key);
      System.out.println(grid.get(key));
    }

    System.out.println();

    engine = parser.getMapFromXML(myEngineResource);

    for(String key : engine.keySet())
    {
      System.out.println(key);
      System.out.println(engine.get(key));
    }

    System.out.println();

    game = parser.getMapFromXML(myGameResource);

    for(String key : game.keySet())
    {
      System.out.println(key);
      System.out.println(game.get(key));
    }

  }*/

