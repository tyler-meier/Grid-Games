package ooga.player;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.property.StringProperty;
import javafx.stage.Stage;
import ooga.controller.UserLogin;
import ooga.data.UserProfile;
import ooga.engine.grid.Grid;
import ooga.player.screens.*;

import java.util.Map;

public class Player implements PlayerStart{

  private static final String TITLE = "Grid GORLS + Tyler :)";

  private Stage myStage;
  private LoginScreen myLoginScreen;
  private GameScreen myGameScreen;
  private CustomView myCustomView;
  private UserDefinedGameScreenOne myUserDefinedGameScreenOne;
  private UserDefinedGameScreenTwo myUserDefinedGameScreenTwo;
  private String myGameType, currentUsername;
  private UserLogin myNewUserLogin, myUserLogin;
  private UserProfile myUserProfile;
  private EventHandler<ActionEvent> myEngineEvent, myResetEvent, mySaveEvent, myNewWindowEvent, myUserDefEngineEvent;

  public Player(Stage primaryStage){
    myStage = primaryStage;
    myLoginScreen = new LoginScreen(this);
    myStage.setScene(myLoginScreen.setUpScene());
    myStage.setTitle(TITLE);
    myStage.show();
  }

  /**
   *
   */
  @Override
  public void setUpLoginScreen(){
    myStage.setScene(myLoginScreen.setUpScene());
  }

  /**
   *
   * @param dataError
   */
  @Override
  public void setUpNewProfScreen(StringProperty dataError){
    NewProfileScreen myNewProfScreen = new NewProfileScreen(this);
    myNewProfScreen.setError(dataError);
    myStage.setScene(myNewProfScreen.setUpScene());
  }

  /**
   *
   * @param dataError
   */
  @Override
  public void setUpStartScreen(StringProperty dataError){
    StartScreen myStartScreen = new StartScreen(this);
    myStartScreen.setError(dataError);
    myStage.setScene(myStartScreen.setUpScene());
  }

  /**
   *
   * @param backendGrid
   * @param dataError
   */
  @Override
  public void setUpGameScreen(Grid backendGrid, StringProperty dataError){
    myGameScreen = new GameScreen(myGameType, this);
    myStage.setScene(myGameScreen.makeScene());
    myGameScreen.setGrid(backendGrid);
    myGameScreen.setStats(backendGrid.getGameStats());
    myGameScreen.setGameStatus(backendGrid.getLossStatus(), backendGrid.getWinStatus());
    myGameScreen.setError(dataError);
  }

  /**
   * Creates scene when game is lost, sets on stage
   */
  @Override
  public void setUpLossScreen(){
    LossScreen myLossScreen = new LossScreen(this);
    myStage.setScene(myLossScreen.setUpScene());
  }

  /**
   * Creates screen when level of game is won, sets on stage
   */
  @Override
  public void setUpWonLevelScreen(){
    WonLevelScreen myWonLevelScreen = new WonLevelScreen(this);
    myStage.setScene(myWonLevelScreen.setUpScene());
  }

  /**
   * Creates screen when whole game is won, sets on stage
   */
  @Override
  public void setUpWonGameScreen(){
    WonGameScreen myWonGameScreen = new WonGameScreen(this);
    myStage.setScene(myWonGameScreen.setUpScene());
  }

  public void setUpMakeNewGameScreenOne(){
    myUserDefinedGameScreenOne = new UserDefinedGameScreenOne(this);
    myStage.setScene(myUserDefinedGameScreenOne.setUpScene());
  }

  public void setUpMakeNewGameScreenTwo(Map<String, String> selectedEngineAttributes, Map<String, String> selectedGameAttributes){
    myUserDefinedGameScreenTwo = new UserDefinedGameScreenTwo(this);
    myStage.setScene(myUserDefinedGameScreenTwo.setUpScene(selectedEngineAttributes, selectedGameAttributes));
  }

  public Map<String,String> getUserMadeEngineAttributesMap(){
    return myUserDefinedGameScreenTwo.getUserSelectedEngineAttributes();
  }

  public Map<String, String> getUserMadeGameAttributesMap(){
    return myUserDefinedGameScreenTwo.getUserSelectedGameAttributes();
  }

  /**
   *
   */
  @Override
  public void setUpCustomView(){
    myCustomView = new CustomView(this);
    myCustomView.display();
  }

  /**
   *
   * @param newWindowAction
   */
  @Override
  public void setNewWindow(EventHandler<ActionEvent> newWindowAction){
    myNewWindowEvent = newWindowAction;
  }

  /**
   *
   * @return
   */
  @Override
  public EventHandler<ActionEvent> getNewWindowEvent(){
    return myNewWindowEvent;
  }

  /**
   *
   * @param userLogin
   */
  @Override
  public void setUserLogin(UserLogin userLogin){
    myUserLogin = userLogin;
  }

