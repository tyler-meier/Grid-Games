## Use Case: Login and Loading a Profile

In this case, it is necessary to have communication
between the Data and Player parts. This communication
will be done via the Controller in the steps
described below:

1. User enters username and password into frontend text boxes in Player
2. When they click go, Player uses the functional interface UserLogin (passed in setup method from Controller to Player) to pass username and password to Data.getPlayerProfile() and grab a DataObject containing the user’s profile info
3. Within the Data method, Data checks the username and password against user “database” XML file
4. Profile match (username is in database and correct password-username combo) is found
5. Data builds DataObject with Strings holding profile info like name, highscores, preferences
6. This object is returned by the data.getPlayerProfile() method that is called within the UserLogin functional interface
7. Player saves DataObject and renders next screen (select game type) upon successful login



