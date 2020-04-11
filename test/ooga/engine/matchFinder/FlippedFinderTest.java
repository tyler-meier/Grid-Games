package ooga.engine.matchFinder;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ooga.engine.Cell;
import ooga.engine.grid.Grid;
import ooga.engine.validator.PairValidator;
import ooga.engine.validator.Validator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class FlippedFinderTest {
    private int[][] initialConfig = { {1,6, 5, 4, 2}, {5, 1, 3, 1, 5}, {4, 4, 6, 6, 1}, {2, 3, 4, 3, 2}};
    private Map<String, String> gameAttributes = new HashMap<>() {{
        put("NumSelectedPerMove", "2");
        put("AddNewCells", "false");
        put("MaxState", "6");
        put("HasHiddenCells", "false");
        put("PointsPerCell", "5");
    }};

    @Test
    void makeMatches() {
        Validator validator = new PairValidator();
        MatchFinder matchFinder = new FlippedFinder();
        StringProperty errorMessage = new SimpleStringProperty();
        Grid myGrid = new Grid(gameAttributes, validator, matchFinder, errorMessage);
    }
}