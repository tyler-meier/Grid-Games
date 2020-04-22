package ooga.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import ooga.data.buildingXML.XMLBuilder;
import ooga.data.buildingXML.XMLGameBuilder;
import ooga.data.buildingXML.XMLRegisteredProfileBuilder;
import ooga.data.buildingXML.XMLSingularProfileBuilder;
import ooga.data.exceptions.EmptyEntryException;
import ooga.data.exceptions.IncorrectPasswordException;
import ooga.data.exceptions.InvalidCharacterEntryException;
import ooga.data.exceptions.NaughtyNameException;
import ooga.data.exceptions.NoUserExistsException;
import ooga.data.exceptions.UserAlreadyExistsException;

/**
 * This class deals with all of the active profiles to cut down on the work Data has to do.
 * ProfileManager keeps a running map of all the active profiles and has the ability
 * to add new ones.
 */
public class ProfileManager {

  private final String REGISTERED_PROFILES_PATH = "data/RegisteredProfiles.xml";
  private final String INAPPROPRIATE_WORDS_PATH = "resources.InvalidNameEntries";
  private final ResourceBundle myInappropriateWords = ResourceBundle.getBundle(INAPPROPRIATE_WORDS_PATH);

  private final String MAIN_TAG = "profile";
  private final String CONTAINER_TAG = MAIN_TAG + "s";
  private final String SKELETON_SPACE = "%s %s";
  private final int INDEX_OF_USERNAME = 0;
  private final int INDEX_OF_PASSWORD = 1;
  private final String DELIMINATOR = " ";
  private final int GAME_TYPE_INDEX = 0;
  private final int VALUE_INDEX = 1;
  private final String DEFAULT_BOOLEAN_VALUE = "false";
  private final String DEFAULT_LIST_VALUE = "";
  private final String DEFAULT_PREFERENCE_VALUE = "default";
  private final String DARK_MODE_TAG = "DarkMode";
  private final String PARENTAL_CONT_TAG = "ParentalControls";
  private final String HIGH_SCORE_TAG = "HighScore";
  private final String PREVIOUS_GAME_TAG = "PreviousGame";
  private static final String DISPLAY_PREF_TAG = "DisplayPreference";

  private List<String> inappropriateNameEntries;
  private List<UserProfile> allProfiles;
  private XMLParser profileParser;

  public ProfileManager()
  {
    profileParser = new XMLParser(REGISTERED_PROFILES_PATH);
    allProfiles = new ArrayList<>();
    retrieveKnownProfiles();
    inappropriateNameEntries = Collections.list(myInappropriateWords.getKeys());

  }

  /**
   * Given an updated user, able to update their XML file
   * @param user
   */
  public void updatePLayerXML(UserProfile user)
  {
    XMLBuilder newProfileXML = new XMLSingularProfileBuilder(MAIN_TAG, user.getPath(), user);
  }

  /**
   * This allows for username and password validating to login
   * @param username
   * @param password
   * @return
   * @throws IncorrectPasswordException
   * @throws NoUserExistsException
   */
  public boolean isValid(String username, String password) throws IncorrectPasswordException, NoUserExistsException
  {
    for(UserProfile user : allProfiles)
    {
      if(user.getUsername().equals(username))
      {
        if(matchingPassword(password, user))
        {
          return true;
        }
        else
        {
          throw new IncorrectPasswordException(password);
        }
      }
    }
    throw new NoUserExistsException(username);
  }

  /**
   * Used in Data to get profiles to send to frontend
   * @param username
   * @return
   */
  public UserProfile getProfile(String username)
  {
    for(UserProfile user : allProfiles)
    {
      if(user.getUsername().equals(username))
      {
        return user;
      }
    }
    return null;
  }


  /**
   * Adds a new profile to the XML configuration and
   * adds new folder to all profiles
   * @param username
   * @param password
   * @return
   */
  public UserProfile addProfile(String username, String password) throws UserAlreadyExistsException, EmptyEntryException,
      InvalidCharacterEntryException, NaughtyNameException
  {
    if(username.isEmpty() || password.isEmpty())
    {
      throw new EmptyEntryException();
    }
    if(username.contains(" ") || password.contains(" "))
    {
      throw new InvalidCharacterEntryException(" ");
    }
    if(inappropriateNameEntries.contains(username) || inappropriateNameEntries.contains(password))
    {
      throw new NaughtyNameException();
    }
    if(notExistingUsername(username))
    {
      UserProfile newUser = new UserProfile(username, password);
      XMLBuilder newProfileXML = new XMLSingularProfileBuilder(MAIN_TAG, newUser.getPath(), newUser);
      allProfiles.add(newUser);
      writeNewProfileToRegisteredList();
      return newUser;
    }
    else
    {
      throw new UserAlreadyExistsException(username);
    }
  }

