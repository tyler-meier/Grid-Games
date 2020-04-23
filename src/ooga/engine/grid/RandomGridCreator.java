package ooga.engine.grid;

import java.awt.*;
import java.util.Random;

public class RandomGridCreator extends GridCreator {
    public RandomGridCreator(int numRows, int numColumns, int maxState, int numSelectedPerMove, int totalLives) {
        super(numRows, numColumns, maxState, numSelectedPerMove, totalLives);
        buildInitialConfig();
    }

    @Override
    protected void buildInitialConfig() {
        for (Point p:availableCells) initialConfig[p.x][p.y] = getRandomState();
    }

    private int getRandomState(){
        Random random = new Random();
        return random.nextInt(myMaxState)+1;
    }
}
