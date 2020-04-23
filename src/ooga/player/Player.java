package ooga.player;

import java.util.Map;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.property.StringProperty;
import javafx.stage.Stage;
import ooga.controller.UserLogin;
import ooga.data.UserProfile;
import ooga.engine.Grid;
import ooga.player.exceptions.ImageNotFoundException;
import ooga.player.screens.*;

/**
 * Main player class that deals with all of the connections between the screens/UI display and controller,
 * and where all of the info is received  from data/engine through controller. Has multiple getter
 * and setter methods for storing data/info, and sets up all of the screens/scenes
 * @author Tyler Meier, Alyssa Shin
 */
public class Player implements PlayerStart{

  private static final String TITLE = "Grid GORLS + Tyler :)";
  private Stage myStage;
  private LoginScreen myLoginScreen;
  private GameScreen myGameScreen;
  private CustomView myCustomView;
  private LeaderBoardScreen myLeaderBoardScreen;
  private UserDefinedGameScreenOne myUserDefinedGameScreenOne;
  private UserDefinedGameScreenTwo myUserDefinedGameScreenTwo;
  private UserDefinedGameScreenThree myUserDefinedGameScreenThree;
  private UserDefinedGameScreenImages myUserDefinedGameScreenImages;
  private String myGameType, currentUsername;
  private UserLogin myNewUserLogin, myUserLogin;
  private UserProfile myUserProfile;
  private EventHandler<ActionEvent> myEngineEvent, myResetGameEvent, myResetLevelEvent, mySaveEvent,
      myNewWindowEvent, myNexLevelEvent, myUserDefEngineEvent;
  private Map<String, Integer> myLeaderBoardMap;
  private String myModeType = "default";

  /**
   * Constructor for this class, sets up the main stage
   * @param primaryStage main stage
   */
  public Player(Stage primaryStage){
    myStage = primaryStage;
    myLoginScreen = new LoginScreen(this);
    myStage.setScene(myLoginScreen.setUpScene());
    myStage.setTitle(TITLE);
    myStage.show();
  }

  /**
   * Gets saved display preference from user profile, and if there is no
   * saved profile, return default
   * @return String of preferred theme
   */
  @Override
  public String getStyle() {
    if (myUserProfile != null) {
      return myUserProfile.getDisplayPreference();
    }
    return "default";
  }

  /**
   * Create login screen and set on stage
   */
  @Override
  public void setUpLoginScreen(){
    myStage.setScene(myLoginScreen.setUpScene());
  }

  /**
   * Creates the screen for creating a new profile
   * @param dataError the error message to be displayed if an error occurs
   */
  @Override
  public void setUpNewProfScreen(StringProperty dataError){
    NewProfileScreen myNewProfScreen = new NewProfileScreen(this);
    myNewProfScreen.setError(dataError);
    myStage.setScene(myNewProfScreen.setUpScene());
  }

  /**
   * Creates the start/home screen for a user
   * @param dataError the error message to be displayed if an error occurs
   */
  @Override
  public void setUpStartScreen(StringProperty dataError){
    StartScreen myStartScreen = new StartScreen(this);
    myStartScreen.setError(dataError);
    myStage.setScene(myStartScreen.setUpScene());
  }

