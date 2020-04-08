package ooga.player;

import javafx.scene.Scene;
import javafx.stage.Stage;
import ooga.controller.UserLogin;
import ooga.data.DataObject;
import ooga.engine.Cell;
import ooga.player.screens.GameScreen;
import ooga.player.screens.LoginScreen;
import ooga.player.screens.NewProfileScreen;
import ooga.player.screens.StartScreen;

public class Player implements PlayerStart{

  private static final String TITLE = "Grid GORLS + Tyler :)";
  private Stage myStage;

  public Player(){
  }

  public void startView(Stage primaryStage){
    myStage = primaryStage;
    myStage.setTitle(TITLE);
    myStage.show();
  }

//
//  public void setLoginAction(UserLogin userLogin){
//    loginScreen.setLoginButton(userLogin);

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
   * @param grid
   */
  @Override
  public void setGrid(Cell[][] grid){

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
  public void setErrorMessage(String errorMessage){

  };

}

/*
code for the method to set up the front end grid with binding--move this to where it makes the most sense
when you're ready to use it, rename things and ask if anything is unclear

public void setGrid(Grid grid) { // ideally we'll be able to make this run one cell at a time
        //so we don't need it to be a 2D array, or we just make a Grid object in back end

        myGrid = new UICell[grid.getRows()][grid.getColumns();
        for (int r = 0; r < myGrid.length; r++) {
            for (int c = 0; c < myGrid[0].length; c++) {
                myGrid[r][c] = new UICell(grid.getCell(r, c);

                // set property so that front end stops users from making a new move while one is in motion
                myGrid[r][c].setMoveInProgress(grid.getInProgressProperty());
            }
        }
}
*/


