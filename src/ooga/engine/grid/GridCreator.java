package ooga.engine.grid;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GridCreator {
    protected int myNumRows;
    protected int myNumColumns;
    protected int myMaxState;
    protected int myNumSelected;
    protected int myNumLives;
    protected List<Point> availableCells = new ArrayList<>();
    protected int[][] initialConfig;

    public GridCreator(int numRows, int numColumns, int maxState, int numSelectedPerMove, int totalLives){
        myNumRows = numRows;
        myNumColumns = numColumns;
        myMaxState = maxState;
        myNumSelected = numSelectedPerMove;
        myNumLives = totalLives;
        initialConfig = new int[numRows][numColumns];
        buildPointList();
    }

    private void buildPointList(){
        for (int r=0; r<myNumRows; r++){
            for (int c=0; c<myNumColumns; c++){
                Point point = new Point(r, c);
                availableCells.add(point);
            }
        }
    }

    protected abstract void buildInitialConfig();

    public void printGrid(){
        for (int r=0; r<myNumRows; r++){
            for (int c=0; c<myNumColumns; c++){
                System.out.print(initialConfig[r][c]+ " ");
            }
            System.out.println();
        }
    }

    /**
     * This method will create the initial configuration of the grid,
     * depending on what kind of initial configuration is specified in the data
     * file/ user interaction with the program - either random, from memory, or loaded.
     * @return
     */
    public  int[][] getInitialConfig(){ return initialConfig; }
}
