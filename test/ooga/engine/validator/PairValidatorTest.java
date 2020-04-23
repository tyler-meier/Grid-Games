package ooga.engine.validator;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ooga.engine.Cell;
import ooga.engine.GameProgressManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PairValidatorTest {
    private static Map<String, String> attributes = new HashMap<>() {{
        put("Level", "0");
        put("Score", "0");
        put("TargetScore", "30");
        put("LossStat", "MovesUsed");
        put("MovesUsed", "10");
    }};

    @org.junit.jupiter.api.Test
    void checkIsValid() {
        PairValidator pairValidator = new PairValidator();
        pairValidator.setMyProgressManager(new GameProgressManager(attributes));
        List<Cell> cells = new ArrayList<>();
        Cell cellA = new Cell(1, true, 1);
        Cell cellB = new Cell(2, false, 1);
        Cell cellC = new Cell(2, false, 1);
        Cell cellD = new Cell(3, false, 1);
        cells.add(cellA);
        assertFalse(pairValidator.checkIsValid(cells));
        cells.add(cellB);
        assertFalse(pairValidator.checkIsValid(cells));
        cells.remove(cellA);
        cells.add(cellC);
        assertTrue(pairValidator.checkIsValid(cells));
        cells.remove(cellB);
        cells.add(cellD);
        assertFalse(pairValidator.checkIsValid(cells));
    }
}