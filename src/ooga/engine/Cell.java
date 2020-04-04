package ooga.engine;

public class Cell {
    int myState;
    boolean open;
    int numPoints;
    String powerUp;
    int myRow;
    int myColumn;

    public Cell(int initialState){ // will have to do something to account for open/closed and number of points
        myState = initialState;
    }

    public void setCoordinates(int row, int col) {
        this.myRow = row;
        this.myColumn = col;
    }


}
