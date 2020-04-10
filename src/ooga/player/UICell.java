package ooga.player;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import ooga.engine.Cell;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class UICell {
    BooleanProperty open = new SimpleBooleanProperty();
    IntegerProperty state = new SimpleIntegerProperty();
    ImageView myImageView = new ImageView();

    private static final String RESOURCES = "ooga/player/Resources/";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
    private static final String IMAGERESOURCES = "src/ooga/player/Resources/Images/";
    private static final String DEFAULT_IMAGERESOURCE_PACKAGE = IMAGERESOURCES.replace("/", ".");
    private static final String HIDDEN_IMAGE_PATH = "x";
    private ResourceBundle myResources;
    private Map<Integer, Image> imageMap = new HashMap<>();
    private Image hiddenImage;

    public UICell(Cell cell, String gameType, int cellHeight, int cellWidth){
        open.bind(cell.isOpen());

        state.bind(cell.cellState());
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + gameType);
        setupImageMap();
        setupImageView(cellHeight, cellWidth);
        //setListeners();
        cell.cellState().addListener((obs, oldv, newv) -> changeImage());
        myImageView.setOnMouseClicked(e -> cell.toggleSelected());
        cell.isOpen().addListener((obs, oldv, newv) -> {
            System.out.println("OpPEN STATUS IS CHANGING");
            changeImage();
        });
    }

    private void setupImageMap(){
        List<String> keys = Collections.list(myResources.getKeys());
        try {
            for (String key : keys) {
                String imageName = myResources.getString(key);
                String imagePath = IMAGERESOURCES + imageName + ".png";
                FileInputStream input = new FileInputStream(imagePath);
                Image image = new Image(input);
                imageMap.put(Integer.parseInt(key), image);
            }
            String imagePath = IMAGERESOURCES + HIDDEN_IMAGE_PATH + ".png";
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

    private void setListeners(){
        //state.addListener((obs, oldv, newv) -> changeImage());
    }


    private void changeImage(){
         if (open.get()) {
             //System.out.println("CHANGING THE IMAGE iN UI CELL");
             myImageView.setImage(getImage()); // however you want to get the image associated with this state
         }
         else {
             //System.out.println("cell not open");
             myImageView.setImage(hiddenImage); // however you want to store the image displayed for "hidden" cells
         }
    }

    //TODO: get either integer or cell to retrieve information about the cell type, return image view
    private Image getImage(){
        return imageMap.get(state.getValue());
    }

    public ImageView getImageView() {
        return myImageView;
    }
//
//     some info for if we want cells to be highlighted when selected
//     https://stackoverflow.com/questions/28253169/javafx-how-to-make-the-border-of-imageview-when-i-click-the-imageview
}
