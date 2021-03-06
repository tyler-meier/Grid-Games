package ooga.engine.validator;

import ooga.engine.Cell;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class represents the validator for the games where
 * the cells are 'opened' once they are selected and the open cells must
 * be matching.
 * @author Natalie Novitsky and Tanvi Pabby.
 */
public class PairValidator extends Validator {

    /**
     * Validates based on all selected cells being same state.
     * @param selected cells
     * @return true if all same state
     */
    @Override
    public boolean checkIsValid(List<Cell> selected) {
        for (Cell cell:selected) if (cell.isOpen().get()) return false;
        int matchState = selected.get(0).getMyState();
        boolean matched = true;
        for (Cell cell:selected){
            cell.isOpen().set(true);
            if (cell.getMyState()!=matchState) matched = false;
        }
        if (!matched){
            myProgressManager.decrementMoves();
            (new Timer()).schedule(new TimerTask() {
                @Override
                public void run() {
                    for (Cell cell:selected){
                        cell.isOpen().set(false);
                    } }
            }, time);
        }
        return matched;
    }
}
