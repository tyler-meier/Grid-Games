package ooga.player;

import java.util.Map;
import javafx.beans.property.IntegerProperty;
import javafx.event.EventHandler;
import javafx.beans.property.StringProperty;
import javafx.stage.Stage;
import ooga.controller.UserLogin;
import ooga.data.DataObject;
import ooga.engine.grid.Grid;
import ooga.player.screens.GameScreen;
import ooga.player.screens.LoginScreen;
import ooga.player.screens.NewProfileScreen;
import ooga.player.screens.StartScreen;

public class Player implements PlayerStart{

  private static final String TITLE = "Grid GORLS + Tyler :)";

  private Stage myStage;
  private LoginScreen myLoginScreen;
  private NewProfileScreen myNewProfScreen;
  private GameScreen myGameScreen;
  private StartScreen myStartScreen;
  private String myGameType, currentUsername;
  private UserLogin myUserLogin;
  private EventHandler myEngine;

  public Player(){
  }

  public void startView(Stage primaryStage){
    myStage = primaryStage;
    myLoginScreen = new LoginScreen(this);
    myStage.setScene(myLoginScreen.setUpScene());
    myStage.setTitle(TITLE);
    myStage.show();
  }

  public void setUpStartScreen(String username){
    myStartScreen = new StartScreen(myEngine, this);
    currentUsername = username;
    myStage.setScene(myStartScreen.setUpScene(username));
  }

  public void setUpNewProfScreen(){
    myNewProfScreen = new NewProfileScreen(myUserLogin, this);
    myStage.setScene(myNewProfScreen.setUpScene());
  }

  public void setUpGameScreen(Grid backendGrid){   //TODO Pass through game type?
    myGameScreen = new GameScreen(myGameType, this);
    myStage.setScene(myGameScreen.makeScene(backendGrid, myGameType, currentUsername, 800, 500));
  }

  public void setUpLoginScreen(){
    myStage.setScene(myLoginScreen.setUpScene());
  }

  public void setLoginAction(UserLogin userLogin){
    myLoginScreen.giveMeUserLogin(userLogin);
  }

  public void setNewLoginAction(UserLogin userLogin){
    myUserLogin = userLogin;
  }

  public void setStartGameButton(EventHandler engine){
    myEngine = engine;
  }

  /**
   * An instance variable boolean keeps track of whether most recent progress of player is saved.
   * The boolean is returned in this method.
   * @return
   */
  @Override
  public boolean isGameSaved(){
    return true;
  };

  /**
   * An instance variable gameType is set based off of what the  game chosen to play was
   * @param type the current game chosen to  be played
   */
  @Override
  public void setGameType(String type){  //TODO add to api
    myGameType = type;
  }

  /**
   * An instance variable String gameType is set as the name of the game type being currently played.
   * The String is returned in this method.
   * @return
   */
  @Override
  public String getGameType(){
    return myGameType;
  };

  /**
   * When a player creates a new profile, their username is saved as a String instance variable playerUsername.
   * The String is returned in this method.
   * @return
   */
  @Override
  public String getUsername(){
    return currentUsername;
  };

  /**
   * When a player creates a new profile, their password is saved as a String instance variable playerPassword.
   * The String is returned in this method.
   * @return
   */
  @Override
  public String getPassword(){
    return "";
  };

  /**
   * Takes in name of XMLfile that corresponds to the progress of the player and displays view
   * @param fileName
   */
  @Override
  public void loadProfile(String fileName){

  };

  /**
   * Check to see if the login actually works
   * @param username the username for the profile
   * @param password the password of the profile
   * @return
   */
  @Override
  public boolean tryLogin(String username, String password){
    return true;
  };

  /**
   * when the user wants to create a new profile
   * @param username the username the user wants to use
   * @param password the password the user wants to use
   */
  @Override
  public void createNewProfile(String username, String password){

  };

  /**
   * starts a new game given the information of the default data method
   * @param defaultData default data for the given game
   */
  @Override
  public void startNewGame(DataObject defaultData){

  };

  /**
   * starts a game based off of the saved data
   * @param myData the data that is for a saved game
   */
  @Override
  public  void loadSaveGame(DataObject myData){

  };

  /**
   * sets the profile info of each player
   */
  @Override
  public void setProfileInfo(){

  };

  /**
   * gets the preferences of the user (dark mode, colors, etc)
   */
  @Override
  public void getPreferences(){

  };

  /**
   * loads the profile and the given info based off of the username chosen
   */
  @Override
  public void loadProfile(){

  };

  /**
   * Sets the error message for if there was an incorrect username or such
   * @param errorMessage the message that is to be displayed
   */
  @Override
  public void setErrorMessage(StringProperty errorMessage){
    myLoginScreen.setError(errorMessage);
  };

}



