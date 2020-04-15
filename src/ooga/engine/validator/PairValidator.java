package ooga.engine.validator;

import ooga.engine.Cell;
import ooga.engine.GameProgressManager;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PairValidator extends Validator {

    @Override
    public boolean checkIsValid(List<Cell> selected, GameProgressManager myProgressManager) {
        myProgressManager.decrementMoves();
        for (Cell cell:selected) {
            if (cell.isOpen().get()){
                return false;
            }
        }
        int matchState = selected.get(0).getMyState();
        boolean matched = true;
        for (Cell cell:selected){
            cell.isOpen().set(true);
            if (cell.getMyState()!=matchState) matched = false;
        }

        if (!matched){
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    for (Cell cell:selected){
                        cell.isOpen().set(false);
                    }
                }
            }, 2000);
        }

        return matched;
    }
}
