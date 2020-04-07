package ooga.data.buildingXML;

import java.util.List;
import java.util.Map;
import org.w3c.dom.Element;

public class XMLProfileBuilder extends XMLBuilder {

  public XMLProfileBuilder(String mainTag, String pathName, Map<String, List<String>> dataToWrite) {
    super(mainTag, pathName, dataToWrite);
  }

  @Override
  void addElementsToRoot(Element root, Map<String, List<String>> dataToWrite) {
    for(String tag : dataToWrite.keySet())
    {
      String data = String.join(DELIMINATOR, dataToWrite.get(tag));
      Element temp = createElement(tag, data);
      root.appendChild(temp);
    }
  }
}
