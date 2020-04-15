package ooga.player;

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
import java.util.*;


public class UICell {
    BooleanProperty open = new SimpleBooleanProperty();
    IntegerProperty state = new SimpleIntegerProperty();
    ImageView myImageView = new ImageView();

    private static final String RESOURCES = "ooga/player/Resources/";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
    private static final String IMAGERESOURCES = "src/ooga/player/Resources/Images/";
    private static final String DEFAULT_IMAGERESOURCE_PACKAGE = IMAGERESOURCES.replace("/", ".");
    private static final String HIDDEN_IMAGE_PATH = "question";
    private BooleanProperty paused = new SimpleBooleanProperty();
    private ResourceBundle myResources;
    private Map<Integer, Image> imageMap = new HashMap<>();
    private Image hiddenImage;

    public UICell(Cell cell, String gameType, int cellHeight, int cellWidth){
        open.bind(cell.isOpen());
        state.bind(cell.cellState());
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + gameType);
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
            if (newvalue) myImageView.setEffect(new DropShadow(20, Color.YELLOW));
            else myImageView.setEffect(null);
        });
    }

    public void setPauseProperty(BooleanProperty paused){
        this.paused.bind(paused);
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


    private void changeImage(){
         if (open.get()) myImageView.setImage(getImage());
         else myImageView.setImage(hiddenImage); // however you want to store the image displayed for "hidden" cells
    }

    //TODO: get either integer or cell to retrieve information about the cell type, return image view
    private Image getImage(){
        return imageMap.get(state.getValue());
    }

    public ImageView getImageView() {
        return myImageView;
    }
}
