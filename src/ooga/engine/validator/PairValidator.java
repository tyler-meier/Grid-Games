package ooga.engine.validator;

import ooga.engine.Cell;
import ooga.engine.matchFinder.MatchFinder;

public class PairValidator extends Validator {

    public PairValidator(){
        super();
    }

    @Override
    public boolean checkIsValid(Cell[][] grid, MatchFinder myMatchFinder) {
        // set the state of the two selected cells to open
        if(myMatchFinder.matchesExist(grid)){
            return true;
        }
        // set the state of the two selected cells to close
        return false;
    }
}
