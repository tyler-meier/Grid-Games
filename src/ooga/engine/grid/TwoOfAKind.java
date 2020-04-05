package ooga.engine.grid;

import ooga.engine.Cell;

public class TwoOfAKind extends GridCreator {

    public TwoOfAKind(int numRows, int numColumns, int numStatesOfCells) {
        super(numRows, numColumns, numStatesOfCells);
    }

    @Override
    public void makeMyCells() {
        int numStates = super.myNumStatesOfCells;
        for(int i = 1; i <= numStates; i++){
            int currentState = i;
            for(int z = 0; z < 2; z++){ //could change to make it x of a kind
                Cell curCell = new Cell(currentState);
                myCells.add(curCell);
            }
        }
    }

}
