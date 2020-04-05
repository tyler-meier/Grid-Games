## Use Case: Load a previous game (logged in)

This occurs when a user is already logged in 
and they have previously saved a game configuration.
This is only an option for profile users.

1. User selects gameType and presses go, then selects “Load Saved Game” in Player
2. Controller calls buildNewEngine with the current Player and with savedGame set to true
3. Controller calls data.getEngineAttributes() on the gameType from player.getGameType()
4. Data uses the default configuration found for that level (assigned with a ResourceBundle) and creates a DataObject with attributes of the needed game engine
5. Controller sets the game state of the DataObject by calling data.loadPreviousGame(player.getUsername(), type));
6. Controller initializes Engine using this information (edited DataObject)
7. Sets the grid, new move button (used to tell back end that user has made a move) and saveGameButton of the Player
8. Player opens up to a new screen to begin the game (not included in Controller, private method)
