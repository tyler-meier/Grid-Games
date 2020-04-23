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
    private int[][] initialConfig = {{0,0,2,9,3}, {1,1,4,9,9}};
    private Map<String, String> gameAttributes = new HashMap<>() {{
        put("NumSelectedPerMove", "2");
        put("AddNewCells", "false");
        put("MaxStateNumber", "6");
        put("NoHiddenCells", "false");
        put("PointsPerCell", "5");
        put("SecondsOpen", "5");
    }};

    @Test
    void makeMatches() {
        Validator validator = new PairValidator();
        MatchFinder matchFinder = new FlippedFinder();
        StringProperty errorMessage = new SimpleStringProperty();
        Grid myGrid = new Grid(gameAttributes, validator, matchFinder, errorMessage);
        myGrid.setNewGame(initialConfig,gameAttributes,null);
        myGrid.getCell(0,0).isOpen().set(true);
        List<Cell> ret = new ArrayList<>();
        ret.add(myGrid.getCell(1,0));
        ret.add(myGrid.getCell(0,1));
        ret.add(myGrid.getCell(1,1));
        assertEquals(ret, matchFinder.makeMatches(myGrid));
        // set the cells that were returned to open (this is what will happen in our code)
        for(Cell cell: ret){
            cell.isOpen().set(true);
        }
        ret.clear();
        // now we will call the method again with the new open cells - one being a 0
        ret.add(myGrid.getCell(0,2));
        ret.add(myGrid.getCell(1,2));
        assertEquals(ret, matchFinder.makeMatches(myGrid));
        // close all cells on the grid - should return an empty arraylist
        for(int i = 0; i < myGrid.getRows(); i++){
            for(int j = 0; j < myGrid.getCols(); j++){
                Cell cell = myGrid.getCell(i,j);
                cell.isOpen().set(false);
            }
        }
        ret.clear();
        assertEquals(ret, matchFinder.makeMatches(myGrid));
    }
}