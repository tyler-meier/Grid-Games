package ooga.data;


import java.util.HashMap;
import java.util.Map;

public class UserProfile {
    private Map<String, String> savedGames;
    private Map<String, Integer> highScores;
    private String username;
    private String password;
    private boolean parentalControls;
    private boolean darkMode;


    public UserProfile(String username, String password)
    {
        this.username = username;
        this.password = password;
        darkMode = false;
        parentalControls = false;
        savedGames = new HashMap<>();
        highScores = new HashMap<>();
    }

    public UserProfile()
    {

    }


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
        return savedGames.get(type);
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


}
