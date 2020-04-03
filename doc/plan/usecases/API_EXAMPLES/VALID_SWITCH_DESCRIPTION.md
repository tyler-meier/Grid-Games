## Use Case: Making a valid switch of cells 

In this case, it is necessary to have communication
between the Player and Engine parts. This communication
will be done via the Controller in the steps
described below:

1. User selects two cells they want to check.
2. Through binding, when the user selects two cells, the player.setNewMoveButton 
   is triggered, which will call engine.updateBoard() on the backend, from the Controller.
3. Internal calls in Engine validate the move, and generate an updated grid for the frontend.

