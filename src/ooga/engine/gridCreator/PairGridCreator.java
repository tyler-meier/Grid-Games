package ooga.engine.gridCreator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PairGridCreator extends GridCreator{
    private List<Integer> availableStates = new ArrayList<>();


    @Override
    protected void buildInitialConfig() {
        fillAvailableStatesList();
        Random random = new Random();
        int state;
        while(availableCells.size()>0){
            state = availableStates.remove(random.nextInt(availableStates.size()));
            if (availableStates.size()<1) fillAvailableStatesList();
            for (int i = 0; i< myNumSelected; i++){
                Point nextPoint = availableCells.remove(random.nextInt(availableCells.size()));
                initialConfig[nextPoint.x][nextPoint.y] = state;
                if (availableCells.size()<1) break;
            }
        }
        printGrid();
    }

    private void fillAvailableStatesList(){
        for (int i=1; i<=myMaxState; i++) availableStates.add(i);
    }
}
