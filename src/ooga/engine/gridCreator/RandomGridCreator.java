package ooga.engine.gridCreator;

import java.awt.*;
import java.util.Random;

/**
 * Class to build a grid with random state configuration.
 * @author natalie
 */
public class RandomGridCreator extends GridCreator {
    @Override
    protected void buildInitialConfig() {
        for (Point p:availableCells) initialConfig[p.x][p.y] = getRandomState();
    }

    private int getRandomState(){
        Random random = new Random();
        return random.nextInt(myMaxState)+1;
    }
}
