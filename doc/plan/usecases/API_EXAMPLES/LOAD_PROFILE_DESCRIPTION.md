## Use Case: Loading a Profile

In this case, it is necessary to have communication
between the Data and Player parts. This communication
will be done via the Controller in the steps
described below:

1. User enters username and password into frontend text boxes
2. When they click go, the method getAndLoadProfile() in the Controller is called 
3. This method calls the getPlayerProfile() method on the data object
4. Do stuff to parse playerXML doc and create a playerDataObject
5. The DataObject containing the attributes of this profile as Strings is given to the frontend 
6. Controller calls the loadProfile() 


