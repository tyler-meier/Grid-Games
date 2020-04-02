# Use Cases

## Alyssa Use Cases:
1. User changes to different game mode (i.e. dark mode, infinite game, challenge game)
    * User accesses the different game modes available for each game type through a drop down window
    * When a new game mode is clicked, a new resource bundle is accessed to change the colors of that game (i.e dark mode color scheme changes)
2. User creates a valid move (move to next square in grid)
    * Based on the rules of the game, check if the move is valid
    * If valid, either make the cells disappear or animate
3. User creates an invalid move (move across grid)
    * Based on the rules of the game, check if the move is invalid
    * If invalid, either cause the correct error or nothing happens
4. User creates a new window
    * A new player with associated controller and engine are created
5. User goes back to main menu
    * Splash screen is displayed again, user can now choose again what type of game they want to play
6. User saves game and exits the window
    * Should save most recent progress when the game is open and started again
7. User restarts game
    * Reverts to original configuration, all views of scores, elapsed time, etc. resets
8. User saves preferences
    * Save to player profile

## Tyler Use Cases:
1. Switches color of background
    * User chooses a new color on a color picker, frontend takes in infor and changes display to be that color (all in frontend)
2. User types in wrong info for username and password
    * User tries to type in username and password and it is wrong, frontend takes in this info and passes it to the data through controller, data tries to retireve but there is no match, an error is thrown and handled somewhere, let's user know that the login credentials do not match anything
3. Create new account
    * User chooses create new account, screen pops up to type in a username and a password, clicks create, once it does that a file is created that has all of the updated information of what the player has completed (how far in each game it has gotten), file saved in data
4. Login to existing account successfully
    * User types in their information, once correct, the engine will get the file from the data with the given username and send up to load all of the information to the frontend of what levels the plaer is on and what its scores are and such
5. User clicks on a new game button
    * user clicks which game it wants to play, if it is logged in to profile, it will pop up a screen asking to load saved work on game or create new game, if clicks load saved then data will retrieve file for players last game and send it up to front end to set up grid and view, if chooses new game, data loads the basic preset values and information for the game being played
6. User loses a game
    * A window will pop up saying you lost the game or level, and give you an option to restart the game or level or to go back to the main menu, and depending on what the user chooses the frontend will change the windows to work
7. User wins a game and beats all of the levels
    * A window pops up showing that you won the game and beat all of the levels, and gives an option to go back to. level one or return to the main menu
8. User moves onto different level
    * if rules for a level for beating it are reached, then the level will update and the view will change, the data will have to send up more information based off of data file for the next level, and the front end will update the view based off of the new states for the grid. 


## Juliet Use Cases:
1. Controller wants to create a new game
    * String of gameType is given to the Data interface method getGame()
    * Using a resource bundle, the Data interface gets the path of the XML associated with that game type
    * Parser builds a new document from that XML file
    * Parser looks for the necessary keys in that file and bundles them into a dataobject that the Controller can grab and use
    
    
2. Parser cannot find element in XML file
    * Instead of breaking, the parser will need to be configured so that if it doesn't find something, a default value will be set
    * Parser will go into resource bundle and grab the default value for that key
    * If value is not found in the XML file then the needed value is set to the default
    * Give message on console that a default message was set
    
    
3. Controller wants to create new engine
    * Parser bundles a data object which includes the grid, validator, new cell adder and more attributes as Strings for the Engine object to change into objects using reflection
    * parser.getDataObject() is called


4. Controller wants to save a current game configuration
    * Data is given the current configuration of the board, current profile and the current engine attributes by the Controller
    * Parser converts the attributes of the board and engine into strings
    * Parser builds a new XML file and saves the attributes
    * Parser adds a saved data file to the list of saved data files of the current player 
    
5. Controller wants to load a game configuration
    * The controller will give a game configuration and a current user profile of the game to load
    * The Parser will look into the XML file for that player to match the desired game with the path of XML file to read. 
    * Parser grabs the needed path then reads that XML document to grab the encoded information to create a game 
    * Data bundles these attributes into seperate front and backend data bundles with different needed data for the two ends to read 

