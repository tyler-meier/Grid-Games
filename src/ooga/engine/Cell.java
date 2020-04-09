package ooga.engine;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Random;

/**
 * This class represents a cell object.
 */
public class Cell {
    IntegerProperty myState = new SimpleIntegerProperty();
    BooleanProperty open = new SimpleBooleanProperty();
    BooleanProperty inProgress = new SimpleBooleanProperty();
    boolean sel = false;
    int numPoints;
    String powerUp;
    int myRow;
    int myColumn;
    SelectedCellCounter myCounter;

    public Cell(int initialState, boolean isOpen, int points){
        myState.setValue(initialState);
        open.set(!isOpen);
        numPoints = points;
    }

    /**
     * This method sets the listener for checking if a cell has been selected by the user.
     * @param counter
     */
    public void setupSelection(SelectedCellCounter counter, BooleanProperty inProgress){
        myCounter = counter;
        this.inProgress.bind(inProgress);
    }

    public void toggleSelected(){
        if (!inProgress.get()){
            sel = !sel;
            myCounter.changeCount(sel);
        }
    }

    /**
     * This method returns a boolean indicating whether or not a cell is considered to be selected.
     * @return
     */
    public boolean isSelected() {
        return sel;
    }

    /**
     * This method returns a boolean indicating if the cell is considered to be open or not.
     * @return
     */
    public BooleanProperty isOpen() {
        return open;
    }

    public IntegerProperty cellState() { return myState; }

    public int getRow() { return myRow; }

    public int getColumn() { return myColumn; }

    public boolean isNeighbor(Cell cell) {
        return (Math.abs(cell.getRow()-myRow)==1 && cell.getColumn()==myColumn) ||
            (Math.abs(cell.getColumn()-myColumn)==1 && cell.getRow()==myRow);

    }
    public int getScore(){ return numPoints; }

    public void swap(Cell cell){
        Cell placeholder = new Cell(myState.get(), open.get(), numPoints);
        myState.set(cell.getMyState());
        open.set(cell.open.get());
        cell.cellState().set(placeholder.getMyState());
        cell.isOpen().set(placeholder.open.get());
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
     * This method returns the state of a cell.
     * @return
     */
    public int getMyState(){
        return myState.get();
    }

    public void randomize(int maxState){
        Random random = new Random();
        // random number 1 through maxState inclusive
        int randomInteger = random.nextInt(maxState)+1;
        myState.set(randomInteger);
    }

}
