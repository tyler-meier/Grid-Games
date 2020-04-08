package ooga.engine.grid;

import ooga.engine.Cell;
import ooga.engine.matchFinder.MatchFinder;
import ooga.engine.validator.Validator;

import java.util.ArrayList;
import java.util.List;

public class GridUpdater {
    private int numSelected;
    private boolean hasHiddenCells;
    private Grid myGrid;
    private MatchFinder myMatchFinder;
    private Validator myValidator;
    private boolean addNewCells;
    private int maxState;

    public GridUpdater(Validator validator, int numberSelected, MatchFinder matchFinder, boolean hiddenCells, Grid grid, boolean addNewCells, int maxState){
        this.hasHiddenCells = hiddenCells;
        this.myGrid = grid;
        this.myValidator = validator;
        this.numSelected = numberSelected;
        this.myMatchFinder = matchFinder;
        this.addNewCells = addNewCells;
        this.maxState = maxState;
    }

    /**
     * This method updates the current game grid according to the move selected by the user.
     */
    public void updateGrid(){
        List<Cell> selectedCells = getSelectedCells();
        if(myValidator.checkIsValid(selectedCells)){
            List<Cell> matchedCells = new ArrayList<>();
            if (numSelected>0) matchedCells.addAll(myMatchFinder.makeMatches(selectedCells, myGrid));
            if (hasHiddenCells) matchedCells.addAll(myMatchFinder.makeMatches(myGrid));
            while (matchedCells.size()>0){
                //increment moves?
                if (hasHiddenCells) openMatchedCells(matchedCells);
                else deleteMatchedCells(matchedCells);
                matchedCells.addAll(myMatchFinder.makeMatches(myGrid));
            }
        }
    }

    private List<Cell> getSelectedCells(){
        List<Cell> selected = new ArrayList<>();
        for (int r = 0; r<myGrid.getRows(); r++){
            for (int c=0; c<myGrid.getCols(); c++){
                if (myGrid.getCell(r,c).isSelected().get()) selected.add(myGrid.getCell(r,c));
            }
        }
        return selected;
    }

    private void openMatchedCells(List<Cell> matchedCells){
        for (Cell cell:matchedCells) {
            cell.isOpen().set(true);
            myGrid.incrementScore(cell.getScore());
        }
    }

    private void deleteMatchedCells(List<Cell> matchedCells){
        for (Cell cell:matchedCells) {
            cell.cellState().set(-1);
            myGrid.incrementScore(cell.getScore());
        }
        for (int col = 0; col<myGrid.getCols(); col++){
            for (int row = 1; row<myGrid.getRows(); row++){
                Cell cell = myGrid.getCell(row, col);
                if (cell.getMyState()==-1){
                    int nextRowAbove = row-1;
                    Cell above;
                    while (nextRowAbove>=0 && (above = myGrid.getCell(nextRowAbove, col)).getMyState()!=-1){
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
        for (int row = 1; row<myGrid.getRows(); row++) {
            Cell cell = myGrid.getCell(row, col);
            if (cell.getMyState()==-1) cell.randomize(maxState);
        }
    }
}