6. Controller wants to create and save a new login profile
    * Data is given a username and password which had been typed into a text box in the frontend
    * Parser adds this info to its resource bundle which maps usernames to passwords
    * Parser saves the atributes such as parental controls to an XML file which decribes that player 
    * As the player plays and saves games, saved configurations will also be saved to this XML file
    
7. Controller wants to load a login profile 
    * Controller gives the Parser a username and password
    * The Data interface checks this username and pasword against all of the saved usernames and passwords in its resource bundle
    * Data returns the dataobject of that profile to just the frontend 
    
8. Controller wants to load a login profile but gives the wrong username or password
    * During the launching of the game, a StringPropety titled loginMessage will be bidirectionally binded between the Data and Player interfaces
    * Once Data is given the invalid login, it will loop through all of its login profiles
    * if it does not find a username, or the password does not match, Data will set the value of the loginMessage StringProperty to "User doesn't exist" or "Incorrect Password"
    * This message will automatically be updated in the frontend and appear on the screen, giving the user feedback on their input


## Tanvi Use Cases:
1. User playing candy crush and clicks two tiles that when switched, do not make a match. (Sprint 1)
    * When the player selects their two tiles, the `updateBoard()` method in the `Engine` class will be called.
    * This method will first call `checkIsValid()` to make sure that the selected tiles are valid.
    * The `SwitchValidator` instance of `Validator` will switch the cells and call `matchesExist()` in the `OpenFinder` instance of `MatchFinder` to see if the switch would result in any matches. 
    * Since the switch does not result in any matches, `SwitchValidator` will return the cells to thier original positions, and hand control back to `Player` to await user input.
2. User playing memory game clicks two tiles that are not a match. (Sprint 1)
    * When the player selects their two tiles, the `updateBoard()` method in the `Engine` class will be called.
    * This method will first call `checkIsValid()` to make sure that the selected tiles are valid.
    * The `PairValidator` instance of `Validator` will flip over the two cells and call `matchesExist()` in the `FlippedFinder` instance of `MatchFinder` to see if the  two selected cells make a pair. 
    * Since the selected cells do not make a pair, `PairValidator` will turn the cells back to closed and hand control back to `Player` to await user input.
3. User playing memory game clicks two tiles that are a match. (Sprint 1)
    * When the player selects their two tiles, the `updateBoard()` method in the `Engine` class will be called.
    * This method will first call `checkIsValid()` to make sure that the selected tiles are valid.
    * The `PairValidator` instance of `Validator` will flip over the two cells and call `matchesExist()` in the `FlippedFinder` instance of `MatchFinder` to see if the  two selected cells make a pair.
    * Since the two cells do make a pair, they will be considered valid, and `Engine` will then `removeCell()` for the pair, and hand control back to `Player` to await user input.
4. User playing bewjewled selects two tiles that, when switched, make a match. (Sprint 1)
    * When the player selects their two tiles, the `updateBoard()` method in the `Engine` class will be called.
    * This method will first call `checkIsValid()` to make sure that the selected tiles are valid.
    * The `SwitchValidator` instance of `Validator` will switch the cells and call `matchesExist()` in the `OpenFinder` instance of `MatchFinder` to see if the switch would result in any matches.
    * Since the switch would result in matches, the switch is considered to be valid.
    * Engine then calls `removeCell()` on the cells that are identified by `identifyMatches()`. Once the matched cells are removed, then all the cells are shifted down to fill the spaces, and `addCellsToBoard()` in `NewCellAdder` is called to fill the grid again.
    * The `FillFromTop` instance of `NewCellAdder` fills in the top of the grid randomly with new cells.
    * Then `removeCell()` is called again on the cells found by `identifyMatches()`, so long as matches still exist in the grid.
    * Once matches no longer exist in the grid, control is handed back to `Player` to await user input.
