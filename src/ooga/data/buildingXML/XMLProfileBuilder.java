package ooga.data.buildingXML;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.data.UserProfile;
import org.w3c.dom.Element;

public class XMLProfileBuilder extends XMLBuilder {

  private UserProfile user;
  private Map<String, List<String>> userAttributes = new HashMap<>();

  public XMLProfileBuilder(String mainTag, String pathName, UserProfile user) {
    super(mainTag, pathName);
    this.user = user;
    fillUserMap();
    createDocument(mainTag, pathName);
  }

  private void fillUserMap() {
    createMapEntry("DarkMode", Boolean.toString(user.getDarkMode()));
    createMapEntry("ParentalControls", Boolean.toString(user.getParentalControls()));
    createMapEntry("Username", user.getUsername());
    createMapEntry("Password", user.getPassword());

    userAttributes.put("HighScore", new ArrayList<>());
    for(String game : user.getAllSavedGamed().keySet())
    {
      addMapEntry("HighScore", String.format("%s %s", game, user.getSavedGame(game)));
    }

    userAttributes.put("PreviousGame", new ArrayList<>());
    for(String game : user.getAllHighScores().keySet())
    {
      addMapEntry("PreviousGame", String.format("%s %d", game, user.getHighScore(game)));
    }

  }

  private void createMapEntry(String key, String value)
  {
    userAttributes.put(key, new ArrayList<>());
    userAttributes.get(key).add(value);
  }

  private void addMapEntry(String key, String value)
  {
    userAttributes.get(key).add(value);
  }


  @Override
  void addElementsToRoot(Element root) {
    //TODO: fill in
    for(String tag : userAttributes.keySet())
    {
      for(String item : userAttributes.get(tag))
      {
        Element temp = createElement(tag, item);
        root.appendChild(temp);
      }
    }
  }
}
