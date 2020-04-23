package ooga.engine.grid;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NeighborGridCreatorTest {
    private static final int BOMB_STATE=9;

    @Test
    void buildInitialConfig() {
        int rows = 15, cols=15, maxState = 9, numSelected = 1, lives = 10;
        NeighborGridCreator ngc = new NeighborGridCreator(rows, cols, maxState, numSelected, lives);
        assert(ngc.initialConfig.length==rows && ngc.initialConfig[0].length==cols);
        int maxStateFound=-1, minStateFound=-1, numBombs=0;
        for (int r=0; r<ngc.initialConfig.length; r++){
            for (int c=0; c<ngc.initialConfig[0].length; c++){
                if (ngc.initialConfig[r][c]>maxStateFound || maxStateFound==-1) maxStateFound=ngc.initialConfig[r][c];
                if (ngc.initialConfig[r][c]<minStateFound || minStateFound==-1) minStateFound=ngc.initialConfig[r][c];
                if (ngc.initialConfig[r][c]==BOMB_STATE) numBombs++;
            }
        }
        assert(minStateFound>=0 && maxStateFound<=maxState);
        assert(numBombs>=lives && numBombs<2*lives);
    }
}