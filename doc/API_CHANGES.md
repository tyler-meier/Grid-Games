API CHANGES
====

### Engine API
public interface EngineBuilder 
```java
Grid getGrid();
```
This method serves the same purpose as the original getGrid() method, but returns a Grid object rather than a 2D array of Cells. This allows Engine to pass Player all necessary information without depending on a set data structure for the Cells.
```java
Map<String, String> getGameAttributes();
```
We needed to add this public method so that statistics about the current game (such as level, score, and loss statistic) could be saved by Data to save a user’s game progress.
```java
void setupGame(int[][] initialConfig, Map<String, String> gameAttributes, boolean[][] openCells);
```
We needed to add this public method so that the Engine of the game could be set up correctly (given attributes and initial grid configuration in XML files - if applicable). We need a separate method for this (not just constructor) so that the Engine can play multiple levels of the same game and Controller does not need to create a new Engine instance.
```java 
int[][] getGridConfiguration();
```
We needed to add this public method to Engine so that the configuration of the grid (states of all the cells) can be saved when a player wants to save their current game.
```java 
boolean[][] getOpenCellConfiguration();
```
We needed to add this public method so that, again, when a player wants to save their current game, if the game they are playing has open cells, the configuration of the open cells on the grid can also be saved.
```java 
int getLevel();
```
We needed to add this public method so that the Data can access the current level of the game being played. This is important for loading the initial level, the next level, and determining if there are no more levels. 
    
### Data API
Although our Data API looks quite different than our original plan, the conceptual idea of being the link between reading data and creating a game is still very much intact. The biggest change in our API is that we decided not to use the idea of a DataObject. Initially, the DataObject is how we were going to send information regarding the engine, profile and game between the front and back ends. We realized this DataObject was just going to be a bunch of getters and setters so instead, we decided to use Maps for engines and games then create a UserProfile object to share the current user. Additionally, the loadPreviousGame() and loadConfigurationFile() methods were condensed into one loadGameLevelAttributes() method which can handle loading a previous game, loading a default level and loading a user defined game. Saving configurations has been turned into saveCreatedGame() and now needs to take in a gameAttributes map, and grids of states and uncovered cells in order to successfully save a game. Beyond modifying the existing methods, the current Data API has additional functionality to account for the new features we were able to implement. We added methods to be able to save a user created game, get the grid of states and uncovered cells (because we no longer use a data object), get a stringProperty errorMessage to be able to print data error to the frontend and a method which returns a list of all the high scores in order for a given gameType (this allows for the leader board functionality). 

#### Adjusted methods 

1. getPlayerProfile now called login and returns a UserProfile 
```java
DataObject getPlayerProfile(String username, String password);
```
```java 
UserProfile login(String username, String password);
```

2. saveNewPlayerProfile no longer returns void, now returns the UserProfile which was created in order to login the user

```java
 void saveNewPlayerProfile(String username, String password);
```
```java 
UserProfile saveNewPlayerProfile(String username, String password);
```

3. getEngineAttributes now returns the map of a given game type
```java 
 DataObject getEngineAttributes();
```
 ```java 
 Map<String, String> getEngineAttributes(String gameType);
```

4. saveConfigurationFile now called saveGame and needs gameAttributes map as well as state grid and uncovered grid

```java
void saveConfigurationFile(String username, DataObject engineAtributes);
```
```java
void saveGame(Map<String, String> gameAttributes, int [][] grid, boolean[][] uncoveredCells);
```

5. These two methods have been consolidated into one getGameLevelAtrributes which accounts for previous gamees, default levels and user defined games

```java DataObject loadPreviousGame(String username, String gameType);
 DataObject loadConfigurationFile(String gameType);
 DataObject loadPreviousGame(String username, String gameType)'''
 ```
```java
Map<String, String> getGameLevelAttributes(String username, String gameType, int levelIndicator);
```


#### New Methods 
```java
  * Returns an array of the specified stated for the current game
  * @return
  *
 int[][] getGrid();
```

```java
  * If a game has hidden cells, it will need an array of booleans to determine
  * which cells should start open
  * @return
  *
 boolean[][] getOpenCells();
```

```java
  * Allows the backend error messages to be binded to anywhere else in the
  * program so users can get helpful feedback without the other ends knowing what
  * exactly the data is catching
  * @return
  *
 StringProperty getErrorMessage();
```

```java

  * Returns a map of users with saved high scores fot that game and is sorted so
  * that the first users in the map has the highest score. This is used to create
  * a leader board in the frontend
  * @param gameType
  * @return
  *
 Map<String, Integer> getHighScores(String gameType);
```

###  Player API


We knew that Player was gonna be the  main in between  for the controller to send backend info/data to the screens to be displayed, so we knew going into it that a lot more methods were going to be added to ensure this was possible. A lot of the methods added were just getter and setter methods that allowed info to be  passed through and set and gotten by both the screens  to display but  also by the controller/backend to allow for data to be sent to the  back to be updated.

#### NEW METHODS

