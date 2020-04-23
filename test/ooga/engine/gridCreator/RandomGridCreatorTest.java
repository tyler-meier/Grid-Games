package ooga.engine.gridCreator;

import ooga.engine.exceptions.InvalidDataException;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

import java.util.HashMap;
import java.util.Map;

class RandomGridCreatorTest {
    private int rows = 5, columns = 5, lives = 6, minState=1;
    private Map<String, String> gameAttributes = new HashMap<>() {{
        put("LivesLeft", "6");
    }};

    @Test
    void buildInitialConfig() throws Exception {
        int maxState = 8, numSelected = 2;
        boolean catchNoEngineAttributes = false, catchNoRowCol = false;
        RandomGridCreator rgc = new RandomGridCreator();
        //test invalid data (no engine attributes)
        try{ rgc.getInitialConfig(gameAttributes);
        } catch (InvalidDataException e){
            catchNoEngineAttributes = true; }
        assert(catchNoEngineAttributes);

        //test invalid data (no rows or cols)
        rgc.setEngineAttributes(maxState, numSelected);
        try{ rgc.getInitialConfig(gameAttributes);
        } catch (InvalidDataException e){
            catchNoRowCol = true; }
        assert(catchNoRowCol);

        //test setup with valid data
        gameAttributes.put("numRows", "5");
        gameAttributes.put("numColumns", "5");
        rgc.getInitialConfig(gameAttributes);
        assert(rgc.myNumRows==rows && rgc.myNumColumns==columns && rgc.myNumLives==lives);
        assert(rgc.initialConfig.length==rows && rgc.initialConfig[0].length==columns);

        //test cells are in appropriate state range
        int maxStateFound=-1, minStateFound=-1;
        for (int r=0; r<rgc.initialConfig.length; r++){
            for (int c=0; c<rgc.initialConfig[0].length; c++){
                if (rgc.initialConfig[r][c]>maxStateFound || maxStateFound==-1) maxStateFound=rgc.initialConfig[r][c];
                if (rgc.initialConfig[r][c]<minStateFound || minStateFound==-1) minStateFound=rgc.initialConfig[r][c];
            } }
        assert(minStateFound>=minState && maxStateFound<=maxState);
    }
}