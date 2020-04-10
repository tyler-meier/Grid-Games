package ooga.data;


import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This class allows us to send all this information
 * the frontend needs about the player
 */
public class UserProfile {
    private final String DEFAULT_GAME_PATH = "resources.DefaultGamePaths";
    private final ResourceBundle myGamePathResource = ResourceBundle.getBundle(DEFAULT_GAME_PATH);
    private final String TO_STRING_SKELETON = "Username: %s \nPassword: %s\nPath: %s\nHigh Scores: %s\nSavedGames: %s\n";


    private final String PATH_SKELETON = "data/%s.xml";
    private Map<String, String> savedGames;
    private Map<String, Integer> highScores;
    private String username;
    private String password;
    private String path;
    private boolean parentalControls;
    private boolean darkMode;


    public UserProfile(String username, String password)
    {
        this.username = username;
        this.password = password;

        this.path = String.format(PATH_SKELETON, username);

        darkMode = false;
        parentalControls = false;
        savedGames = new HashMap<>();
        highScores = new HashMap<>();
    }

    public UserProfile()
    {

    }

    /**
     *
     * @param type
     * @return
     */
    public boolean hasSavedGame(String type){
        return savedGames.containsKey(type);
    }

    public void addHighScore(String type, int score){
        if (!highScores.containsKey(type) || highScores.get(type) < score) {
            highScores.put(type, score);
        }
    }

    public int getHighScore(String type){
        return highScores.get(type);
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
    }

    public boolean getDarkMode()
    {
        return darkMode;
    }

    public void setParentalControls(boolean stateOfParentalControls)
    {
        parentalControls = stateOfParentalControls;
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
        return String.format(TO_STRING_SKELETON, username, password, path, highScores, savedGames);
    }

}
