package ooga.data.buildingXML;

import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;

/**
 * This method allows us to create the engines for User Defined Games
 * This is not used in any other case.
 */
public class XMLEngineBuilder extends XMLBuilder {

  private Map<String, String> engineAttributes;
  public XMLEngineBuilder(String mainTag, String pathName, Map<String, String> attributes) throws ParserConfigurationException {
    super(mainTag, pathName);
    try{
      engineAttributes = attributes;
      createDocument(mainTag, pathName);
    } catch(ParserConfigurationException e)
    {
      throw e;
    }
  }

  @Override
  void addElementsToRoot(Element root) {
    for(String tag : engineAttributes.keySet())
    {
      Element temp = createElement(tag, engineAttributes.get(tag));
      root.appendChild(temp);
    }
  }
}