5. User playing bewjewled puzzle selects two tiles that, when switched, make a match. (Sprint 1)
    * When the player selects their two tiles, the `updateBoard()` method in the `Engine` class will be called.
    * This method will first call `checkIsValid()` to make sure that the selected tiles are valid.
    * The `SwitchValidator` instance of `Validator` will switch the cells and call `matchesExist()` in the `OpenFinder` instance of `MatchFinder` to see if the switch would result in any matches.
    * Since the switch would result in matches, the switch is considered to be valid.
    * Engine then calls `removeCell()` on the cells that are identified by `identifyMatches()`. Once the matched cells are removed, then all the cells are shifted down to fill the spaces, and `addCellsToBoard()` in `NewCellAdder` is called to fill the grid again.
    * The `NoAdding` instance of `NewCellAdder` does not add any new cells to the grid.
    * Then `removeCell()` is called again on the cells found by `identifyMatches()`, so long as matches still exist in the grid.
    * Once matches no longer exist in the grid, control is handed back to `Player` to await user input.
6. User playing bewjewled endless and achieves a score that allows them to advance to the next level. (Sprint 2)
    *  When the player selects their two tiles, the `updateBoard()` method in the `Engine` class will be called.
    * This method will first call `checkIsValid()` to make sure that the selected tiles are valid.
    * The `SwitchValidator` instance of `Validator` will switch the cells and call `matchesExist()` in the `OpenFinder` instance of `MatchFinder` to see if the switch would result in any matches.
    * Since the switch would result in matches, the switch is considered to be valid.
    * Engine then calls `removeCell()` on the cells that are identified by `identifyMatches()`. When the cells are being removed, the points of the cells, added to the current score of the player, reach a threshold that allows the player to advance to the next level. This process stops and `Engine` lets `Player` know that the level of the game is being incremented.
7. User selects that the game they want to play is the memory game.  (Sprint 1)
    * `Data` will give `Engine` the attributes of the current game as Strings.
    * `Engine` will initialize the appropriate `Validator`, `MatchFinder`, `NewCellAdder`, and `GridCreator` using the Strings and reflection.
    * `Engine` will then `setUpGrid()` and then give control to `Player` to await user input.
8. User playing candy crush makes a switch that creates a pair that includes a power up cell (double points). (Sprint 2)
    * When the player selects their two tiles, the `updateBoard()` method in the `Engine` class will be called.
    * This method will first call `checkIsValid()` to make sure that the selected tiles are valid.
    * The `SwitchValidator` instance of `Validator` will switch the cells and call `matchesExist()` in the `OpenFinder` instance of `MatchFinder` to see if the switch would result in any matches.
    * Since the switch would result in matches, the switch is considered to be valid.
    * Engine then calls `removeCell()` on the cells that are identified by `identifyMatches()`. For the cell that has the power-up, when `removeCell()` is called, it will call `applyPowerUp()` in `PowerUps`, which will result in that speciifc power-up being applied (whether the whole row of cells is deleted, or the player gets double the points).
    * Once the matched cells are removed, power-up applied, then all the cells are shifted down to fill the spaces, and `addCellsToBoard()` in `NewCellAdder` is called to fill the grid again.
    * The `FillFromTop` instance of `NewCellAdder` adds new cells to the top of the grid.
    * Then `removeCell()` is called again on the cells found by `identifyMatches()`, so long as matches still exist in the grid.
    * Once matches no longer exist in the grid, control is handed back to `Player` to await user input.


## Natalie use cases:
1. Program initiated, Controller creates initial window with a Player, gives Player lambda to call Controller function to build new window
2. Login data entered and checked against user "database" in Data for valid login through Controller
3. Login data entered to create new entry in user "database" in Data through Controller
4. User selects initial game type in Player, Controller asks Data whether user has a saved game state for this type and tells this to Player
6. User opts to start new game OR does not have saved game state for this game type, Controller gets appropriate data from Data and builds new Engine with this data using default initial config (overwrites existing Engine if not initial game)
7. User opts to continue existing game, Controller gets appropriate data from Data and builds new Engine with this data using initial config from user's saved game state (overwrites existing Engine if not initial game)
8. New Engine passes its Grid to the Player after it is initiated
9. Player builds a new window, Controller builds a new window with a new Player, gives Player lambda to call Controller function to build new window
10. Player saves game state, Controller passes game state to Data to save with user's profile