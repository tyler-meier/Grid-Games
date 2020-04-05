package ooga.engine;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;

import java.util.Random;

/**
 * This class represents a cell object.
 */
public class Cell {
    IntegerProperty myState = new SimpleIntegerProperty();
    BooleanProperty open = new SimpleBooleanProperty();
    BooleanProperty selected = new SimpleBooleanProperty();
    int numPoints;
    String powerUp;
    int myRow;
    int myColumn;

    public Cell(int initialState, boolean isOpen){ // will have to do something to account for open/closed and number of points
        myState.setValue(initialState);
        open.set(isOpen);
        selected.set(false);
    }

    public void setSelectionChangeListener(SelectedCellCounter counter){
        selected.addListener((o, oldv, newv) -> counter.changeCount(selected.get()));
    }

    public BooleanProperty isSelected() { return selected; }
    public BooleanProperty isOpen() { return open; }
    public IntegerProperty cellState() { return myState; }
    public int getRow() { return myRow; }
    public int getColumn() { return myColumn; }
    public boolean isNeighbor(Cell cell) {
        return (Math.abs(cell.getRow()-myRow)==1 && cell.getColumn()==myColumn) ||
            (Math.abs(cell.getColumn()-myColumn)==1 && cell.getRow()==myRow);

    }
    public int getScore(){ return numPoints; }

    public void swap(Cell cell){
        Cell placeholder = new Cell(myState.get(), open.get());
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
        return myState.get();
    }

    public void randomize(int maxState){
        Random random = new Random();
        // random number 1-maxState
        int randomInteger = random.nextInt(maxState)+1;
        myState.set(randomInteger);
    }

}
