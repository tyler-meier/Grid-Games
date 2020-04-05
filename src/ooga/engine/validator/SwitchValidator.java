package ooga.engine.validator;

import ooga.engine.Cell;
import ooga.engine.matchFinder.MatchFinder;

import java.util.List;

public class SwitchValidator extends Validator{
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
