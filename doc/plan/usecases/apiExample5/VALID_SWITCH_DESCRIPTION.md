## Use Case: Making a valid switch of cells 

In this case, it is necessary to have communication
between the Player and Engine parts. This communication
will be done via the Controller in the steps
described below:

1. User selects two cells they want to check.
2. setNewMoveButton is triggered and, as a result, the Controller calls updateBoard() method in Engine.
3. Internal methods are called within the backend to validate the move and update the grid
4. Player calls getGrid() on the Engine to get the updated game grid.
