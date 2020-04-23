package ooga.engine.gridCreator;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class PairGridCreatorTest {
    private int rows = 5, columns = 5, lives = 6, minState=1;
    private Map<String, String> gameAttributes = new HashMap<>() {{
        put("numRows", "5");
        put("numColumns", "5");
        put("LivesLeft", "6");
    }};

    @Test
    void buildInitialConfig() {
        int maxState = 8, numSelected = 3;
        int[] counts = new int[maxState+1];
        PairGridCreator pgc = new PairGridCreator();
        pgc.setEngineAttributes(maxState, numSelected);
        pgc.getInitialConfig(gameAttributes);
        //test appropriate grid built
        assert(pgc.myNumRows==rows && pgc.myNumColumns==columns && pgc.myNumLives==lives);
        assert(pgc.initialConfig.length==rows && pgc.initialConfig[0].length==columns);
        //test states are in right range
        int maxStateFound=-1, minStateFound=-1;
        for (int r=0; r<pgc.initialConfig.length; r++){
            for (int c=0; c<pgc.initialConfig[0].length; c++){
                if (pgc.initialConfig[r][c]>maxStateFound || maxStateFound==-1) maxStateFound=pgc.initialConfig[r][c];
                if (pgc.initialConfig[r][c]<minStateFound || minStateFound==-1) minStateFound=pgc.initialConfig[r][c];
                counts[pgc.initialConfig[r][c]]++;
            }
        }
        assert(minStateFound>=minState && maxStateFound<=maxState);
        //test correct numbers of each state
        int numMisMatched = 0;
        for (Integer count:counts){
            if (count%numSelected!=0) numMisMatched++;
        }
        assert(numMisMatched==0 || numMisMatched==1);
    }
}