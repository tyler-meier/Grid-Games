package ooga.engine;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;

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

    public void removeCell(){

    }

}
