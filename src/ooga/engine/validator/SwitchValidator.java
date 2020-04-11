package ooga.engine.validator;

import ooga.engine.Cell;
import ooga.engine.GameProgressManager;

import java.util.List;

public class SwitchValidator extends Validator{

    public SwitchValidator(){
        super();
    }
    @Override
    public boolean checkIsValid(List<Cell> selected, GameProgressManager myProgressManager) {
        if (selected.size()>2) return false;

        Cell first = selected.get(0);
        System.out.println("State of the first cell: " + first.getMyState());
        Cell second = selected.get(1);
        System.out.println("State of the second cell: " + second.getMyState());
        if (!first.isNeighbor(second)) return false;

        first.swap(second);
        System.out.println("State of the first cell after swap: " + first.getMyState());
        System.out.println("State of the second cell after swap: " + second.getMyState());
        return true;
    }

}
