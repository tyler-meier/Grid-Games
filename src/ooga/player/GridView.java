package ooga.player;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ooga.engine.grid.Grid;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

public class GridView {
    private static final String RESOURCES = "ooga/player/Resources/";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
    private int myGridSize;
    private String myGameType;
    private ResourceBundle myResources;

    public GridView(String gameType, int gridSize) {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + gameType);
        myGridSize = gridSize;
        myGameType = gameType;
    }

//    //code for the method to set up the front end grid with binding--move this to where it makes the most sense
//    //when you're ready to use it, rename things and ask if anything is unclear
    public GridPane setGrid(Grid backendGrid){ // ideally we'll be able to make this run one cell at a time
//        //so we don't need it to be a 2D array, or we just make a Grid object in back end
        GridPane myGrid = new GridPane();
        myGrid.setGridLinesVisible(true);

        int myCellWidth = myGridSize/backendGrid.getRows();
        int myCellHeight = myGridSize/backendGrid.getCols();

        for (int row = 0; row < backendGrid.getRows(); row++) {
            for (int col = 0; col < backendGrid.getCols(); col++) {
                Rectangle rec = new Rectangle(myCellWidth, myCellHeight, Color.WHITE);
                rec.setStroke(Color.BLACK);
                rec.setStrokeWidth(1);
                UICell currCell = new UICell(backendGrid.getCell(row, col), myGameType, myCellHeight, myCellWidth);
                ImageView myImageView = currCell.getImageView();
                GridPane.setRowIndex(myImageView, row * myCellWidth);
                GridPane.setColumnIndex(myImageView, col * myCellHeight);
                myGrid.getChildren().add(myImageView);
            }
        }
        return myGrid;
    }
}
