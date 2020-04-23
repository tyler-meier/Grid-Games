package ooga.player;

import javafx.beans.property.BooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ooga.engine.gridCreator.Grid;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class GridView {
    private static final String RESOURCES = "ooga/player/Resources/";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
    private static final String IMAGE_RESOURCES = "src/ooga/player/Resources/images/";
    private static final String HIDDEN_IMAGE_PATH = "question";
    private int myGridSize;
    private String myImagePath;

    public GridView(String imagePath, int gridSize) {
        myGridSize = gridSize;
        myImagePath = imagePath;
    }

//    //code for the method to set up the front end grid with binding--move this to where it makes the most sense
//    //when you're ready to use it, rename things and ask if anything is unclear
    public GridPane setGrid(Grid backendGrid, BooleanProperty paused){ // ideally we'll be able to make this run one cell at a time
//        //so we don't need it to be a 2D array, or we just make a Grid object in back end
        GridPane myGrid = new GridPane();
        myGrid.setGridLinesVisible(true);
        Map<Integer, Image> imageMap = setupImageMap(myImagePath);
        Image hiddenImage = getHiddenImage();

        int myCellWidth = myGridSize/backendGrid.getRows();
        int myCellHeight = myGridSize/backendGrid.getCols();

        for (int row = 0; row < backendGrid.getRows(); row++) {
            for (int col = 0; col < backendGrid.getCols(); col++) {
                Rectangle rec = new Rectangle(myCellWidth, myCellHeight, Color.WHITE);
                rec.setStroke(Color.BLACK);
                rec.setStrokeWidth(1);
                UICell currCell = new UICell(backendGrid.getCell(row, col), myCellHeight, myCellWidth, imageMap, hiddenImage);
                ImageView myImageView = currCell.getImageView();
                currCell.setPauseProperty(paused);
                GridPane.setRowIndex(myImageView, row * myCellWidth);
                GridPane.setColumnIndex(myImageView, col * myCellHeight);
                myGrid.getChildren().add(myImageView);
            }
        }
        return myGrid;
    }

    private Map<Integer, Image> setupImageMap(String path){
        Map<Integer, Image> imageMap = new HashMap<>();
        ResourceBundle myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + path);
        List<String> keys = Collections.list(myResources.getKeys());
        try {
            for (String key : keys) {
                String imageName = myResources.getString(key);
                String imagePath = IMAGE_RESOURCES + imageName + ".png";
                FileInputStream input = new FileInputStream(imagePath);
                Image image = new Image(input);
                imageMap.put(Integer.parseInt(key), image);
            }
        } catch (FileNotFoundException e){
            //TODO: deal with exception later
        }
        return imageMap;
    }

    private Image getHiddenImage(){
        try{
            String imagePath = IMAGE_RESOURCES + HIDDEN_IMAGE_PATH + ".png";
            FileInputStream input = new FileInputStream(imagePath);
            return new Image(input);
        } catch (Exception e){
            //TODO errors
            return null;
        }

    }
}
