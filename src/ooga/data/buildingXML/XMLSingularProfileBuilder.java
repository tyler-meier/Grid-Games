package ooga.data.buildingXML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.data.UserProfile;
import org.w3c.dom.Element;

/**
 * Creates or updates the XML of the user passed
 */
public class XMLSingularProfileBuilder extends XMLBuilder {

  private static final String DISPLAY_PREF_TAG = "DisplayPreference";
  private final String DARK_MODE_TAG = "DarkMode";
  private final String PARENTAL_CONT_TAG = "ParentalControls";
  private final String USERNAME_TAG = "Username";
  private final String PASSWORD_TAG = "Password";
  private final String HIGH_SCORE_TAG = "HighScore";
  private final String PREVIOUS_GAME_TAG = "PreviousGame";
  private final String IMAGE_PREFERENCE_TAG = "ImagePreference";
  private final String ENTRY_SKELETON = "%s %s";

  private UserProfile user;
  private Map<String, List<String>> userAttributes = new HashMap<>();

  public XMLSingularProfileBuilder(String mainTag, String pathName, UserProfile user) {
    super(mainTag, pathName);
    this.user = user;
    fillUserMap();
    createDocument(mainTag, pathName);
  }

  /**
   * Adds elements based off the map created from the User given
   * @param root
   */
  @Override
  void addElementsToRoot(Element root) {
    for(String tag : userAttributes.keySet())
    {
      for(String item : userAttributes.get(tag))
      {
        Element temp = createElement(tag, item);
        root.appendChild(temp);
      }
    }
  }

  private void fillUserMap() {
    createMapEntry(DARK_MODE_TAG, Boolean.toString(user.getDarkMode()));
    createMapEntry(PARENTAL_CONT_TAG, Boolean.toString(user.getParentalControls()));
    createMapEntry(USERNAME_TAG, user.getUsername());
    createMapEntry(PASSWORD_TAG, user.getPassword());
    createMapEntry(DISPLAY_PREF_TAG, user.getDisplayPreference());

    userAttributes.put(PREVIOUS_GAME_TAG, new ArrayList<>());
    for(String game : user.getAllSavedGamed().keySet())
    {
      addMapEntry(PREVIOUS_GAME_TAG, String.format(ENTRY_SKELETON, game, user.getSavedGame(game)));
    }

    userAttributes.put(HIGH_SCORE_TAG, new ArrayList<>());
    for(String game : user.getAllHighScores().keySet())
    {
      addMapEntry(HIGH_SCORE_TAG, String.format(ENTRY_SKELETON, game, user.getHighScore(game)));
    }

    userAttributes.put(IMAGE_PREFERENCE_TAG, new ArrayList<>());

    for(String game : user.getAllImagePreference().keySet())
    {
      addMapEntry(IMAGE_PREFERENCE_TAG, String.format(ENTRY_SKELETON, game, user.getImagePreference(game)));
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

}
