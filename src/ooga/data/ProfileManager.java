package ooga.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ooga.data.buildingXML.XMLBuilder;
import ooga.data.buildingXML.XMLGameBuilder;
import ooga.data.buildingXML.XMLRegisteredProfileBuilder;
import ooga.data.buildingXML.XMLSingularProfileBuilder;
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
  private final String SKELETON_SPACE = "%s %s";
  private final int INDEX_OF_USERNAME = 0;
  private final int INDEX_OF_PASSWORD = 1;
  private final String DELIMINATOR = " ";

  private List<UserProfile> allProfiles;
  private XMLParser profileParser;

  public ProfileManager()
  {
    profileParser = new XMLParser(REGISTERED_PROFILES_PATH);
    allProfiles = new ArrayList<>();
    retrieveKnownProfiles();

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
  public UserProfile addProfile(String username, String password)
  {
    UserProfile newUser = new UserProfile(username, password);
    XMLBuilder newProfileXML = new XMLSingularProfileBuilder(MAIN_TAG, newUser.getPath(), newUser);
    allProfiles.add(newUser);
    writeNewProfileToRegisteredList();
    return newUser;
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

  public boolean notExistingProfile(String username) {
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

    temp.setDarkMode(tempProfileParser.getBooleanElementByTag("DarkMode", "false"));
    temp.setParentalControls(tempProfileParser.getBooleanElementByTag("ParentalControls", "false"));

    setHighScores(temp, tempProfileParser);
    addSavedGames(temp, tempProfileParser);
    return temp;
  }

  private void setHighScores(UserProfile temp, XMLParser tempProfileParser) {
    List<String> highScores = tempProfileParser.getListFromXML("HighScore", "");
    if(!highScores.get(0).isEmpty())
    {
      for(String entry: highScores)
      {
        String [] parts = entry.split(" ");
        temp.addHighScore(parts[0], Integer.parseInt(parts[1]));
      }
    }
  }

  private void addSavedGames(UserProfile temp, XMLParser tempProfileParser) {
    List<String> previousGames = tempProfileParser.getListFromXML("PreviousGame", "");
    if(!previousGames.get(0).isEmpty())
    {
      for(String entry: previousGames)
      {
        String [] parts = entry.split(" ");
        temp.addSavedGame(parts[0], parts[1]);
      }
    }
  }

}
