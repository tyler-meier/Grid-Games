package ooga.player;

public interface PlayerStart {

  boolean isGameSaved();
  String getGameType();
  String getUsername();
  String getPassword();
  void setGrid();
  void setNewMoveButton();
  void setSaveGameButton();
  void loadProfile();
  void setLoginButton();
  void setCreateUserButton();
  void setGameTypeButton();
  void setStartNewGameButton();
  void setStartSavedGameButton();
  void setSavePreferencesButton();

}
