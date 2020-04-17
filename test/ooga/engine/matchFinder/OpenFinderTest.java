package ooga.engine.matchFinder;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ooga.engine.Cell;
import ooga.engine.grid.Grid;
import ooga.engine.validator.PairValidator;
import ooga.engine.validator.SwitchValidator;
import ooga.engine.validator.Validator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OpenFinderTest {
    private int[][] initialConfig = { {1,6, 5, 4, 2}, {5, 1, 3, 1, 5}, {4, 4, 4, 6, 1}, {2, 3, 4, 3, 2}};
    private int[][] initialConfig2 = { {1,6, 5, 4, 2}, {5, 1, 3, 1, 5}, {4, 4, 6, 6, 1}, {2, 3, 4, 3, 2}};
    private int[][] initialConfig3 = { {5,6, 5, 4, 2}, {5, 1, 3, 1, 5}, {5, 4, 6, 6, 1}, {5, 3, 4, 3, 2}};
    private Map<String, String> gameAttributes = new HashMap<>() {{
        put("NumSelectedPerMove", "2");
        put("AddNewCells", "true");
        put("MaxStateNumber", "6");
        put("HasHiddenCells", "true");
        put("PointsPerCell", "5");
        put("SecondsOpen", "5");
    }};

    @Test
    void makeMatches() {
        Validator validator = new SwitchValidator();
        MatchFinder matchFinder = new OpenFinder();
        StringProperty errorMessage = new SimpleStringProperty();
        Grid myGrid = new Grid(gameAttributes, validator, matchFinder, errorMessage);
        myGrid.setNewGame(initialConfig, gameAttributes, null);
        // now we have a grid with states and everything
        List<Cell> ret = new ArrayList<>();
        ret.add(myGrid.getCell(2,0));
        ret.add(myGrid.getCell(2,1));
        ret.add(myGrid.getCell(2,2));
        assertEquals(ret, matchFinder.makeMatches(myGrid));
        ret.clear();
        myGrid.setNewGame(initialConfig2, gameAttributes, null);
        assertEquals(ret, matchFinder.makeMatches(myGrid));
        ret.add(myGrid.getCell(1,0));
        ret.add(myGrid.getCell(2,0));
        ret.add(myGrid.getCell(3,0));
        ret.add(myGrid.getCell(0,0));
        myGrid.setNewGame(initialConfig3, gameAttributes, null);
    }

}