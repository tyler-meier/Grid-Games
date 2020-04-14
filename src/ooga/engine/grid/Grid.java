package ooga.engine.grid;

import javafx.beans.property.*;
import ooga.engine.Cell;
import ooga.engine.GameProgressManager;
import ooga.engine.matchFinder.MatchFinder;
import ooga.engine.validator.Validator;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Grid {
    private static final String NUM_SELECTED_PER_MOVE = "NumSelectedPerMove";
    private static final String ADD_NEW_CELLS = "AddNewCells";
    private static final String MAX_STATE_NUMBER = "MaxStateNumber";
    private static final String NO_HIDDEN_CELLS = "HasHiddenCells";
    private static final String POINTS_PER_CELL = "PointsPerCell";
    private static final int BOMB_STATE = 9;
    private Cell[][] myGrid;
    private int numSelected = 0;
    private int numSelectedPerMove;
    private Validator myValidator;
    private MatchFinder myMatchFinder;
    private boolean addNewCells;
    private int maxState;
    private boolean noHiddenCells;
    private int pointsPerCell;
    private BooleanProperty moveInProgress = new SimpleBooleanProperty(false);
    private GameProgressManager myProgressManager;
    private StringProperty myErrorMessage = new SimpleStringProperty();

    public Grid(Map<String, String> gameAttributes, Validator validator, MatchFinder matchFinder, StringProperty errorMessage){
        myErrorMessage.bindBidirectional(errorMessage);
        //TODO: better error handling
        try {
            numSelectedPerMove = Integer.parseInt(gameAttributes.get(NUM_SELECTED_PER_MOVE));
            addNewCells = Boolean.parseBoolean(gameAttributes.get(ADD_NEW_CELLS));
            maxState = Integer.parseInt(gameAttributes.get(MAX_STATE_NUMBER));
            noHiddenCells = Boolean.parseBoolean(gameAttributes.get(NO_HIDDEN_CELLS));
            pointsPerCell = Integer.parseInt(gameAttributes.get(POINTS_PER_CELL));
        } catch (InvalidDataException e){
            myErrorMessage.set(e.toString());
        }
        myValidator = validator;
        myMatchFinder = matchFinder;
        myErrorMessage = errorMessage;
    }

    /**
     * This method resets the grid to have the specified states for the cells.
     * @param initialStates
     */
    public void setNewGame(int[][] initialStates, Map<String, String> gameAttributes){
        if (myGrid==null) myGrid = new Cell[initialStates.length][initialStates[0].length];
        setupGridStates(initialStates);
        try{
            myProgressManager = new GameProgressManager(gameAttributes);
        } catch (Exception e){
            myErrorMessage.set(e.toString());
        }
        numSelected=0;
    }

    public int[][] getGridConfiguration(){
        int[][] gridStates = new int[myGrid.length][myGrid[0].length];
        for (int col = 0; col<getCols(); col++){
            for (int row = 1; row<getRows(); row++) {
                gridStates[row][col] = myGrid[row][col].getMyState();
            }
        }
        return gridStates;
    }

    public Map<String, String> getGameAttributes() { return myProgressManager.getGameAttributes(); }
    public Map<String, IntegerProperty> getGameStats() { return myProgressManager.getGameStats(); }

    public BooleanProperty getLossStatus(){
        return myProgressManager.isLoss();
    }

    public BooleanProperty getWinStatus(){
        return myProgressManager.isWin();
    }

    /**
     * This method sets up the grid given the specified initial states of the cells.
     * @param initialStates
     */
    private void setupGridStates(int[][] initialStates){
        for (int r = 0; r<initialStates.length; r++){
            for (int c=0; c<initialStates[0].length; c++){
                if (getCell(r, c)==null){
                    myGrid[r][c] = new Cell(initialStates[r][c], noHiddenCells, pointsPerCell);
                    myGrid[r][c].setupSelection(increment -> {
                        numSelected += increment? 1 : -1;
                        if (numSelected==numSelectedPerMove) updateGrid();
                    }, moveInProgress);
                    myGrid[r][c].setCoordinates(r,c);
                } else {
                    getCell(r, c).cellState().set(initialStates[r][c]);
                    getCell(r, c).isOpen().set(noHiddenCells);
                }
            }
        }
    }

    /**
     * This method returns the Cell object in the specified row and column.
     * @param row
     * @param col
     * @return
     */
    public Cell getCell(int row, int col){
        if (row<0 || row>=myGrid.length) return null;
        if (col<0 || col>=myGrid[0].length) return null;
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


    /**
     * This method updates the current game grid according to the move selected by the user.
     */
    public void updateGrid(){
        moveInProgress.set(true);
        List<Cell> selectedCells = getSelectedCells();
         if(myValidator.checkIsValid(selectedCells, myProgressManager)){
            System.out.println("valid move");
            List<Cell> matchedCells = new ArrayList<>();

            //TODO: ask TA if there is a better way to do this to avoid circular dependency
            if (noHiddenCells){
                myProgressManager.decrementMoves();
                matchedCells.addAll(myMatchFinder.makeMatches(selectedCells, this));
                // here, if the swap did not result in matches, the size of matched cells will be zero
            } else {
                matchedCells.addAll(selectedCells); //we are adding the selected cells so that the points are accounted for
                matchedCells.addAll(myMatchFinder.makeMatches(this)); //minesweeper game
            }
            while (matchedCells.size()>0){
                if (!noHiddenCells) {
                    openMatchedCells(matchedCells);
                }
                else {
                    // here we are deleting the matched cells and re-felling the board
                    deleteMatchedCells(matchedCells);
                }
                // here we are looking at the board as a whole and adding matched cells
                matchedCells.addAll(myMatchFinder.makeMatches(this));
            }
         }
         else {
             System.out.println("invalid move");
         }
         moveInProgress.set(false);
         for (Cell cell:selectedCells) {
             cell.toggleSelected();
         }
    }

    private List<Cell> getSelectedCells(){
        List<Cell> selected = new ArrayList<>();
        for (int r = 0; r<getRows(); r++){
            for (int c=0; c<getCols(); c++){
                if (getCell(r,c).isSelected()) selected.add(getCell(r,c));
            } }
        return selected;
    }

    private void openMatchedCells(List<Cell> matchedCells){
        for (Cell cell:matchedCells) {
            cell.isOpen().set(true);
            if (cell.getMyState() == BOMB_STATE) myProgressManager.decrementLives();
            myProgressManager.updateScore(cell.getScore());
        }
        matchedCells.clear();
    }

    private void deleteMatchedCells(List<Cell> matchedCells){
        for (Cell cell:matchedCells) {
            cell.cellState().set(-1);
            myProgressManager.updateScore(cell.getScore());
        }
        for (int col = 0; col<getCols(); col++){
            for (int row = 1; row<getRows(); row++){
                Cell cell = getCell(row, col);
                if (cell.getMyState()==-1){
                    int nextRowAbove = row-1;
                    Cell above;
                    while (nextRowAbove>=0 && (above = getCell(nextRowAbove, col)).getMyState()!=-1){
                        cell.swap(above);
                        cell = above;
                        nextRowAbove--;
                    } } }
            if (addNewCells) refillColumn(col);
        }
        matchedCells.clear();
    }

    private void refillColumn(int col){
        for (int row = 0; row<getRows(); row++) {
            Cell cell = getCell(row, col);
            if (cell.getMyState()==-1){
                cell.randomize(maxState);
            }
        }
    }
}
