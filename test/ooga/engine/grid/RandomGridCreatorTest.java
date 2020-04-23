package ooga.engine.grid;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomGridCreatorTest {

    @Test
    void buildInitialConfig() {
        int rows = 3, cols=3, maxState = 8, numSelected = 2, lives = 0;
        RandomGridCreator rgc = new RandomGridCreator(rows, cols, maxState, numSelected, lives);
        assert(rgc.initialConfig.length==rows && rgc.initialConfig[0].length==cols);
        int maxStateFound=-1, minStateFound=-1;
        for (int r=0; r<rgc.initialConfig.length; r++){
            for (int c=0; c<rgc.initialConfig[0].length; c++){
                if (rgc.initialConfig[r][c]>maxStateFound || maxStateFound==-1) maxStateFound=rgc.initialConfig[r][c];
                if (rgc.initialConfig[r][c]<minStateFound || minStateFound==-1) minStateFound=rgc.initialConfig[r][c];
            }
        }
        assert(minStateFound>0 && maxStateFound<=maxState);
    }
}