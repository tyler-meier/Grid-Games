package ooga.player;

import javafx.beans.property.BooleanProperty;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ooga.engine.grid.Grid;

/**
 * Class that creates the front end grid/the UI grid and what is shown. It takes in the backend grid
 * and changes it to a grid pane that is displayed
 * @author Alyssa Shin, Tyler Meier, Natalie Novitsky
 */
public class GridView {
    private int myGridSize;
    private String myImagePath;

    /**
     * Constructor for this class, sets the path to retrieve images and grid size instance variables
     * @param imagePath  the path to retrieve the specific images
     * @param gridSize  the size of this grid
     */
    public GridView(String imagePath, int gridSize) {
        myGridSize = gridSize;
        myImagePath = imagePath;
    }

    /**
     * Sets up the front end grid by using UI cells and transforming the backend grid into
     * a grid pane with  the images being displayed
     * @param backendGrid the backend grid with initial states and such to be able to create UI grid with images
     * @param paused boolean telling whether the game is paused or not to display the correct images  and know
     *               whether to freeze them or not
     * @return the final grid pane that is created for the front end
     */
    public GridPane setGrid(Grid backendGrid, BooleanProperty paused){
        GridPane myGrid = new GridPane();
        myGrid.setGridLinesVisible(true);
        int myCellWidth = myGridSize/backendGrid.getRows();
        int myCellHeight = myGridSize/backendGrid.getCols();
        for (int row = 0; row < backendGrid.getRows(); row++) {
            for (int col = 0; col < backendGrid.getCols(); col++) {
                Rectangle rec = new Rectangle(myCellWidth, myCellHeight, Color.WHITE);
                rec.setStroke(Color.BLACK);
                rec.setStrokeWidth(1);
                UICell currCell = new UICell(backendGrid.getCell(row, col), myImagePath, myCellHeight, myCellWidth);
                ImageView myImageView = currCell.getImageView();
                currCell.setPauseProperty(paused);
                GridPane.setRowIndex(myImageView, row * myCellWidth);
                GridPane.setColumnIndex(myImageView, col * myCellHeight);
                myGrid.getChildren().add(myImageView);
            }
        }
        return myGrid;
    }
}
