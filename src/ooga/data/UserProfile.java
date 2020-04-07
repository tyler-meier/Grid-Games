package ooga.data;


import java.util.Map;

public class UserProfile {
    Map<String, String> savedGames;
    Map<String, Integer> highScores;
    String username;
    String password;

    public boolean hasSavedGame(String type){
        return savedGames.containsKey(type);
    }

    public void setHighScore(String type, int score){
        if (!highScores.containsKey(type)) highScores.put(type, score);
    }

    public int getHighScore(String type){

    }
}
