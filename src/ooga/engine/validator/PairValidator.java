package ooga.engine.validator;

import ooga.engine.Cell;
import ooga.engine.matchFinder.MatchFinder;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

//        try { Thread.sleep(4000);
//        } catch (Exception e) {
//            System.out.println("timer issue"); }


        //wait 5 seconds
        for (Cell cell:selected){
            if (!matched) cell.isOpen().set(false);
        }
        return matched;
    }
}
