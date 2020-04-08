package ooga.player;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

public class GridView {
    private static final String RESOURCES = "ooga/player/Resources/";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
    private static final String IMAGERESOURCES = "ooga/player/Resources/Images/";
    private static final String DEFAULT_IMAGERESOURCE_PACKAGE = RESOURCES.replace("/", ".");
    private int myGridSize;
    private ResourceBundle myResources;

    public GridView(String gameType, int gridSize) {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + gameType);
        myGridSize = gridSize;
    }

//    //code for the method to set up the front end grid with binding--move this to where it makes the most sense
//    //when you're ready to use it, rename things and ask if anything is unclear
//    public void setGrid(Grid grid) { // ideally we'll be able to make this run one cell at a time
//        //so we don't need it to be a 2D array, or we just make a Grid object in back end
//        myGrid = new UICell[grid.getRows()][grid.getColumns]();
//        for (int r = 0; r < myGrid.length; r++) {
//            for (int c = 0; c < myGrid[0].length; c++) {
//                myGrid[r][c] = new UICell(grid.getCell(r, c);
//                // set property so that front end stops users from making a new move while one is in motion
//                myGrid[r][c].setMoveInProgress(grid.getInProgressProperty());
//            }
//        }
//    }

    /**
     * return grid for the gameplay
     * @param rownum
     * @param colnum
     * @return
     */
    public GridPane makeGrid(int rownum, int colnum) {
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);

        int myCellWidth = myGridSize/colnum;
        int myCellHeight = myGridSize/rownum;

        for (int row = 0; row < rownum; row++) {
            for (int col = 0; col < colnum; col++) {
                Rectangle rec = new Rectangle(myCellWidth, myCellHeight, Color.WHITE);
                rec.setStroke(Color.BLACK);
                rec.setStrokeWidth(1);
                grid.setRowIndex(rec, col * myCellWidth);
                grid.setColumnIndex(rec, row * myCellHeight);
                grid.getChildren().add(rec);
            }
        }
        return grid;
    }

    //TODO: get either integer or cell to retrieve information about the cell type, return image view
    private ImageView getImage(int state) throws FileNotFoundException {
        String stringInt = Integer.toString(state);
        String imageName = myResources.getString(stringInt);
        String imagePath = DEFAULT_IMAGERESOURCE_PACKAGE + imageName + ".png";
        FileInputStream input = new FileInputStream(imagePath);
        Image image = new Image(input);
        ImageView imageview = new ImageView(image);
        return imageview;
    }


}
