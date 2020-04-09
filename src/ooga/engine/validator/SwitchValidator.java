package ooga.engine.validator;

import ooga.engine.Cell;

import java.util.List;

public class SwitchValidator extends Validator{

    public SwitchValidator(){
        super();
    }
    @Override
    public boolean checkIsValid(List<Cell> selected) {
        if (selected.size()>2) return false;

        Cell first = selected.get(0);
        Cell second = selected.get(1);
        if (!first.isNeighbor(second)) return false;

        first.swap(second);
        return true;
    }

}
