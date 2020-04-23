package ooga.engine.gridCreator;

import java.awt.*;
import java.util.Random;

/**
 * Class to build random grid based on neighbor states, used for Minesweeper.
 * @author natalie
 */
public class NeighborGridCreator extends GridCreator {
    private static final int BOMB_STATE = 9;

    @Override
    protected void buildInitialConfig() {
        addBombs();
        Random random = new Random();
        while(availableCells.size()>0){
            Point nextPoint = availableCells.remove(random.nextInt(availableCells.size()));
            initialConfig[nextPoint.x][nextPoint.y] = countNeighboringBombs(nextPoint);
        }
        printGrid();
    }

    private int countNeighboringBombs(Point p){
        int count = 0;
        if (p.x>0){
            if (p.y>0 && initialConfig[p.x-1][p.y-1]==BOMB_STATE) count++;
            if (p.y<myNumColumns-1 && initialConfig[p.x-1][p.y+1]==BOMB_STATE) count++;
            if (initialConfig[p.x-1][p.y]==BOMB_STATE) count++;
        }
        if (p.x<myNumColumns-1){
            if (p.y>0 && initialConfig[p.x+1][p.y-1]==BOMB_STATE) count++;
            if (p.y<myNumColumns-1 && initialConfig[p.x+1][p.y+1]==BOMB_STATE) count++;
            if (initialConfig[p.x+1][p.y]==BOMB_STATE) count++;
        }
        if (p.y>0 && initialConfig[p.x][p.y-1]==BOMB_STATE) count++;
        if (p.y<myNumColumns-1 && initialConfig[p.x][p.y+1]==BOMB_STATE) count++;
        return count;
    }

    private void addBombs(){
        Random random = new Random();
        int numBombs = random.nextInt(myNumLives) + 2*myNumLives;
        for (int i=0; i<numBombs; i++){
            if (availableCells.size()<1) break;
            Point nextPoint = availableCells.remove(random.nextInt(availableCells.size()));
            initialConfig[nextPoint.x][nextPoint.y] = BOMB_STATE;
        }
    }
}
