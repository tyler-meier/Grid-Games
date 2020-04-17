package ooga.engine.validator;

import ooga.engine.Cell;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PairValidator extends Validator {

    @Override
    public boolean checkIsValid(List<Cell> selected) {
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
            myProgressManager.decrementMoves();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    for (Cell cell:selected){
                        cell.isOpen().set(false);
                    }
                }
            }, time);
        }

        return matched;
    }
}
