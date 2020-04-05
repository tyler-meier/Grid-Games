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

    public void resetGrid(int[][] initialStates){
        setupGridStates(initialStates);
        score=0;
        numSelected=0;
    }

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

    public BooleanProperty getInProgressProperty() { return moveInProgress; }

    public void setValidator(Validator validator){
        myValidator = validator;
    }

    public void setMatchFinder(MatchFinder matchFinder){
        myMatchFinder = matchFinder;
    }

    public Cell getCell(int row, int col){
        if (row<0 || row>myGrid.length) return null;
        if (col<0 || col>myGrid[0].length) return null;
        return myGrid[row][col];
    }

    public int getRows() { return myGrid.length; }
    public int getCols() { return myGrid[0].length; }

    private void updateMyBoard(){
        moveInProgress.set(true);
        List<Cell> selectedCells = getSelectedCells();
        if(myValidator.checkIsValid(selectedCells)){
            List<Cell> matchedCells = new ArrayList<>();
            if (numSelected>0) matchedCells.addAll(myMatchFinder.makeMatches(selectedCells, this));
            if (hasHiddenCells) matchedCells.addAll(myMatchFinder.makeMatches(this));
            while (matchedCells.size()>0){
                //increment moves?
                if (hasHiddenCells) openMatchedCells(matchedCells);
                else deleteMatchedCells(matchedCells);
                matchedCells.addAll(myMatchFinder.makeMatches(this));
            }
        }
        moveInProgress.set(false);
    }

    private void openMatchedCells(List<Cell> matchedCells){
        for (Cell cell:matchedCells) {
            cell.isOpen().set(true);
            score += cell.getScore();
        }
    }

    private void deleteMatchedCells(List<Cell> matchedCells){
        for (Cell cell:matchedCells) {
            cell.cellState().set(-1);
            score += cell.getScore();
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
                    }
                }
            }
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

    private List<Cell> getSelectedCells(){
        List<Cell> selected = new ArrayList<>();
        for (int r = 0; r<myGrid.length; r++){
            for (int c=0; c<myGrid[0].length; c++){
                if (myGrid[r][c].isSelected().get()) selected.add(myGrid[r][c]);
            }
        }
        return selected;
    }
}
