public interface Player {

  /**
   * An instance variable boolean keeps track of whether most recent progress of player is saved.
   * The boolean is returned in this method.
   * @return
   */
  boolean isGameSaved();

  /**
   * An instance variable String gameType is set as the name of the game type being currently played.
   * The String is returned in this method.
   * @param gameName
   * @return
   */
  String getGameType();

  /**
   * When a player creates a new profile, their username is saved as a String instance variable playerUsername.
   * The String is returned in this method.
   * @return
   */
  String getUsername();

  /**
   * When a player creates a new profile, their password is saved as a String instance variable playerPassword.
   * The String is returned in this method.
   * @return
   */
  String getPassword();

  /**
   * 2D array of grid is taken in as parameter to generate the corresponding view of the grid.
   * @param grid
   */
  void setGrid(Cell[][] grid);

  /**
   * Takes in name of XMLfile that corresponds to the progress of the player and displays view
   * @param fileName
   */
  void loadProfile(String fileName);

  

  void setLoginButton();
  void setCreateUserButton();
  void setGameTypeButton();
  void setStartNewGameButton();
  void setStartSavedGameButton();
  void setSavePreferencesButton();

}