package ooga.player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import ooga.engine.Grid;
import ooga.player.exceptions.ImageNotFoundException;

/**
 * Class that creates the front end grid/the UI grid and what is shown. It takes in the backend grid
 * and changes it to a grid pane that is displayed
 * @author Alyssa Shin, Tyler Meier, Natalie Novitsky
 */
public class GridView {
    private static final String RESOURCES = "ooga/player/Resources/";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
    private static final String IMAGE_RESOURCES = "src/ooga/player/Resources/images/";
    private static final String HIDDEN_IMAGE_PATH = "question";
    private EventHandler<ActionEvent> myCellMatchSoundHandler;
    private int myGridSize;
    private String myImagePath;

    /**
     * Constructor for this class, sets the path to retrieve images and grid size instance variables
     * @param imagePath  the path to retrieve the specific images
     * @param gridSize  the size of this grid
     */
    public GridView(String imagePath, int gridSize, EventHandler<ActionEvent> cellMatchSoundHandler) {
        myGridSize = gridSize;
        myImagePath = imagePath;
        myCellMatchSoundHandler = cellMatchSoundHandler;
    }

    /**
     * Sets up the front end grid by using UI cells and transforming the backend grid into
     * a grid pane with  the images being displayed
     * @param backendGrid the backend grid with initial states and such to be able to create UI grid with images
     * @param paused boolean telling whether the game is paused or not to display the correct images  and know
     *               whether to freeze them or not
     * @return the final grid pane that is created for the front end
     */
    public GridPane setGrid(Grid backendGrid, BooleanProperty paused) throws ImageNotFoundException {
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
                currCell.setSoundPlayer(myCellMatchSoundHandler);
                ImageView myImageView = currCell.getImageView();
                currCell.setPauseProperty(paused);
                GridPane.setRowIndex(myImageView, row * myCellWidth);
                GridPane.setColumnIndex(myImageView, col * myCellHeight);
                myGrid.getChildren().add(myImageView);
            }
        }
        return myGrid;
    }

    private Map<Integer, Image> setupImageMap(String path) throws ImageNotFoundException {
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
            throw new ImageNotFoundException();
        }
        return imageMap;
    }

    private Image getHiddenImage() throws ImageNotFoundException {
        try{
            String imagePath = IMAGE_RESOURCES + HIDDEN_IMAGE_PATH + ".png";
            FileInputStream input = new FileInputStream(imagePath);
            return new Image(input);
        } catch (Exception e){
            throw new ImageNotFoundException();
        }
    }
}

