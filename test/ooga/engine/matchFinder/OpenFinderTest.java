package ooga.engine.matchFinder;

import ooga.data.Data;
import ooga.engine.Cell;
import ooga.engine.Grid;
import ooga.engine.validator.SwitchValidator;
import ooga.engine.validator.Validator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        Data data = new Data();
        Validator validator = new SwitchValidator();
        MatchFinder matchFinder = new OpenFinder();
        Map<String, String> engineAttributes = data.getEngineAttributes("CandyCrush");
        Map<String, String> gameAttributes = data.getGameLevelAttributes("Guest", "CandyCrush", 1);
        Grid myGrid = new Grid(engineAttributes, validator, matchFinder);
        myGrid.setNewGame(initialConfig, gameAttributes, null);
        // now we have a grid with states and everything
        List<Cell> ret = new ArrayList<>();
        ret.add(myGrid.getCell(2,0));
        ret.add(myGrid.getCell(2,1));
        ret.add(myGrid.getCell(2,2));
        assertEquals(ret, matchFinder.makeMatches(myGrid));
        ret.clear();
        // should return nothing since there are no matches
        myGrid.setNewGame(initialConfig2, gameAttributes, null);
        assertEquals(ret, matchFinder.makeMatches(myGrid));
        ret.add(myGrid.getCell(0,0));
        ret.add(myGrid.getCell(1,0));
        ret.add(myGrid.getCell(2,0));
        ret.add(myGrid.getCell(3,0));
        myGrid.setNewGame(initialConfig3, gameAttributes, null);
        assertEquals(ret, matchFinder.makeMatches(myGrid));
    }

}