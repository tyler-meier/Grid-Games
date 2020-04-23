package ooga.engine.gridCreator;

import ooga.engine.matchFinder.MatchFinder;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class GridCreator {
    private static final String LIVES_LEFT = "LivesLeft";
    private static final String NUM_ROWS = "numRows";
    private static final String NUM_COLS = "numColumns";
    private static final int DEFAULT_LIVES = 3;
    protected int myNumRows;
    protected int myNumColumns;
    protected int myMaxState;
    protected int myNumSelected;
    protected int myNumLives;
    protected List<Point> availableCells = new ArrayList<>();
    protected int[][] initialConfig;


    public void setEngineAttributes(int maxState, int numSelectedPerMove){
        myMaxState = maxState;
        myNumSelected = numSelectedPerMove;
    }

    private void buildPointList(){
        for (int r=0; r<myNumRows; r++){
            for (int c=0; c<myNumColumns; c++){
                Point point = new Point(r, c);
                availableCells.add(point);
            }
        }
    }

    /**
     * This method will create the initial configuration of the grid,
     * depending on what kind of initial configuration is specified in the data
     * file/ user interaction with the program - either random, from memory, or loaded.
     * @return
     */
    public  int[][] getInitialConfig(Map<String, String> gameAttributes){
        //TODO: error checking
        myNumRows = Integer.parseInt(gameAttributes.get(NUM_ROWS));
        myNumColumns = Integer.parseInt(gameAttributes.get(NUM_COLS));
        try{
            myNumLives = Integer.parseInt(gameAttributes.get(LIVES_LEFT));
        } catch (Exception e){
            myNumLives = DEFAULT_LIVES;
        }
        initialConfig = new int[myNumRows][myNumColumns];
        buildPointList();
        buildInitialConfig();
        return initialConfig; }

    protected abstract void buildInitialConfig();

    public void printGrid(){
        for (int r=0; r<myNumRows; r++){
            for (int c=0; c<myNumColumns; c++){
                System.out.print(initialConfig[r][c]+ " ");
            }
            System.out.println();
        }
    }


}
