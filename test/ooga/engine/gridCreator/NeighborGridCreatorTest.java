package ooga.engine.gridCreator;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class NeighborGridCreatorTest {
    private static final int BOMB_STATE=9;
    private int rows = 5, columns = 5, lives = 6, minState = 0;
    private Map<String, String> gameAttributes = new HashMap<>() {{
        put("numRows", "5");
        put("numColumns", "5");
    }};

    @Test
    void buildInitialConfig() {
        int maxState = 9, numSelected = 3;
        NeighborGridCreator ngc = new NeighborGridCreator();
        ngc.setEngineAttributes(maxState, numSelected);
        ngc.getInitialConfig(gameAttributes);
        //test default setting of LivesLeft
        assert(ngc.myNumRows==rows && ngc.myNumColumns==columns && ngc.myNumLives==3);
        gameAttributes.put("LivesLeft", "6");
        ngc.getInitialConfig(gameAttributes);
        //test proper setup
        assert(ngc.myNumRows==rows && ngc.myNumColumns==columns && ngc.myNumLives==lives);
        assert(ngc.initialConfig.length==rows && ngc.initialConfig[0].length==columns);
        int maxStateFound=-1, minStateFound=-1, numBombs=0;
        for (int r=0; r<ngc.initialConfig.length; r++){
            for (int c=0; c<ngc.initialConfig[0].length; c++){
                if (ngc.initialConfig[r][c]>maxStateFound || maxStateFound==-1) maxStateFound=ngc.initialConfig[r][c];
                if (ngc.initialConfig[r][c]<minStateFound || minStateFound==-1) minStateFound=ngc.initialConfig[r][c];
                if (ngc.initialConfig[r][c]==BOMB_STATE) numBombs++;
            }
        }
        //test appropriate state range and number of bombs placed
        assert(minStateFound>=minState && maxStateFound<=maxState);
        assert(numBombs<3*lives && numBombs>=2*lives);
    }
}