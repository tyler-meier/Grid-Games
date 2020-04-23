package ooga.data;


import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * This class allows us to send all this information
 * the frontend needs about the player
 */
public class UserProfile {
    private final String DEFAULT_GAME_PATH = "resources.DefaultGamePaths";
    private final ResourceBundle myGamePathResource = ResourceBundle.getBundle(DEFAULT_GAME_PATH);
    private final String TO_STRING_SKELETON = "Username: %s \nPassword: %s\nPath: %s\nDisplay Preference: %s\nHigh Scores: %s\nSavedGames: %s\n";
    private final String DISPLAY_PREF_DEFAULT = "default";

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


    public UserProfile(String username, String password)
    {
        this.username = username;
        this.password = password;

        this.path = String.format(PATH_SKELETON, username);

        darkMode = false;
        parentalControls = false;
        savedGames = new HashMap<>();
        highScores = new HashMap<>();
        displayPreference = DISPLAY_PREF_DEFAULT;
    }

    public UserProfile()
    {

    }

    /**
     * Used by frontend to offer the option of saving a game
     * @param type
     * @return
     */
    public boolean hasSavedGame(String type){
        return savedGames.containsKey(type);
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
            return "0";
        }
        return highScores.get(type).toString();
    }

    public String getSavedGame(String type){
        if(savedGames.containsKey(type))
        {
            return savedGames.get(type);
        }
        return myGamePathResource.getString(type);
    }

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

    public void addSavedGame(String type, String path)
    {
        savedGames.put(type, path);
    }

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

    public Map<String, String> getAllSavedGamed() {
        return savedGames;
    }

    public Map<String, Integer> getAllHighScores() {
        return highScores;
    }

    public String toString()
    {
        return String.format(TO_STRING_SKELETON, username, password, path, displayPreference, highScores, savedGames);
    }

    private void save()
    {
        if (mySaveAction!=null) mySaveAction.handle(new ActionEvent());
    }


  public void setDisplayPreference(String preference) {
      displayPreference = preference;
      save();
  }
    public String getDisplayPreference() {
        return displayPreference;
    }
}
