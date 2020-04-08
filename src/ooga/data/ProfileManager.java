package ooga.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.data.buildingXML.XMLBuilder;
import ooga.data.buildingXML.XMLProfileBuilder;
import ooga.data.exceptions.IncorrectPasswordException;
import ooga.data.exceptions.NoUserExistsException;

/**
 * This class deals with all of the active profiles to cut down on the work Data has to do.
 * ProfileManager keeps a running map of all the active profiles and has the ability
 * to add new ones.
 */
public class ProfileManager {

  private final String REGISTERED_PROFILES_PATH = "data/RegisteredProfiles.xml";
  private final String MAIN_TAG = "profile";
  private final String CONTAINER_TAG = MAIN_TAG + "s";
  private final int INDEX_OF_USERNAME = 2;
  private final int INDEX_OF_PASSWORD = 0;
  private final int INDEX_OF_PATH = 1;
  private final String DELIMINATOR = "::";

  private List<UserProfile> allProfiles;
  private XMLParser profileParser;

  public ProfileManager()
  {
    profileParser = new XMLParser(REGISTERED_PROFILES_PATH);
    allProfiles = new ArrayList<>();
    retrieveKnownProfiles();

  }

  private void retrieveKnownProfiles() {
    for(String user : profileParser.getListFromXML(MAIN_TAG, null))
    {
      String [] neededParts = user.split(DELIMINATOR);
      String currUsername = neededParts[INDEX_OF_USERNAME];
      String currPassword = neededParts[INDEX_OF_PASSWORD];
      String currPath = neededParts[INDEX_OF_PATH];
      UserProfile temp = createProfile(currUsername, currPassword, currPath);
      allProfiles.add(temp);
    }
  }

  private UserProfile createProfile(String currUsername, String currPassword, String currPath) {
    UserProfile temp = new UserProfile(currUsername, currPassword);
    XMLParser tempProfileParser = new XMLParser(currPath);

    temp.setDarkMode(tempProfileParser.getBooleanElementByTag("DarkMode", "false"));
    temp.setParentalControls(tempProfileParser.getBooleanElementByTag("ParentalControls", "false"));

    setHighScores(temp, tempProfileParser);
    addSavedGames(temp, tempProfileParser);
    return temp;
  }

  private void setHighScores(UserProfile temp, XMLParser tempProfileParser) {
    List<String> highScores = tempProfileParser.getListFromXML("HighScore", "");
    for(String entry: highScores)
    {
      String [] parts = entry.split(" ");
      temp.addHighScore(parts[1], Integer.parseInt(parts[2]));
    }
  }

  private void addSavedGames(UserProfile temp, XMLParser tempProfileParser) {
    List<String> highScores = tempProfileParser.getListFromXML("PreviousGame", "");
    for(String entry: highScores)
    {
      String [] parts = entry.split(" ");
      temp.addSavedGame(parts[1], parts[2]);
    }
  }

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



  public UserProfile addProfile(String username, String password)
  {
    UserProfile newUser = new UserProfile(username, password);
    XMLBuilder newProfileXML = new XMLProfileBuilder(MAIN_TAG, newUser.getPath(), newUser);
    allProfiles.add(newUser);
    return newUser;
  }


  /*
  public void writeUpdatedProfilesToXML()
  {
    Map<String, List<String>> updatedMap = new HashMap<>();
    for(String user : allProfiles.keySet())
    {
      updatedMap.put(MAIN_TAG, new ArrayList(allProfiles.get(user)));
      updatedMap.get(MAIN_TAG).add(user);
    }
    XMLBuilder builder = new XMLProfileBuilder(CONTAINER_TAG, REGISTERED_PROFILES_PATH, updatedMap);
  }*/

  private boolean matchingPassword(String password, UserProfile temp)
  {
    return password.equals(temp.getPassword());
  }
}
