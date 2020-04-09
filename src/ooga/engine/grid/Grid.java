package ooga.engine.grid;

import javafx.beans.property.*;
import ooga.engine.Cell;
import ooga.engine.GameProgressManager;
import ooga.engine.matchFinder.MatchFinder;
import ooga.engine.validator.Validator;


import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Grid {
    private static final String NUM_SELECTED_PER_MOVE = "NumSelectedPerMove";
    private static final String ADD_NEW_CELLS = "AddNewCells";
    private static final String MAX_STATE_NUMBER = "MaxStateNumber";
    private static final String HAS_HIDDEN_CELLS = "HasHiddenCells";
    private static final String POINTS_PER_CELL = "PointsPerCell";
    private static final int BOMB_STATE = -1;
    private Cell[][] myGrid;
    private int numSelected = 0;
    private int numSelectedPerMove;
    private Validator myValidator;
    private MatchFinder myMatchFinder;
    private int score = 0;
    private boolean addNewCells;
    private int maxState;
    private boolean hasHiddenCells;
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
            hasHiddenCells = Boolean.parseBoolean(gameAttributes.get(HAS_HIDDEN_CELLS));
            pointsPerCell = Integer.parseInt(gameAttributes.get(POINTS_PER_CELL));
        } catch (Exception e){
            e.printStackTrace();
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

    /**
     * This method sets up the grid given the specified initial states of the cells.
     * @param initialStates
     */
    private void setupGridStates(int[][] initialStates){
        for (int r = 0; r<initialStates.length; r++){
            for (int c=0; c<initialStates[0].length; c++){
                if (getCell(r, c)==null){
                    myGrid[r][c] = new Cell(initialStates[r][c], hasHiddenCells, pointsPerCell);
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

    /**
     * This method will return the current grid with the updated states of all the cells.
     * This method will be so the frontend can get the updated grid from the backend.
     * @return
     */
    private void updateMyBoard(){
        moveInProgress.set(true);
        System.out.println("about to update grid" + moveInProgress);
        updateGrid();
        if (myProgressManager.isWin()) System.out.println("win"); // win action
        else if (myProgressManager.isLoss()) System.out.println("loss"); // loss action
        moveInProgress.set(false);
        System.out.println("done updating grid" + moveInProgress);
    }

    /**
     * This method updates the current game grid according to the move selected by the user.
     */
    public void updateGrid(){
        List<Cell> selectedCells = getSelectedCells();
        System.out.println("here is the state of first selected cell: " + selectedCells.get(0).getMyState());
        System.out.println("here is the state of second selected cell: " + selectedCells.get(1).getMyState());
        if(myValidator.checkIsValid(selectedCells)){
            System.out.println("found the cells to be valid");
            myProgressManager.incrementMoves();
            System.out.println("incremented moves in progress manager");
            List<Cell> matchedCells = new ArrayList<>();
            //TODO: ask TA if there is a better way to do this to avoid circular dependency
            if (numSelected>0)
                matchedCells.addAll(myMatchFinder.makeMatches(selectedCells, this));
            // TODO: need to find a way to increment score for memory game, below will not add memory cells to arraylist
            if (hasHiddenCells){
                //FIXME: added line below to account for incrementing score for memory game
                matchedCells.addAll(selectedCells);
                matchedCells.addAll(myMatchFinder.makeMatches(this));
            }
            while (matchedCells.size()>0){
                if (hasHiddenCells) openMatchedCells(matchedCells);
                else deleteMatchedCells(matchedCells);
                matchedCells.addAll(myMatchFinder.makeMatches(this));
            }
        }
        else{
            System.out.println("found the cells to not be valid");
        }
    }

    private List<Cell> getSelectedCells(){
        List<Cell> selected = new ArrayList<>();
        for (int r = 0; r<getRows(); r++){
            for (int c=0; c<getCols(); c++){
                if (getCell(r,c).isSelected().get()) selected.add(getCell(r,c));
            } }
        return selected;
    }

    private void openMatchedCells(List<Cell> matchedCells){
        for (Cell cell:matchedCells) {
            cell.isOpen().set(true);
            if (cell.getMyState() == BOMB_STATE) myProgressManager.incrementLives();
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
        for (int row = 1; row<getRows(); row++) {
            Cell cell = getCell(row, col);
            if (cell.getMyState()==-1) cell.randomize(maxState);
        }
    }
}
