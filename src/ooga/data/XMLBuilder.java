package ooga.data;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLBuilder {


  private DocumentBuilderFactory documentFactory;
  private DocumentBuilder documentBuilder;
  private Document document;
  private final String DELIMINATOR = "::";

  public XMLBuilder(String mainTag, String pathName, Map<String, List<String>> dataToWrite)
  {
    buildFile();
    createDocument(mainTag, pathName, dataToWrite);
  }

  private void buildFile() {
    try {
      documentFactory = DocumentBuilderFactory.newInstance();
      documentBuilder = documentFactory.newDocumentBuilder();
      document = documentBuilder.newDocument();
    } catch (ParserConfigurationException pce) {
      pce.printStackTrace();
    }
  }

  /**
   * Builds the root element to add new element to and eventually transform
   */
  private void createDocument(String element, String pathName, Map<String, List<String>> dataToWrite){
    // root element
    Element root = document.createElement(element);
    document.appendChild(root);
    addElementsToRoot(root, dataToWrite);
    transform(pathName);
  }

  /**
   * Transformer allows us to write the DOM object to the desired output StreamResult
   */
  private void transform(String pathName) {

    try{
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource domSource = new DOMSource(document);

      StreamResult streamResult = new StreamResult(new File(pathName));

      transformer.transform(domSource, streamResult);

    }
    catch (TransformerException e) {
      e.printStackTrace();
    }
  }

  /**
   * Add the elements that are necessary to every board
   * including type, rows, columns, and a manual board
   * @param root
   */
  private void addElementsToRoot(Element root, Map<String, List<String>> dataToWrite)
  {
    for(String tag : dataToWrite.keySet())
    {
      String data = String.join(DELIMINATOR, dataToWrite.get(tag));
      Element temp = createElement(tag, data);
      root.appendChild(temp);
    }
  }

  /**
   * Creates a single element from a given tag name and text
   * @param tagName
   * @param text
   * @return
   */
  private Element createElement(String tagName, String text)
  {
    Element e = document.createElement(tagName);
    e.appendChild(document.createTextNode(text));
    return e;
  }


  /*

  //needed instance variables to run main method

  private static DocumentBuilderFactory documentFactory;
  private static DocumentBuilder documentBuilder;
  private static Document document;
  private static final String DELIMINATOR = "::";
  private static String pathName = "data/practiceBuilder.xml";


    public static void main(String[] args)
    {
      String REGISTERED_PROFILES_PATH = "data/RegisteredProfiles.xml";
      XMLParser profileParser = new XMLParser(REGISTERED_PROFILES_PATH);
      Map<String, List<String>> allProfiles = new HashMap<>();
      for(String user : profileParser.getListFromXML("profile", null))
      {
        String [] neededParts = user.split("::");
        String currUsername = neededParts[2];
        String currPassword = neededParts[0];
        String currPath = neededParts[1];
        allProfiles.put(currUsername, new ArrayList<>());
        allProfiles.get(currUsername).add(currPassword);
        allProfiles.get(currUsername).add(currPath);
      }
      Map<String, List<String>> updatedMap = new HashMap<>();
      for(String user : allProfiles.keySet())
      {
        updatedMap.put("profile", new ArrayList(allProfiles.get(user)));
        updatedMap.get("profile").add(user);
      }
      XMLBuilder builder = new XMLBuilder("profiles", REGISTERED_PROFILES_PATH, updatedMap);
    }*/
  }