package ooga.data.buildingXML;

import java.util.List;
import java.util.Map;
import org.w3c.dom.Element;

public class XMLGameBuilder extends XMLBuilder {

  public XMLGameBuilder(String mainTag, String pathName, Map<String, List<String>> dataToWrite) {
    super(mainTag, pathName, dataToWrite);
  }

  @Override
  public void addElementsToRoot(Element root, Map<String, List<String>> dataToWrite)
  {
    for(String tag : dataToWrite.keySet())
    {
      for(String item : dataToWrite.get(tag))
      {
        Element temp = createElement(tag, item);
        root.appendChild(temp);
      }
    }
  }

}
