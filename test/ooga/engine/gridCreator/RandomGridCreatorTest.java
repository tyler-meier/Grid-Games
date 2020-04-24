package ooga.engine.gridCreator;

import ooga.engine.exceptions.InvalidDataException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;



class RandomGridCreatorTest {
    private int rows = 5, columns = 5, lives = 6, minState=1, maxState = 8, numSelected = 2;;
    private Map<String, String> gameAttributes = new HashMap<>() {{
        put("LivesLeft", "6");
    }};

    @Test
    void testValidSetup() {
        RandomGridCreator rgc = new RandomGridCreator();
        //test setup with valid data
        gameAttributes.put("numRows", "5");
        gameAttributes.put("numColumns", "5");
        rgc.setEngineAttributes(maxState, numSelected);
        rgc.getInitialConfig(gameAttributes);
        assert(rgc.myNumRows==rows && rgc.myNumColumns==columns && rgc.myNumLives==lives);
        assert(rgc.initialConfig.length==rows && rgc.initialConfig[0].length==columns);
    }

    @Test
    void testValidStateRange(){
        RandomGridCreator rgc = new RandomGridCreator();
        if (!gameAttributes.containsKey("numRows")){
            gameAttributes.put("numRows", "5");
            gameAttributes.put("numColumns", "5");
        }
        rgc.setEngineAttributes(maxState, numSelected);
        rgc.getInitialConfig(gameAttributes);
        int maxStateFound=-1, minStateFound=-1;
        for (int r=0; r<rgc.initialConfig.length; r++){
            for (int c=0; c<rgc.initialConfig[0].length; c++){
                if (rgc.initialConfig[r][c]>maxStateFound || maxStateFound==-1) maxStateFound=rgc.initialConfig[r][c];
                if (rgc.initialConfig[r][c]<minStateFound || minStateFound==-1) minStateFound=rgc.initialConfig[r][c];
            } }
        assert(minStateFound>=minState && maxStateFound<=maxState);
    }


    @Test
    void testBadInputs() {
        RandomGridCreator rgc = new RandomGridCreator();
        //test invalid data (no engine attributes)
        try{ rgc.getInitialConfig(gameAttributes);
            fail();
        } catch (InvalidDataException e){
            assert(e.toString().equals("bad data :(("));
        }
        //test invalid data (no rows or cols)
        rgc.setEngineAttributes(maxState, numSelected);
        try{ rgc.getInitialConfig(gameAttributes);
            fail();
        } catch (InvalidDataException e) {
            assert (e.toString().equals("bad data :(("));
        }
    }
}