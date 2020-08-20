## Use Case: Making a valid switch of cells 

In this case, it is necessary to have communication
between the Player and Engine parts. This communication
will be done via the Controller in the steps
described below:

1. User selects two cells they want to switch
2. Player knows that two selected cells constitutes a complete “move”, so it calls the event handler passed to it with the method Player.setNewMoveButton 
3. This triggers the method Engine.updateBoard()
4. Engine finds the selected cells and performs game functions on them (Internal calls in Engine validate the move, and generate an updated grid for the frontend)


