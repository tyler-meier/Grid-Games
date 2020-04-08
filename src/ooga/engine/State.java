package ooga.engine;

import ooga.engine.grid.Grid;

/**
 * This class will represent the state of a game so that
 * the user can save and load a current state of their game.
 */
public class State {
    private Grid myGrid;
    private int myScore;
    private int myLevel;
    private int myTimer;

    public State(Grid grid, int score){
        myGrid = grid;
        myScore = score;
        //myLevel = level;
        //myTimer = timer;
    }


}
