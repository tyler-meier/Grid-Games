package ooga.player;

import javafx.beans.property.StringProperty;
import javafx.stage.Stage;
import ooga.controller.UserLogin;
import ooga.data.DataObject;
import ooga.engine.Cell;
import ooga.engine.grid.Grid;
import ooga.player.screens.GameScreen;
import ooga.player.screens.LoginScreen;
import ooga.player.screens.NewProfileScreen;
import ooga.player.screens.StartScreen;

import java.io.FileNotFoundException;

public class Player implements PlayerStart{

  private static final String TITLE = "Grid GORLS + Tyler :)";
  private Stage myStage;
  private LoginScreen myLoginScreen;
  private NewProfileScreen myNewProfScreen;
  private GameScreen myGameScreen;
  private  StartScreen myStartScreen;

  public Player(){
  }

  public void startView(Stage primaryStage){
    myStage = primaryStage;
    myLoginScreen = new LoginScreen(this);
    myNewProfScreen = new NewProfileScreen(this);
    myGameScreen = new GameScreen("BejeweledAction", this);
    myStartScreen = new StartScreen(this);
    myStage.setScene(myLoginScreen.setUpScene());
    myStage.setTitle(TITLE);
    myStage.show();
  }

  public void setUpStartScreen(String username){
    myStage.setScene(myStartScreen.setUpScene(username));
  }
  public void setUpNewProfScreen(){
    myStage.setScene(myNewProfScreen.setUpScene());
  }

  public void setUpGameScreen(String gameType){
    myStage.setScene(myGameScreen.makeScene(800, 500));
  }
  public void setUpLoginScreen(){
    myStage.setScene(myLoginScreen.setUpScene());
  }


  public void setLoginAction(UserLogin userLogin){
    myLoginScreen.giveMeUserLogin(userLogin);
  }

  public void setNewLoginAction(UserLogin userLogin){
    myNewProfScreen.giveMeUserLogin(userLogin);
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
   * An instance variable String gameType is set as the name of the game type being currently played.
   * The String is returned in this method.
   * @return
   */
  @Override
  public String getGameType(){
    return "";
  };

  /**
   * When a player creates a new profile, their username is saved as a String instance variable playerUsername.
   * The String is returned in this method.
   * @return
   */
  @Override
  public String getUsername(){
    return "";
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
   * 2D array of grid is taken in as parameter to generate the corresponding view of the grid.
   * @param backendGrid
   */
  @Override
  public void setGrid(Grid backendGrid) throws FileNotFoundException {
    myGameScreen.setGrid(backendGrid);
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
   * starts a agame based off of the saved data
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



