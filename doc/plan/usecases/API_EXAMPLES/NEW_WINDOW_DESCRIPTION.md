## Use Case: New Window

This is the case if the User wants to 
have multiple windows open and run different
games at the same time. This is available to 
both logged in and guest users

1. User clicks on the New Window button in Player
2. This button is assigned with the action event so that when clicked, the Controller method newWindow() is called with a new stage
3. This will open a new window with a new Player and the ability to play its own games separate from the original window due to having different Players and Engines
