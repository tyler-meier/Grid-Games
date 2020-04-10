package ooga.data.buildingXML;

import java.util.List;
import org.w3c.dom.Element;

/**
 * Creates the XML that codes the list of existing
 * profiles
 */
public class XMLRegisteredProfileBuilder extends XMLBuilder{

  private List<String> profiles;
  private String profileTag;

  public XMLRegisteredProfileBuilder(String mainTag, String pathName, String profileTag, List<String> profiles) {
    super(mainTag, pathName);
    this.profiles = profiles;
    this.profileTag = profileTag;

    createDocument(mainTag, pathName);
  }

  /**
   * Adds values in the list of profiles all with the same tag "profile"
   * @param root
   */
  @Override
  public void addElementsToRoot(Element root)
  {
    for(String profile : profiles)
    {
      Element temp = createElement(profileTag, profile);
      root.appendChild(temp);
    }
  }
}
