package ooga.engine.grid;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import ooga.engine.Cell;
import ooga.engine.matchFinder.MatchFinder;
import ooga.engine.validator.Validator;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private Cell[][] myGrid;
    private int numSelected = 0;
    private int numSelectedPerMove;
    private Validator myValidator;
    private MatchFinder myMatchFinder;
    private int score = 0;
    private boolean addNewCells;
    private int maxState;
    private BooleanProperty moveInProgress = new SimpleBooleanProperty(false);
    private boolean hasHiddenCells;

    public Grid(int[][] initialStates, int numSelectedPerMove, boolean addNewCells, int maxStateNumber, boolean hasHiddenCells){
        this.numSelectedPerMove = numSelectedPerMove;
        this.addNewCells = addNewCells;
        this.maxState = maxStateNumber;
        this.hasHiddenCells = hasHiddenCells;
        myGrid = new Cell[initialStates.length][initialStates[0].length];
        setupGridStates(initialStates);
    }

    /**
     * This method resets the grid to have the specified states for the cells.
     * @param initialStates
     */
    public void resetGrid(int[][] initialStates){
        setupGridStates(initialStates);
        score=0;
        numSelected=0;
    }

    /**
     * This method returns the current score of the player.
     * @return
     */
    public int getMyScore(){
        return score;
    }

    /**
     * This method sets up the grid given the specified initial states of the cells.
     * @param initialStates
     */
    private void setupGridStates(int[][] initialStates){
        for (int r = 0; r<initialStates.length; r++){
            for (int c=0; c<initialStates[0].length; c++){
                if (getCell(r, c)==null){
                    myGrid[r][c] = new Cell(initialStates[r][c], hasHiddenCells);
                    myGrid[r][c].setSelectionChangeListener(increment -> {
                        numSelected += increment? 1 : -1;
                        if (numSelected==numSelectedPerMove) updateMyBoard();
                    });
                } else {
                    getCell(r, c).cellState().set(initialStates[r][c]);
                    getCell(r, c).isOpen().set(hasHiddenCells);
                }
            }
        }
    }

    /**
     * This method returns the value of the 'move in progress" property.
     * This property indicates whether or not a move is current being handled by the engine,
     * letting Player know if it needs to wait before receiving user input.
     * @return
     */
    public BooleanProperty getInProgressProperty() { return moveInProgress; }

    /**
     * This method sets the Validator for the current game.
     * @param validator
     */
    public void setValidator(Validator validator){
        myValidator = validator;
    }

    /**
     * This method sets the MatchFinder for the current game.
     * @param matchFinder
     */
    public void setMatchFinder(MatchFinder matchFinder){
        myMatchFinder = matchFinder;
    }

    /**
     * This method returns the Cell object in the specified row and column.
     * @param row
     * @param col
     * @return
     */
    public Cell getCell(int row, int col){
        if (row<0 || row>myGrid.length) return null;
        if (col<0 || col>myGrid[0].length) return null;
        return myGrid[row][col];
    }

    /**
     * Returns the number of rows
     * @return
     */
    public int getRows() { return myGrid.length; }

    /**
     * Returns the number of columns
     * @return
     */
    public int getCols() { return myGrid[0].length; }

    public void incrementScore(int scoreToAdd){
        score = score + scoreToAdd;
    }

    /**
     * This method will return the current grid with the updated states of all the cells.
     * This method will be so the frontend can get the updated grid from the backend.
     * @return
     */
    private void updateMyBoard(){
        moveInProgress.set(true);
        GridUpdater myGridUpdater = new GridUpdater(myValidator, numSelected, myMatchFinder,
                hasHiddenCells, this, addNewCells, maxState);
        myGridUpdater.updateGrid();
        moveInProgress.set(false);
    }
}
