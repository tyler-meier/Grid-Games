package ooga.data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import ooga.data.ProfileManager;
import ooga.data.exceptions.UserAlreadyExistsException;
import org.junit.jupiter.api.Test;
import static java.util.Map.entry;

class ProfileManagerTest {

  private Map<String, String> knownProfiles = Map.ofEntries(entry("todd34", "tod"),
                                                            entry("bobbyBoy","bob123"),
                                                            entry("jay18","boob"),
                                                            entry("tylerm","yown"));

  private Map<String, String> unknownProfiles = Map.ofEntries(entry("todd34", "3"),
      entry("3","bob123"),
      entry("jay18","booob"),
      entry("yankee","yown"));

  private ProfileManager manager = new ProfileManager();

  @Test
  void getProfileTest()
  {
    for(String username: knownProfiles.keySet())
    {
      System.out.println(username);
      assertEquals(username, manager.getProfile(username).getUsername());
      System.out.println(manager.getProfile(username));
    }

    for(String username: unknownProfiles.keySet())
    {
      try{
        assertEquals(false, manager.isValid(username, unknownProfiles.get(username)));
      } catch(Exception e)
      {
        System.out.println(e.getMessage());
      }
    }
  }

  @Test
  void isValidTest()
  {
    for(String username: knownProfiles.keySet())
    {
      assertEquals(true, manager.isValid(username, knownProfiles.get(username)));
    }

  }


  @Test
  void addProfileTest()
  {
    String newUsername = "jay18";
    String newPassword = "boob";
    try {
      manager.addProfile(newUsername, newPassword);
      assertEquals(newUsername, manager.getProfile(newUsername).getUsername());
      System.out.println(manager.getProfile(newUsername));
    } catch (UserAlreadyExistsException e)
    {
      System.out.println(e.getMessage());
    }

  }

}