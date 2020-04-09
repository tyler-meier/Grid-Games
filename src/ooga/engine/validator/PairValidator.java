package ooga.engine.validator;

import ooga.engine.Cell;
import ooga.engine.matchFinder.MatchFinder;

import java.util.List;

public class PairValidator extends Validator {

    public PairValidator(){
        super();
        System.out.println("Made a pair validator");
    }

    @Override
    public boolean checkIsValid(List<Cell> selected) {
        System.out.println("validating the cells");
        for (Cell cell:selected) {
            cell.isSelected().set(false);
            if (cell.isOpen().get()){
                System.out.println("One of the selected cells is already open....");
                return false;
            }
        }

        int matchState = selected.get(0).getMyState();
        boolean matched = true;
        for (Cell cell:selected){
            cell.isOpen().set(true);
            if (cell.getMyState()!=matchState) matched = false;
        }

        //wait 5 seconds
        for (Cell cell:selected){
            if (!matched) cell.isOpen().set(false);
        }
        System.out.println("open status of first selected cell: " + selected.get(0).isOpen());
        System.out.println("open status of second selected cell:  " + selected.get(1).isOpen());
        System.out.println("selected status of first selected cell:  " + selected.get(0).isSelected());
        System.out.println("selected status of second sleected cell:  " + selected.get(1).isSelected());
        return matched;
    }
}
