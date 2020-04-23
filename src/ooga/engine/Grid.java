package ooga.engine;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import ooga.engine.exceptions.InvalidDataException;
import ooga.engine.matchFinder.MatchFinder;
import ooga.engine.validator.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class to manage the Grid for a game. Handles user selection of cells and updating grid after each move.
 * @author Tanvi and Natalie
 */
public class Grid {
    private static final String NUM_SELECTED_PER_MOVE = "NumSelectedPerMove";
    private static final String ADD_NEW_CELLS = "AddNewCells";
    private static final String MAX_STATE_NUMBER = "MaxStateNumber";
    private static final String NO_HIDDEN_CELLS = "NoHiddenCells";
    private static final String POINTS_PER_CELL = "PointsPerCell";
    private static final String SECONDS_OPEN = "SecondsOpen";
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
    private boolean setupPhase = false;
    private BooleanProperty moveInProgress = new SimpleBooleanProperty(false);
    private GameProgressManager myProgressManager;

    /**
     * Builds new Grid with given attributes.
     * @param gameAttributes engine attributes
     * @param validator to validate moves
     * @param matchFinder to find matches
     */
    public Grid(Map<String, String> gameAttributes, Validator validator, MatchFinder matchFinder){
        double secondsOpen;
        try {
            numSelectedPerMove = Integer.parseInt(gameAttributes.get(NUM_SELECTED_PER_MOVE));
            addNewCells = Boolean.parseBoolean(gameAttributes.get(ADD_NEW_CELLS));
            maxState = Integer.parseInt(gameAttributes.get(MAX_STATE_NUMBER));
            noHiddenCells = Boolean.parseBoolean(gameAttributes.get(NO_HIDDEN_CELLS));
            pointsPerCell = Integer.parseInt(gameAttributes.get(POINTS_PER_CELL));
            secondsOpen = Double.parseDouble(gameAttributes.get(SECONDS_OPEN));
        } catch (Exception e){
            throw new InvalidDataException();
        }
        myValidator = validator;
        myMatchFinder = matchFinder;
        myValidator.setTime(secondsOpen);
    }

    /**
     * This method resets the grid to have the specified states for the cells.
     * @param initialStates
     */
    public void setNewGame(int[][] initialStates, Map<String, String> gameAttributes, boolean[][] openCells){
        if (myGrid==null) myGrid = new Cell[initialStates.length][initialStates[0].length];
        setupGridStates(initialStates, openCells);
        myProgressManager = new GameProgressManager(gameAttributes);
        myValidator.setMyProgressManager(myProgressManager);
        numSelected=0;
    }

    /**
     * Clears matches built into initial config by auto generators.
     */
    public void clearInitialMatches(){
        setupPhase = true;
        List<Cell> initialMatches = new ArrayList<>(myMatchFinder.makeMatches(this));
        handleMatchedCells(initialMatches);
        setupPhase = false;
    }

    /**
     * This method updates the current game grid according to the move selected by the user.
     */
    public void updateGrid(){
        moveInProgress.set(true);
        List<Cell> selectedCells = getSelectedCells();
        if(myValidator.checkIsValid(selectedCells)){
            List<Cell> matchedCells = new ArrayList<>();
            findMatchedCells(matchedCells, selectedCells);
            handleMatchedCells(matchedCells);
        }
        moveInProgress.set(false);
        for (Cell cell:selectedCells) cell.toggleSelected();
    }

    /**
     * This method returns the states of the grid in the form of a 2D array.
     * @return
     */
    public int[][] getGridConfiguration(){
        int[][] gridStates = new int[myGrid.length][myGrid[0].length];
        for (int col = 0; col<getCols(); col++){
            for (int row = 0; row<getRows(); row++) {
                gridStates[row][col] = myGrid[row][col].getMyState();
            }
        }
        return gridStates;
    }

    /**
     * This method returns the open status of all cells in the grid, in the form of a 2D array.
     * @return
     */
    public boolean[][] getOpenCellConfiguration(){
        if (noHiddenCells) return null;
        boolean[][] openCells = new boolean[myGrid.length][myGrid[0].length];
        for (int col = 0; col<getCols(); col++){
            for (int row = 0; row<getRows(); row++) {
                openCells[row][col] = myGrid[row][col].isOpen().get();
            }
        }
        return openCells;
    }

    /**
     * This method returns the attributes of the current game in a string to string mapping.
     * @return
     */
    public Map<String, String> getGameAttributes() { return myProgressManager.getGameAttributes(); }

    /**
     * This method returns the attributes of the current game in a string to integer property mapping.
     * @return
     */
    public Map<String, IntegerProperty> getGameStats() { return myProgressManager.getGameStats(); }

    /**
     * This method returns whether or not the game has been lost.
     * @return
     */
    public BooleanProperty getLossStatus(){
        return myProgressManager.isLoss();
    }

    /**
     * This method returns whether or not the game has been won.
     * @return
     */
    public BooleanProperty getWinStatus(){
        return myProgressManager.isWin();
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

    public int getLevel() {
        if (myProgressManager == null) return -1;
        return myProgressManager.getLevel();
    }

    private void setupGridStates(int[][] initialStates, boolean[][] openCells){
        for (int r = 0; r<initialStates.length; r++){
            for (int c=0; c<initialStates[0].length; c++){
                checkAndSetCell(r,c, initialStates, openCells);
            }
        }
    }

    private void checkAndSetCell(int r, int c, int[][] initialStates, boolean[][] openCells){
        if (getCell(r, c)==null){
            boolean isOpen = (openCells!=null) ? openCells[r][c] : noHiddenCells;
            myGrid[r][c] = new Cell(initialStates[r][c], isOpen, pointsPerCell);
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

    private void findMatchedCells(List<Cell> matchedCells, List<Cell> selectedCells){
        if (noHiddenCells){
            matchedCells.addAll(myMatchFinder.makeMatches(selectedCells, this));
            if (matchedCells.size()>0 && myProgressManager!=null) myProgressManager.decrementMoves();
        } else {
            matchedCells.addAll(selectedCells);
            matchedCells.addAll(myMatchFinder.makeMatches(this));
        }
    }

    private void handleMatchedCells(List<Cell> matchedCells){
        while (matchedCells.size()>0){
            if (!noHiddenCells) openMatchedCells(matchedCells);
            else deleteMatchedCells(matchedCells);
            matchedCells.addAll(myMatchFinder.makeMatches(this));
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
            if (!setupPhase){
                if (cell.getMyState() == BOMB_STATE) myProgressManager.decrementLives();
                myProgressManager.updateScore(cell.getScore());
            }
        }
        matchedCells.clear();
    }

    private void deleteMatchedCells(List<Cell> matchedCells){
        for (Cell cell:matchedCells) {
            cell.cellState().set(-1);
            if (!setupPhase) myProgressManager.updateScore(cell.getScore());
        }
        for (int col = 0; col<getCols(); col++){
            for (int row = 1; row<getRows(); row++){
                Cell cell = getCell(row, col);
                swapUpCells(row, col, cell);
            }
            if (addNewCells || setupPhase) refillColumn(col);
        }
        matchedCells.clear();
    }

    private void swapUpCells(int row, int col, Cell cell){
        if (cell.getMyState()==-1){
            int nextRowAbove = row-1;
            Cell above;
            while (nextRowAbove>=0 && (above = getCell(nextRowAbove, col)).getMyState()!=-1){
                cell.swap(above);
                cell = above;
                nextRowAbove--;
            }
        }
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