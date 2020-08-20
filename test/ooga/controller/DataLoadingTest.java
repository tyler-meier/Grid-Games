package ooga.controller;

import ooga.data.Data;
import ooga.data.exceptions.ParserException;
import ooga.engine.Engine;
import ooga.engine.exceptions.InvalidDataException;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DataLoadingTest {
    private String TEST = "test";
    private boolean[][] booleanGrid = new boolean[][]{{true, false}, {false, true}};
    private boolean[] falseArray = new boolean[]{false, false, false};

    @Test
    void testInvalidEngineData(){
        Data data = new Data();
        data.login(TEST, TEST);
        //tests invalid class used in reflection
        Map<String, String> myEngineAttributes = data.getEngineAttributes("Tester1");
        try{
            Engine engine = new Engine(myEngineAttributes);
            fail();
        } catch (InvalidDataException e){ assertEquals("bad data :((", e.toString()); }
        //tests unexpected noninteger values
        myEngineAttributes = data.getEngineAttributes("Tester2");
        try{
            Engine engine = new Engine(myEngineAttributes);
            fail();
        } catch (InvalidDataException e){ assertEquals("bad data :((", e.toString()); }
        //tests missing boolean tag
        myEngineAttributes = data.getEngineAttributes("Tester3");
        try{
            Engine engine = new Engine(myEngineAttributes);
            fail();
        } catch (InvalidDataException e){ assertEquals("bad data :((", e.toString()); }
    }

    @Test
    void testInvalidGameData(){
        Data data = new Data();
        data.login(TEST, TEST);
        Map<String, String> myEngineAttributes = data.getEngineAttributes("Tester4");
        Engine engine = new Engine(myEngineAttributes);
        assert(engine.getLevel()==-1);
        //test invalid grid
        Map<String, String> myGameAttributes = data.getGameLevelAttributes(TEST, "Tester1", engine.getLevel());
        try{
            int[][] initialStates = data.getGrid();
            fail();
        } catch (ParserException e){ assertEquals("Data file could not be read: row", e.getMessage()); }
        //noninteger value test
        myGameAttributes = data.getGameLevelAttributes(TEST, "Tester2", engine.getLevel());
        int[][] initialStates = data.getGrid();
        try{
            engine.setupGame(initialStates, myGameAttributes, null);
            fail();
        } catch (InvalidDataException e){ assertEquals("bad data :((", e.toString()); }
        //missing loss stat tag
        myGameAttributes = data.getGameLevelAttributes(TEST, "Tester3", engine.getLevel());
        try{
            engine.setupGame(initialStates, myGameAttributes, null);
            fail();
        } catch (InvalidDataException e){ assertEquals("bad data :((", e.toString()); }
    }

    @Test
    void testRandomGridGeneration(){
        Data data = new Data();
        data.login(TEST, TEST);
        Map<String, String> myEngineAttributes = data.getEngineAttributes("Tester4");
        Engine engine = new Engine(myEngineAttributes);
        assert(engine.getLevel()==-1);
        //test null grid passed from data
        Map<String, String> myGameAttributes = data.getGameLevelAttributes(TEST, "Tester4", engine.getLevel());
        int[][] initialStates = data.getGrid();
        assertNull(initialStates);
        engine.setupGame(initialStates, myGameAttributes, null);
        assertNotNull(engine.getGrid());
    }

    @Test
    void testLoadingOpenCellConfig(){
        Data data = new Data();
        data.login(TEST, TEST);
        Map<String, String> myEngineAttributes = data.getEngineAttributes("Tester4");
        Engine engine = new Engine(myEngineAttributes);
        assert(engine.getLevel()==-1);

        //test loading open cell config
        Map<String, String> myGameAttributes = data.getGameLevelAttributes(TEST, "Tester5", engine.getLevel());
        int[][] initialStates = data.getGrid();
        boolean[][] openCells = data.getOpenCells();
        assertNotNull(openCells);
        engine.setupGame(initialStates, myGameAttributes, openCells);
        assertNotNull(engine.getOpenCellConfiguration());
        for (int r=0; r<booleanGrid.length; r++){
            assertArrayEquals(booleanGrid[r], engine.getOpenCellConfiguration()[r]);
        }

        //test generating new open cell config when not given initial one
        myGameAttributes = data.getGameLevelAttributes(TEST, "Tester6", engine.getLevel());
        initialStates = data.getGrid();
        openCells = data.getOpenCells();
        assertNull(openCells);
        engine.setupGame(initialStates, myGameAttributes, openCells);
        assertNotNull(engine.getOpenCellConfiguration());
        for (int r=0; r<engine.getOpenCellConfiguration().length; r++){
            assertArrayEquals(falseArray, engine.getOpenCellConfiguration()[r]);
        }
    }
}