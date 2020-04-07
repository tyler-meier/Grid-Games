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

  private static final Map<String, List<String>> DEFAULT_PROFILE = new HashMap<>();
  private final String REGISTERED_PROFILES_PATH = "data/RegisteredProfiles.xml";
  private final String MAIN_TAG = "profile";
  private final String CONTAINER_TAG = MAIN_TAG + "s";
  private final int INDEX_OF_USERNAME = 2;
  private final int INDEX_OF_PASSWORD = 0;
  private final int INDEX_OF_PATH = 1;
  private final String DELIMINATOR = "::";
  private final String PATH_SKELETON = "data/%s.xml";

  private Map<String, List<String>> allProfiles;
  private XMLParser profileParser;

  public ProfileManager()
  {
    profileParser = new XMLParser(REGISTERED_PROFILES_PATH);
    allProfiles = new HashMap<>();
    for(String user : profileParser.getListFromXML(MAIN_TAG, null))
    {
      String [] neededParts = user.split(DELIMINATOR);
      String currUsername = neededParts[INDEX_OF_USERNAME];
      String currPassword = neededParts[INDEX_OF_PASSWORD];
      String currPath = neededParts[INDEX_OF_PATH];
      allProfiles.put(currUsername, new ArrayList<>());
      allProfiles.get(currUsername).add(currPassword);
      allProfiles.get(currUsername).add(currPath);
    }
  }

  public boolean isValid(String username, String password) throws IncorrectPasswordException, NoUserExistsException
  {
    for(String user : allProfiles.keySet())
    {
      if(user.equals(username))
      {
        if(matchingPassword(username, password))
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

  public String getProfilePath(String username)
  {
    return allProfiles.get(username).get(INDEX_OF_PATH);
  }

  public void addProfile(String username, String password)
  {
    String path = String.format(PATH_SKELETON, username);
    XMLBuilder newProfile = new XMLProfileBuilder(MAIN_TAG, path, DEFAULT_PROFILE);
    allProfiles.put(username, new ArrayList<>());
    allProfiles.get(username).add(password);
    allProfiles.get(username).add(path);
  }


  public void writeUpdatedProfilesToXML()
  {
    Map<String, List<String>> updatedMap = new HashMap<>();
    for(String user : allProfiles.keySet())
    {
      updatedMap.put(MAIN_TAG, new ArrayList(allProfiles.get(user)));
      updatedMap.get(MAIN_TAG).add(user);
    }
    XMLBuilder builder = new XMLProfileBuilder(CONTAINER_TAG, REGISTERED_PROFILES_PATH, updatedMap);
  }

  private boolean matchingPassword(String username, String password)
  {
    return password.equals(allProfiles.get(username).get(INDEX_OF_PASSWORD));
  }
}
