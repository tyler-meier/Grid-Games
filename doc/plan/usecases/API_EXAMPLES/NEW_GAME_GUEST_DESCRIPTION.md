## Use Case: Create a new game as a guest user (not logged in)

In this case, it is necessary to have communication
between the Data, Player and Engine parts. This communication
will be done via the Controller in the steps
described below:

1. User selects gameType and presses go in Player
2. Controller calls buildNewEngine with the current Player and with savedGame set to false
3. Controller calls data.getEngineAttributes() on the gameType from player.getGameType()
4. Data uses the default configuration found for that level (assigned with a ResourceBundle)
5. Data creates a DataObject with attributes of the needed game engine
6. Initializes Engine using this information
7. Sets the grid, new move button (used to tell back end that user has made a move) and saveGameButton of the Player
8. Player opens up to a new screen to begin the game (not included in Controller, private method)

