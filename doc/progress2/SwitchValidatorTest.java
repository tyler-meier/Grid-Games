package ooga.engine.validator;

import ooga.engine.Cell;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SwitchValidatorTest {

    @Test
    void checkIsValid() {
        SwitchValidator switchValidator = new SwitchValidator();
        List<Cell> cells = new ArrayList<>();
        Cell cellA = new Cell(1, true, 1);
        Cell cellB = new Cell(2, false, 1);
        Cell cellC = new Cell(2, false, 1);
        cellA.setCoordinates(0,0);
        cellB.setCoordinates(0, 1);
        cells.add(cellA);
        cells.add(cellB);
        assertTrue(switchValidator.checkIsValid(cells, null));
        cellB.setCoordinates(10, 1);
        assertFalse(switchValidator.checkIsValid(cells, null));
        cells.add(cellC);
        assertFalse(switchValidator.checkIsValid(cells, null));
    }
}