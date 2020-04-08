package ooga.data.buildingXML;

import java.util.List;
import java.util.Map;
import org.w3c.dom.Element;

public class XMLGameBuilder extends XMLBuilder {

  private Map<String, String> dataToWrite;

  public XMLGameBuilder(String mainTag, String pathName, Map<String, String> dataToWrite) {
    super(mainTag, pathName);
    this.dataToWrite = dataToWrite;
    createDocument(mainTag, pathName);
  }

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