```java=

/*
 * Gets saved display preference from user profile, 
 * and if there is no saved profile, return default
 * @return String of preferred theme
 */
 String getStyle();

 /*
  * Create login screen and set on stage
  */
 void setUpLoginScreen();

 /**
  * Creates the screen for creating a new profile
  * @param dataError the error message to be displayed if an error occurs
  */
 void setUpNewProfScreen(StringProperty dataError);

 /**
  * Creates the start/home screen for a user
  * @param dataError the error message to be displayed if an error occurs
  */
 void setUpStartScreen(StringProperty dataError);

 /**
  * Creates the game screen where games are played
  * @param backendGrid the actual grid to be changed into gridPane and images to be displayed
  * @param dataError the error message to be displayed if an error occurs
  */
 void setUpGameScreen(Grid backendGrid, StringProperty dataError) throws ImageNotFoundException;

 /**
  * Creates scene when game is lost, sets on stage
  */
 void setUpLossScreen();

 /**
  * Creates screen when level of game is won, sets on stage
  */
 void setUpWonLevelScreen();

 /**
  * Creates screen when whole game is won, sets on stage
  */
 void setUpWonGameScreen();

 /**
  * Sets up the preferences page to change the styling of the screen
  */
 void setUpCustomView();

/**
  * Creates the leader board screen/high score screens
  */
 void setUpLeaderBoardScreen();

 /**
  * Creates the first screen for making new games where you choose images to use
  */
 void setUpMakeNewGameScreenImages();

 /**
  * Creates the second scene in making new games where you
  * start choosing attributes for the new game
  */
 void setUpMakeNewGameScreenOne();

 /**
  * Creates the third scene in making new games where you
  * start choosing attributes for the new game
  */
 void setUpMakeNewGameScreenTwo();

 /**
  * Creates the fourth and final scene in making new games where you
  * start choosing attributes for the new game
  */
 void setUpMakeNewGameScreenThree();

 /**
  * @return map of the selected engine attributes a user chose for their game
  */
 Map<String,String> getUserMadeEngineAttributesMap();

 /**
  * @return map of the specific game attributes the user chose for their game
  */
 Map<String, String> getUserMadeGameAttributesMap();

 /**
  * @return a 2D array of the initial states for the game grid
  */
 int[][] getUserDefinedInitialStates();

 /**
  * Sets the event to start the user defined game
  * @param event the event to do
  */
 void setUserMadeStartButton(EventHandler<ActionEvent> event);

/**
  * Starts the user created game and sets game type to title and sets the images
  */
 void startUserDefinedGame();

 /**
  * Sets action to create a new window to play
  * @param newWindowAction the action
  */
 void setNewWindow(EventHandler<ActionEvent> newWindowAction);

 /**
  * @return the event to create a new window
  */
 EventHandler<ActionEvent> getNewWindowEvent();

 /**
  * Sets an already created userLogin with the information needed
  * @param userLogin the specific userLogin
  */
 void setUserLogin(UserLogin userLogin);

 /**
  * @return the currently set userLogin with info
  */
 UserLogin getMyUserLogin();

 /**
  * Sets a new userLogin when creating one
  * @param userLogin the specific new userLogin
  */
 void setNewLogin(UserLogin userLogin);

 /**
  * @return the new userLogin that was created and all of its information
  */
 UserLogin getMyNewUserLogin();

 /**
  * Sets action for what happens when start button is clicked
  * @param event
  */
 void setStartGameButton(EventHandler<ActionEvent> event);

 /**
  * @return the event that happens when start button is clicked
  */
 EventHandler<ActionEvent> getStartGameButtonEvent();

 /**
  * Sets action for what happens when reset game button is pressed
  * @param event
  */
 void setResetGameButton(EventHandler<ActionEvent> event);

 /**
  * @return the event for resetting a game
  */
 EventHandler<ActionEvent> getResetGameButtonEvent();

 /**
  * Sets action for what happens when reset level button is pressed
  * @param event
  */
 void setResetLevelButton(EventHandler<ActionEvent> event);

 /**
  * @return the event for resetting the level of a game
  */
 EventHandler<ActionEvent> getResetLevelButtonEvent();

 /**
  * Sets action for what happens when save button is pressed
  * @param event
  */
 void setSaveButton(EventHandler<ActionEvent> event);

 /**
  * @return the event that takes place when save button is pressed
  */
 EventHandler<ActionEvent> getSaveButtonEvent();

 /**
  * Sets action for when next level button is pressed
  * @param event
  */
 void setNextLevel(EventHandler<ActionEvent> event);

 /**
  * @return the event for pressing next level/proceed
  */
 EventHandler<ActionEvent> getNextLevelEvent();

 /**
  * Sets the userProfile to the current profile that is being played on
  * @param thisUserProfile current profile
  */
 void setUserProfile(UserProfile thisUserProfile);

 /**
  * @return the current profile of the user playing
  */
 UserProfile getMyUserProfile();

 /**
  * Sets the map of all users that have high scores for a game
  * @param map the being set to
  */
 void setHighScoreMap(Map<String, Integer> event);

 /**
  * @return the high score map of users and high scores
  */
 Map<String, Integer> getHighScoreMap();

 /**
  * Changes css style of all screens
  * @param modeType
  */
 void setMode(String modeType);

 /**
  * An instance variable gameType is set based off of what the  game chosen to play was
  * @param type the current game chosen to  be played
  */
 void setGameType(String type);

 /**
  * Sets the username to be the current username of the player playing
  * @param username is the current username of the player
  */
 void setUsername(String username);