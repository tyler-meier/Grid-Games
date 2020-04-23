package ooga.player;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import ooga.engine.Cell;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Sets up the cell and all of the properties it can have  visually such as the image being displayed, and whether
 * or not it needs to be  hidden. Also visually allows it to have a background when clicked and allows for switches of the
 * images when they are clicked or changed from being hidden to open.
 * @author Alyssa Shin, Tyler Meier, Natalie Novitsky
 */
public class UICell {
    BooleanProperty open = new SimpleBooleanProperty();
    IntegerProperty state = new SimpleIntegerProperty();
    ImageView myImageView = new ImageView();

    private static final String RESOURCES = "ooga/player/Resources/";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
    private static final String IMAGE_RESOURCES = "src/ooga/player/Resources/images/";
    private static final String HIDDEN_IMAGE_PATH = "question";
    private static final int COLOR_RADIUS = 30;

    private BooleanProperty paused = new SimpleBooleanProperty();
    private ResourceBundle myResources;
    private Map<Integer, Image> imageMap = new HashMap<>();
    private Image hiddenImage;

    /**
     * The constructor for creating the ui cell, deals with all of its attributes and sets them up
     * including when the cell should be paused and when it should be hidden or not, and sets
     * up all the images for each cell
     * @param cell the cell of the backend grid at specific row and column
     * @param imagePath the image path used to set the image for the current cell
     * @param cellHeight the height the cell will be based off of size
     * @param cellWidth the width the cell will be based off of size
     */
    public UICell(Cell cell, String imagePath, int cellHeight, int cellWidth){
        open.bind(cell.isOpen());
        state.bind(cell.cellState());
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + imagePath);
        setupImageMap();
        setupImageView(cellHeight, cellWidth);
        cell.cellState().addListener((obs, oldv, newv) -> {
            changeImage();
        });
        cell.isOpen().addListener((obs, oldv, newv) -> changeImage());
        myImageView.setOnMouseClicked(e -> {
            if (!paused.get())
                cell.toggleSelected();
        });
        cell.isSelected().addListener((a, oldvalue, newvalue) -> {
            if (newvalue) myImageView.setEffect(new DropShadow(COLOR_RADIUS, Color.YELLOW));
            else myImageView.setEffect(null);
        });
    }

    /**
     * Binds the boolean based off of if the cell is supposed to be paused or not, given by info in the backend
     * @param paused the boolean of whether it is paused or not
     */
    public void setPauseProperty(BooleanProperty paused){
        this.paused.bind(paused);
    }

    /**
     * @return the image view of the cell/current image
     */
    public ImageView getImageView() {
        return myImageView;
    }

    private void setupImageMap(){
        List<String> keys = Collections.list(myResources.getKeys());
        try {
            for (String key : keys) {
                String imageName = myResources.getString(key);
                String imagePath = IMAGE_RESOURCES + imageName + ".png";
                FileInputStream input = new FileInputStream(imagePath);
                Image image = new Image(input);
                imageMap.put(Integer.parseInt(key), image);
            }
            String imagePath = IMAGE_RESOURCES + HIDDEN_IMAGE_PATH + ".png";
            FileInputStream input = new FileInputStream(imagePath);
            hiddenImage = new Image(input);
        } catch (FileNotFoundException e){
            //TODO: deal with exception later
        }
    }

    private void setupImageView(int cellHeight, int cellWidth){
        myImageView = new ImageView(hiddenImage);
        myImageView.setPreserveRatio(true);
        changeImage();
        myImageView.setFitHeight(cellHeight);
        myImageView.setFitWidth(cellWidth);
    }

    private void changeImage(){
         if (open.get()) myImageView.setImage(getImage());
         else myImageView.setImage(hiddenImage);
    }

    //TODO: get either integer or cell to retrieve information about the cell type, return image view
    private Image getImage(){
        return imageMap.get(state.getValue());
    }
}
