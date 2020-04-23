package ooga.engine;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Random;

/**
 * This class represents a cell object, and the different actions of
 * a cell.
 * @author Natalie Novitsky and Tanvi Pabby.
 */
public class Cell {
    private IntegerProperty myState = new SimpleIntegerProperty();
    private BooleanProperty open = new SimpleBooleanProperty();
    private BooleanProperty inProgress = new SimpleBooleanProperty();
    private BooleanProperty selected = new SimpleBooleanProperty(false);
    private SelectedCellCounter myCounter;
    int numPoints;
    int myRow;
    int myColumn;

    /**
     * Builds new Cell for a Grid.
     * @param initialState of cell
     * @param isOpen whether cell starts open/not hidden
     * @param points per cell
     */
    public Cell(int initialState, boolean isOpen, int points){
        myState.setValue(initialState);
        open.set(isOpen);
        numPoints = points;
    }

    /**
     * This method sets the listener for checking if a cell has been selected by the user.
     * @param counter functional interface to increment selected cell counter in Grid
     * @param inProgress property to prevent selecting a cell while move is ongoing
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
     * @return selected property
     */
    public BooleanProperty isSelected() {
        return selected;
    }

    /**
     * This method returns a boolean indicating if the cell is considered to be open or not.
     * @return open property
     */
    public BooleanProperty isOpen() {
        return open;
    }

    /**
     * This method returns the state of a cell.
     * @return state property
     */
    public IntegerProperty cellState() { return myState; }

    /**
     * This method returns the row number of a cell.
     * @return cell row
     */
    public int getRow() { return myRow; }

    /**
     * This method returns the column number of a cell.
     * @return cell column
     */
    public int getColumn() { return myColumn; }

    /**
     * This method determines whether or not two cells are considered to be neighbors.
     * @param cell neighbor
     * @return true if neighbor
     */
    public boolean isNeighbor(Cell cell) {
        return (Math.abs(cell.getRow()-myRow)==1 && cell.getColumn()==myColumn) ||
            (Math.abs(cell.getColumn()-myColumn)==1 && cell.getRow()==myRow);

    }

    /**
     * This method returns the points associated with the cell.
     * @return points
     */
    public int getScore(){ return numPoints; }

    /**
     * This method swaps the position of two cells
     * @param cell to be swapped with
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
     * @param row index
     * @param col index
     */
    public void setCoordinates(int row, int col) {
        this.myRow = row;
        this.myColumn = col;
    }

    /**
     * This method returns the state of a cell.
     * @return state as integer
     */
    public int getMyState(){
        return myState.get();
    }

    /**
     * Assigns a random number (between 1 and max state) for the state of a new cell.
     * @param maxState inclusive
     */
    public void randomize(int maxState){
        Random random = new Random();
        int randomInteger = random.nextInt(maxState)+1;
        myState.set(randomInteger);
    }

}
