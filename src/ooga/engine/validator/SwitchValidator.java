package ooga.engine.validator;

import ooga.engine.Cell;
import ooga.engine.GameProgressManager;

import java.util.List;

/**
 * This class represents the validator for games where
 * two cells are swapped and the resulting positions of the cells have
 * to make at least one match in the grid.
 * @author Natalie Novitsky and Tanvi Pabby.
 */
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