  /**
   * Creates the game screen where games are played
   * @param backendGrid the actual grid to be changed into gridPane and images to be displayed
   * @param dataError the error message to be displayed if an error occurs
   */
  @Override
  public void setUpGameScreen(Grid backendGrid, StringProperty dataError) throws ImageNotFoundException {
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

  /**
   * Sets up the preferences page to change the styling of the screen
   */
  @Override
  public void setUpCustomView(){
    myCustomView = new CustomView(this);
    myCustomView.display();
  }

  /**
   * Creates the leader board screen/high score screens
   */
  @Override
  public void setUpLeaderBoardScreen(){
    myLeaderBoardScreen = new LeaderBoardScreen(this);
    myLeaderBoardScreen.setUpScene(myModeType);
  }

  /**
   * Creates the first screen for making new games where you choose images to use
   */
  @Override
  public void setUpMakeNewGameScreenImages(){
    myUserDefinedGameScreenImages = new UserDefinedGameScreenImages(this);
    myStage.setScene(myUserDefinedGameScreenImages.setUpScene());
  }

  /**
   * Creates the second scene in making new games where you
   * start choosing attributes for the new game
   */
  @Override
  public void setUpMakeNewGameScreenOne(){
    myUserDefinedGameScreenOne = new UserDefinedGameScreenOne(this);
    myStage.setScene(myUserDefinedGameScreenOne.setUpScene());
    myUserDefinedGameScreenOne.setStateRange(myUserDefinedGameScreenImages.getStateRange());
  }

  /**
   * Creates the third scene in making new games where you
   * start choosing attributes for the new game
   */
  @Override
  public void setUpMakeNewGameScreenTwo(){
    myUserDefinedGameScreenTwo = new UserDefinedGameScreenTwo(this);
    myStage.setScene(myUserDefinedGameScreenTwo.setUpScene());
    boolean canLoseLives = myUserDefinedGameScreenImages.isMinesweeper() && myUserDefinedGameScreenOne.hasHiddenCells();
    myUserDefinedGameScreenTwo.setLossStatOptions(canLoseLives);
  }

  /**
   * Creates the fourth and final scene in making new games where you
   * start choosing attributes for the new game
   */
  @Override
  public void setUpMakeNewGameScreenThree(){
    myUserDefinedGameScreenThree = new UserDefinedGameScreenThree(this);
    myStage.setScene(myUserDefinedGameScreenThree.setUpScene());
    myUserDefinedGameScreenThree.setStateRange(myUserDefinedGameScreenImages.getStateRange());
  }

  /**
   * @return map of the selected engine attributes a user chose for their game
   */
  @Override
  public Map<String,String> getUserMadeEngineAttributesMap(){
    return myUserDefinedGameScreenOne.getUserSelectedAttributes();
  }

  /**
   * @return map of the specific game attributes the user chose for their game
   */
  @Override
  public Map<String, String> getUserMadeGameAttributesMap(){
    return myUserDefinedGameScreenTwo.getUserSelectedAttributes();
  }

  /**
   * @return a 2D array of the initial states for the game grid
   */
  @Override
  public int[][] getUserDefinedInitialStates(){
    return myUserDefinedGameScreenThree.getUserSelectedInitialStates();
  }

  /**
   * Sets the event to start the user defined game
   * @param event the event to do
   */
  public void setUserMadeStartButton(EventHandler<ActionEvent> event){
    myUserDefEngineEvent = event;
  }

  /**
   * Starts the user created game and sets game type to title and sets the images
   */
  public void startUserDefinedGame(){
    String title = myUserDefinedGameScreenOne.getTitle();
    String path = myUserDefinedGameScreenImages.getImagePath();
    myUserProfile.setImagePreferences(title, path);
    setGameType(title);
    myUserDefinedGameScreenTwo.addGridSize(myUserDefinedGameScreenThree.getGridSize());
    myUserDefEngineEvent.handle(new ActionEvent());
  }

  /**
   * Sets action to create a new window to play
   * @param newWindowAction the action
   */
  @Override
  public void setNewWindow(EventHandler<ActionEvent> newWindowAction){
    myNewWindowEvent = newWindowAction;
  }

  /**
   * @return the event to create a new window
   */
  @Override
  public EventHandler<ActionEvent> getNewWindowEvent(){
    return myNewWindowEvent;
  }

  /**
   * Sets an already created userLogin with the information needed
   * @param userLogin the specific userLogin
   */
  @Override
  public void setUserLogin(UserLogin userLogin){
    myUserLogin = userLogin;
  }

  /**
   * @return the currently set userLogin with info
   */
  @Override
  public UserLogin getMyUserLogin(){
    return myUserLogin;
  }

  /**
   * Sets a new userLogin when creating one
   * @param userLogin the specific new userLogin
   */
  @Override
  public void setNewLogin(UserLogin userLogin){
    myNewUserLogin = userLogin;
  }

  /**
   * @return the new userLogin that was created and all of its information
   */
  @Override
  public UserLogin getMyNewUserLogin(){
    return myNewUserLogin;
  }

  /**
   * Sets action for what happens when start button is clicked
   * @param event
   */
  @Override
  public void setStartGameButton(EventHandler<ActionEvent> event){
    myEngineEvent = event;
  }

  /**
   * @return the event that happens when start button is clicked
   */
  @Override
  public EventHandler<ActionEvent> getStartGameButtonEvent(){
    return myEngineEvent;
  }

  /**
   * Sets action for what happens when reset game button is pressed
   * @param event
   */
  @Override
  public void setResetGameButton(EventHandler<ActionEvent> event) {
    myResetGameEvent = event;
  }

  /**
   * @return the event for resetting a game
   */
  @Override
  public EventHandler<ActionEvent> getResetGameButtonEvent(){
    return myResetGameEvent;
  }

  /**
   * Sets action for what happens when reset level button is pressed
   * @param event
   */
  @Override
  public void setResetLevelButton(EventHandler<ActionEvent> event) {
    myResetLevelEvent = event;
  }

  /**
   * @return the event for resetting the level of a game
   */
  @Override
  public EventHandler<ActionEvent> getResetLevelButtonEvent(){
    return myResetLevelEvent;
  }

  /**
   * Sets action for what happens when save button is pressed
   * @param event
   */
  @Override
  public void setSaveButton(EventHandler<ActionEvent> event) { mySaveEvent = event;}

  /**
   * @return the event that takes place when save button is pressed
   */
  @Override
  public EventHandler<ActionEvent> getSaveButtonEvent() {
    return mySaveEvent;
  }

  /**
   * Sets action for when next level button is pressed
   * @param event
   */
  @Override
  public void setNextLevel(EventHandler<ActionEvent> event) {
    myNexLevelEvent = event;
  }

  /**
   * @return the event for pressing next level/proceed
   */
  @Override
  public EventHandler<ActionEvent> getNextLevelEvent() {
    return myNexLevelEvent;
  }

  /**
   * Sets the userProfile to the current profile that is being played on
   * @param thisUserProfile current profile
   */
  @Override
  public void setUserProfile(UserProfile thisUserProfile){
    myUserProfile = thisUserProfile;
  }

  /**
   * @return the current profile of the user playing
   */
  @Override
  public UserProfile getMyUserProfile(){
    return myUserProfile;
  }

  /**
   * Sets the map of all users that have high scores for a game
   * @param map the being set to
   */
  @Override
  public void setHighScoreMap(Map<String, Integer> map){
    myLeaderBoardMap = map;
  }

  /**
   * @return the high score map of users and high scores
   */
  @Override
  public Map<String, Integer> getHighScoreMap(){
    return myLeaderBoardMap;
  }

  /**
   * Changes css style of all screens
   * @param modeType
   */
  @Override
  public void setMode(String modeType) {
    myModeType = modeType;
    myCustomView.setStyle(modeType);
    myGameScreen.setStyle(modeType);
  }

  /**
   * An instance variable gameType is set based off of what the  game chosen to play was
   * @param type the current game chosen to  be played
   */
  @Override
  public void setGameType(String type){
    myGameType = type;
  }

  /**
   * @return a string of the current game being played/accessed
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
}