package ooga.data.buildingXML;


import java.util.Map;
import org.w3c.dom.Element;

/**
 * Able to write an XML file based on a map of data with the
 * tags as the keys and the String text encoded as the values
 */

public class XMLGameBuilder extends XMLBuilder {

  private Map<String, String> dataToWrite;

  public XMLGameBuilder(String mainTag, String pathName, Map<String, String> dataToWrite) {
    super(mainTag, pathName);
    this.dataToWrite = dataToWrite;
    createDocument(mainTag, pathName);
  }

  /**
   * Adds different entry for every entry in the map
   * @param root
   */
  @Override
  public void addElementsToRoot(Element root)
  {
    for(String tag : dataToWrite.keySet())
    {
        Element temp = createElement(tag, dataToWrite.get(tag));
        root.appendChild(temp);
    }
  }


}
