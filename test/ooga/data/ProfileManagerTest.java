package ooga.data;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;
import ooga.data.ProfileManager;
import ooga.data.exceptions.UserAlreadyExistsException;
import org.junit.jupiter.api.Test;
import static java.util.Map.entry;

class ProfileManagerTest {

  private static final String PROFILE_FOLDER = "profileFiles";
  private final String INVALID_LOGINS = "invalidLogins";
  private final String VALID_LOGINS = "validLogins";

  private final String NEW_PROFILE_USERNAME = "test";
  private final String NEW_PROFILE_PASSWORD = "test";


  private ProfileManager manager = new ProfileManager();
  private TextParser tx = new TextParser();


  @Test
  void isValidTest()
  {
    Map<String, String> validLogins = tx.getLogins(VALID_LOGINS);

    for(String username: validLogins.keySet())
    {
      assertTrue(manager.isValid(username, validLogins.get(username)));
    }

  }

  @Test
  void getProfileTest()
  {
    tx.setFileGroup(PROFILE_FOLDER);
    Map<String, String> validLogins = tx.getLogins(VALID_LOGINS);

    for(String username: validLogins.keySet())
    {
      assertTrue(manager.isValid(username, validLogins.get(username)));
      assertEquals(username, manager.getProfile(username).getUsername());
    }

    Map<String, String> invalidLogins = tx.getLogins(INVALID_LOGINS);
    for(String username: invalidLogins.keySet())
    {
      assertFalse(manager.isValid(username, validLogins.get(username)));
    }
  }

  @Test
  void addProfileTest()
  {
    manager.addProfile(NEW_PROFILE_USERNAME, NEW_PROFILE_PASSWORD);
    assertTrue(manager.isValid(NEW_PROFILE_USERNAME, NEW_PROFILE_PASSWORD));

    Map<String, String> validLogins = tx.getLogins(VALID_LOGINS);
    int numberOfLoginsCaught = 0;
    for(String username: validLogins.keySet())
    {
      try{
        manager.addProfile(username, validLogins.get(username));
      } catch(UserAlreadyExistsException e){
        numberOfLoginsCaught++;
    }
    }
    assertEquals(numberOfLoginsCaught, validLogins.size());
  }


}