  public Map<String, Integer> getHighScores(String gameType)
  {
    Map<String, Integer> allHighScores = new HashMap<>();
    for(UserProfile user: allProfiles)
    {
      if(user.getAllHighScores().containsKey(gameType))
      {
          allHighScores.put(user.getUsername(), Integer.parseInt(user.getHighScore(gameType)));
      }
    }
    return sortHighScoreMap(allHighScores);
  }

  private Map<String, Integer> sortHighScoreMap(Map<String, Integer> allHighScores)
  {
    List<Map.Entry<String, Integer> > list = new LinkedList<>(allHighScores.entrySet());
    Collections.sort(list, this::compareHighScores);

    HashMap<String, Integer> temp = new LinkedHashMap<>();
    for (Map.Entry<String, Integer> sortedMap : list) {
      temp.put(sortedMap.getKey(), sortedMap.getValue());
    }
    return temp;

  }

  private int compareHighScores(Map.Entry<String, Integer> userOne,
      Map.Entry<String, Integer> userTwo)
  {
    return (userTwo.getValue()).compareTo(userOne.getValue());
  }



  private void writeNewProfileToRegisteredList()
  {
    List<String> profilesToWrite = new ArrayList<>();
    for(UserProfile user : allProfiles)
    {
      profilesToWrite.add(String.format(SKELETON_SPACE, user.getUsername(), user.getPassword()));
    }
    XMLBuilder builder = new XMLRegisteredProfileBuilder(CONTAINER_TAG, REGISTERED_PROFILES_PATH, MAIN_TAG, profilesToWrite);
  }

  private boolean matchingPassword(String password, UserProfile temp)
  {
    return password.equals(temp.getPassword());
  }

  private boolean notExistingUsername(String username) {
    for(UserProfile existingUser: allProfiles)
    {
      if(existingUser.getUsername().equals(username))
      {
        return false;
      }
    }
    return true;
  }

  private void retrieveKnownProfiles() {
    for(String user : profileParser.getListFromXML(MAIN_TAG, null))
    {
      String [] neededParts = user.split(DELIMINATOR);
      String currUsername = neededParts[INDEX_OF_USERNAME];
      String currPassword = neededParts[INDEX_OF_PASSWORD];
      UserProfile temp = readInProfile(currUsername, currPassword);
      allProfiles.add(temp);
    }
  }

  private UserProfile readInProfile(String currUsername, String currPassword) {
    UserProfile temp = new UserProfile(currUsername, currPassword);
    XMLParser tempProfileParser = new XMLParser(temp.getPath());

    temp.setDarkMode(tempProfileParser.getBooleanElementByTag(DARK_MODE_TAG, DEFAULT_BOOLEAN_VALUE));
    temp.setParentalControls(tempProfileParser.getBooleanElementByTag(PARENTAL_CONT_TAG, DEFAULT_BOOLEAN_VALUE));
    temp.setDisplayPreference(tempProfileParser.getStringElementByTag(DISPLAY_PREF_TAG, DEFAULT_PREFERENCE_VALUE));

    setHighScores(temp, tempProfileParser);
    addSavedGames(temp, tempProfileParser);
    return temp;
  }

  private void setHighScores(UserProfile temp, XMLParser tempProfileParser) {
    List<String> highScores = tempProfileParser.getListFromXML(HIGH_SCORE_TAG, DEFAULT_LIST_VALUE);
    if(!highScores.get(GAME_TYPE_INDEX).isEmpty())
    {
      for(String entry: highScores)
      {
        String [] parts = entry.split(DELIMINATOR);
        temp.addHighScore(parts[GAME_TYPE_INDEX], Integer.parseInt(parts[VALUE_INDEX]));
      }
    }
  }

  private void addSavedGames(UserProfile temp, XMLParser tempProfileParser) {
    List<String> previousGames = tempProfileParser.getListFromXML(PREVIOUS_GAME_TAG, DEFAULT_LIST_VALUE);
    if(!previousGames.get(GAME_TYPE_INDEX).isEmpty())
    {
      for(String entry: previousGames)
      {
        String [] parts = entry.split(DELIMINATOR);
        temp.addSavedGame(parts[GAME_TYPE_INDEX], parts[VALUE_INDEX]);
      }
    }
  }

}
