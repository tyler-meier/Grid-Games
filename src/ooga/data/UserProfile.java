package ooga.data;


import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * This class allows us to share information the frontend needs about the current player.
 * We considered that this could result in complications in terms of the open/closed principal
 * and encapsulation but overruled that this was the simplest way to share the information. Adding
 * this many getters and setters would have cluttered our Data API. Additionally, we wanted
 * the front and back ends to be able to edit this person. We maintain consistency by adding
 * a listener to all of the parts the front end edits so that these are immediately written
 * to the XML. This is done through the save() method.
 */
public class UserProfile {
    private final String DEFAULT_GAME_PATH = "resources.DefaultGamePaths";
    private final ResourceBundle myGamePathResource = ResourceBundle.getBundle(DEFAULT_GAME_PATH);
    private final String TO_STRING_SKELETON = "Username: %s \nPassword: %s\nPath: %s\nDisplay Preference: %s\nHigh Scores: %s\nSavedGames: %s\n";
    private final String DISPLAY_PREF_DEFAULT = "default";
    private final String HIGH_SCORE_DEFAULT = "0";

    private final String PATH_SKELETON = "data/profiles/%s.xml";
    private Map<String, String> savedGames;
    private Map<String, Integer> highScores;
    private String username;
    private String password;
    private String path;
    private boolean parentalControls;
    private boolean darkMode;
    private EventHandler<ActionEvent> mySaveAction;
    private String displayPreference;
    private Map<String, String> imagePreferences;


    public UserProfile(String username, String password)
    {
        this.username = username;
        this.password = password;

        this.path = String.format(PATH_SKELETON, username);

        darkMode = false;
        parentalControls = false;
        savedGames = new HashMap<>();
        highScores = new HashMap<>();
        imagePreferences = new HashMap<>();
        displayPreference = DISPLAY_PREF_DEFAULT;
    }



    /**
     * Allows frontend to add a highscore to the user
     * @param type
     * @param score
     */
    public void addHighScore(String type, int score){
        if (!highScores.containsKey(type) || highScores.get(type) < score) {
            highScores.put(type, score);
            save();
        }

    }

    /**
     * Allows the Data object to bind the act of writing to the specified XML
     * whenever high score, displayPreferences, image preferences or dark mode is changed
     * @param saveAction
     */
    public void setSaveAction(EventHandler<ActionEvent> saveAction){
        mySaveAction = saveAction;
    }

    /**
     * Allows frontend to display high scores and
     * backend to write the score
     * @param type
     * @return
     */
    public String getHighScore(String type){
        if(!highScores.containsKey(type))
        {
            return HIGH_SCORE_DEFAULT;
        }
        return highScores.get(type).toString();
    }

    /**
     * If a user has a saved game at that type then the path of that game is returned.
     * If not, the default path at level 1 is returned
     * @param type
     * @return
     */
    public String getSavedGame(String type){
        if(savedGames.containsKey(type))
        {
            return savedGames.get(type);
        }
        return myGamePathResource.getString(type);
    }


    /**
     * Below, there are many getters and setter for both front and back end usages.
     * In all setters that include save(), the new value is immediately written to
     * the profile XML. This only happens for values the front end accesses
     * @param stateOfDarkMode
     */
    public void setDarkMode(boolean stateOfDarkMode)
    {
        darkMode = stateOfDarkMode;
        save();
    }

    public boolean getDarkMode()
    {
        return darkMode;
    }

    public void setParentalControls(boolean stateOfParentalControls)
    {
        parentalControls = stateOfParentalControls;
        save();
    }

    public boolean getParentalControls()
    {
        return parentalControls;
    }

    /**
     * Only used by backend data to when saving a new game
     * @param type
     * @param path
     */
    public void addSavedGame(String type, String path)
    {
        savedGames.put(type, path);
    }

    /**
     * Used by frontend even though does not contain a save() method.
     * We do not allow editing of usernames or passwords.
     * @return
     */
    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getPath()
    {
        return path;
    }

    /**
     * Used just by Data to write to the user XML
     * @return
     */
    public Map<String, String> getAllSavedGamed() {
        return savedGames;
    }

    /**
     * Used just by Data to write to the user XML
     * @return
     */
    public Map<String, Integer> getAllHighScores() {
        return highScores;
    }

    /**
     * Used for testing to make sure we're getting the right data visually
     * @return
     */
    public String toString()
    {
        return String.format(TO_STRING_SKELETON, username, password, path, displayPreference, highScores, savedGames);
    }

    /**
     * Used for displaying game background preference (wave, rainbow, candy etc.)
     * @param preference
     */
  public void setDisplayPreference(String preference) {
      displayPreference = preference;
      save();
  }

    public String getDisplayPreference() {
        return displayPreference;
    }

    /**
     * Used to save and get the image preferences of a user defined saved game
     * @param gameType
     * @param preference
     */
  public void setImagePreferences(String gameType, String preference)
  {
      imagePreferences.put(gameType, preference);
      save();
  }

    public String getImagePreference(String gameType)
    {
        if(imagePreferences.keySet().contains(gameType))
        {
            return imagePreferences.get(gameType);
        }
        return gameType;
    }

    /**
     * Used for writing to XML
     * @return
     */
    public Map<String, String> getAllImagePreference()
    {
        return imagePreferences;
    }


    private void save()
    {
        if (mySaveAction!=null) mySaveAction.handle(new ActionEvent());
    }
}
