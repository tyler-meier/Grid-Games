package ooga.engine;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class represents a cell object, and the different actions of
 * a cell.
 * @author Natalie Novitsky and Tanvi Pabby.
 */
public class Cell {
    IntegerProperty myState = new SimpleIntegerProperty();
    BooleanProperty open = new SimpleBooleanProperty();
    BooleanProperty inProgress = new SimpleBooleanProperty();
    BooleanProperty selected = new SimpleBooleanProperty(false);
    int numPoints;
    int myRow;
    int myColumn;
    SelectedCellCounter myCounter;

    public Cell(int initialState, boolean isOpen, int points){
        myState.setValue(initialState);
        open.set(isOpen);
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

    /**
     * This method toggles (switches) the selected property of a cell
     */
    public void toggleSelected(){
        if (!inProgress.get()){
            selected.set(!selected.get());
            myCounter.changeCount(selected.get());
        }
    }

    /**
     * This method returns a boolean indicating whether or not a cell is considered to be selected.
     * @return
     */
    public BooleanProperty isSelected() {
        return selected;
    }

    /**
     * This method returns a boolean indicating if the cell is considered to be open or not.
     * @return
     */
    public BooleanProperty isOpen() {
        return open;
    }

    /**
     * This method returns the state of a cell.
     * @return
     */
    public IntegerProperty cellState() { return myState; }

    /**
     * This method returns the row number of a cell.
     * @return
     */
    public int getRow() { return myRow; }

    /**
     * This method returns the column number of a cell.
     * @return
     */
    public int getColumn() { return myColumn; }

    /**
     * This method determines whether or not two cells are considered to be neighbors.
     * @param cell
     * @return
     */
    public boolean isNeighbor(Cell cell) {
        return (Math.abs(cell.getRow()-myRow)==1 && cell.getColumn()==myColumn) ||
            (Math.abs(cell.getColumn()-myColumn)==1 && cell.getRow()==myRow);

    }

    /**
     * This method returns the score of the current game.
     * @return
     */
    public int getScore(){ return numPoints; }

    /**
     * This method swaps the position of two cells
     * @param cell
     */
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

    /**
     * Assigns a random number (between 1 and max state) for the state of a new cell.
     * @param maxState
     */
    public void randomize(int maxState){
        Random random = new Random();
        int randomInteger = random.nextInt(maxState)+1;
        myState.set(randomInteger);
    }

}