  /**
   *
   * @return
   */
  @Override
  public UserLogin getMyUserLogin(){
    return myUserLogin;
  }

  /**
   *
   * @param userLogin
   */
  @Override
  public void setNewLogin(UserLogin userLogin){
    myNewUserLogin = userLogin;
  }

  /**
   *
   * @return
   */
  @Override
  public UserLogin getMyNewUserLogin(){
    return myNewUserLogin;
  }

  /**
   *
   * @param event
   */
  @Override
  public void setStartGameButton(EventHandler<ActionEvent> event){
    myEngineEvent = event;
  }


  /**
   *
   * @return
   */
  @Override
  public EventHandler<ActionEvent> getStartGameButtonEvent(){
    return myEngineEvent;
  }

  public void setUserMadeStartButton(EventHandler<ActionEvent> event){
    myUserDefEngineEvent = event;
  }

  public EventHandler<ActionEvent> getUserMAdeStartButton(){
    return myUserDefEngineEvent;
  }

  /**
   *
   * @param event
   */
  @Override
  public void setResetButton(EventHandler<ActionEvent> event) {
    myResetEvent = event;
  }

  /**
   *
   * @return
   */
  @Override
  public EventHandler<ActionEvent> getResetButtonEvent(){
    return myResetEvent;
  }

  /**
   *
   * @param event
   */
  @Override
  public void setSaveButton(EventHandler<ActionEvent> event) { mySaveEvent = event;}

  /**
   *
   * @return
   */
  @Override
  public EventHandler<ActionEvent> getSaveButtonEvent() {
    return mySaveEvent;
  }

  /**
   *
   * @param thisUserProfile
   */
  @Override
  public void setUserProfile(UserProfile thisUserProfile){
    myUserProfile = thisUserProfile;
  }

  /**
   *
   * @return
   */
  @Override
  public UserProfile getMyUserProfile(){
    return myUserProfile;
  }

  /**
   * Changes css style of all screens
   * @param modeType
   */
  @Override
  public void setMode(String modeType) {
    //TODO: get rid of redundant code
    myCustomView.setStyle(modeType);
    myGameScreen.setStyle(modeType);
  }

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
   * @return current game string
   */
  @Override
  public String getGameType(){
    return myGameType;
  };

  /**
   * Sets the username to be the current username of the player playing
   * @param username is the current username of the player
   */
  @Override
  public void setUsername(String username){
    currentUsername = username;
  };

  /**
   * Gets the username of the current player of the game and who is logged in.
   * The username string is returned in this method.
   * @return the current username of the player
   */
  @Override
  public String getUsername(){
    return currentUsername;
  };

  /**
   * Sets the error message for if there was an incorrect username or such
   * @param errorMessage the message that is to be displayed
   */
  @Override
  public void setErrorMessage(StringProperty errorMessage){
    myLoginScreen.setError(errorMessage);
  };

//    /**
//   * An instance variable boolean keeps track of whether most recent progress of player is saved.
//   * The boolean is returned in this method.
//   * @return
//   */
//  @Override
//  public boolean isGameSaved(){
//    return true;
//  };

//  /**
//   * When a player creates a new profile, their password is saved as a String instance variable playerPassword.
//   * The String is returned in this method.
//   * @return
//   */
//  @Override
//  public String getPassword(){
//    return "";
//  };

//  /**
//   * Takes in name of XMLfile that corresponds to the progress of the player and displays view
//   * @param fileName
//   */
//  @Override
//  public void loadProfile(String fileName){ };

//  /**
//   * Check to see if the login actually works
//   * @param username the username for the profile
//   * @param password the password of the profile
//   * @return
//   */
//  @Override
//  public boolean tryLogin(String username, String password){
//    return true;
//  };

//  /**
//   * when the user wants to create a new profile
//   * @param username the username the user wants to use
//   * @param password the password the user wants to use
//   */
//  @Override
//  public void createNewProfile(String username, String password){
//
//  };

//  /**
//   * starts a new game given the information of the default data method
//   * @param defaultData default data for the given game
//   */
//  @Override
//  public void startNewGame(DataObject defaultData){
//
//  };

//  /**
//   * starts a game based off of the saved data
//   * @param myData the data that is for a saved game
//   */
//  @Override
//  public  void loadSaveGame(DataObject myData){
//
//  };

//  /**
//   * sets the profile info of each player
//   */
//  @Override
//  public void setProfileInfo(){
//
//  };

//  /**
//   * gets the preferences of the user (dark mode, colors, etc)
//   */
//  @Override
//  public void getPreferences(){
//
//  };

//  /**
//   * loads the profile and the given info based off of the username chosen
//   */
//  @Override
//  public void loadProfile(){
//
//  };
}