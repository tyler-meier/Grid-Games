package ooga.engine;

/**
 * This class represents a cell object.
 */
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

    /**
     * This method sets the coordinates of a cell.
     * @param row
     * @param col
     */
    public void setCoordinates(int row, int col) {
        this.myRow = row;
        this.myColumn = col;
    }

    /**
     * This method returns the coordinates of a cell.
     * @return
     */
    public int[] getCoordinates(){
        int x = this.myRow;
        int y = this.myColumn;
        int[] ret = new int[]{x,y};
        return ret;
    }

    /**
     * This method returns the state of a cell.
     * @return
     */
    public int getMyState(){
        return myState;
    }

    public void removeCell(){

    }

}
