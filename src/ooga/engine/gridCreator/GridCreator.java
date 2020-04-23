package ooga.engine.gridCreator;

import ooga.engine.exceptions.InvalidDataException;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Abstract class for classes that generate random grids.
 * @author natalie
 */
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

    /**
     * Pass necessary engine attributes to grid creator.
     * @param maxState of cells
     * @param numSelectedPerMove to build "pairs"
     */
    public void setEngineAttributes(int maxState, int numSelectedPerMove){
        myMaxState = maxState;
        myNumSelected = numSelectedPerMove;
    }

    /**
     * This method will create the initial configuration of the grid,
     * depending on what kind of initial configuration is specified in the data
     * file/ user interaction with the program - either random, from memory, or loaded.
     * @return config
     */
    public int[][] getInitialConfig(Map<String, String> gameAttributes){
        try{
            myNumRows = Integer.parseInt(gameAttributes.get(NUM_ROWS));
            myNumColumns = Integer.parseInt(gameAttributes.get(NUM_COLS));
            if (gameAttributes.containsKey(LIVES_LEFT)) myNumLives = Integer.parseInt(gameAttributes.get(LIVES_LEFT));
            else myNumLives = DEFAULT_LIVES;
        } catch (Exception e){
            throw new InvalidDataException();
        }
        initialConfig = new int[myNumRows][myNumColumns];
        buildPointList();
        buildInitialConfig();
        return initialConfig; }

    protected abstract void buildInitialConfig();

    private void buildPointList(){
        for (int r=0; r<myNumRows; r++){
            for (int c=0; c<myNumColumns; c++){
                Point point = new Point(r, c);
                availableCells.add(point);
            }
        }
    }

}
