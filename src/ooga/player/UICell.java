package ooga.player;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ooga.engine.Cell;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;


public class UICell {
    BooleanProperty open = new SimpleBooleanProperty();
    IntegerProperty state = new SimpleIntegerProperty();
    ImageView myImageView = new ImageView();

    private static final String RESOURCES = "ooga/player/Resources/";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
    private static final String IMAGERESOURCES = "src/ooga/player/Resources/Images/";
    private static final String DEFAULT_IMAGERESOURCE_PACKAGE = IMAGERESOURCES.replace("/", ".");
    private ResourceBundle myResources;

    public UICell(Cell cell, String gameType, int cellHeight, int cellWidth){
        open.bind(cell.isOpen());
        state.bind(cell.cellState());
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + gameType);
        setupImageView(cellHeight, cellWidth);
        setListeners();
        myImageView.setOnMouseClicked(e -> cell.toggleSelected());
    }

    private void setupImageView(int cellHeight, int cellWidth){
        Image initialImage = getImage();
        myImageView = new ImageView(initialImage);
        myImageView.setPreserveRatio(true);
        myImageView.setFitHeight(cellHeight);
        myImageView.setFitWidth(cellWidth);
    }

    private void setListeners(){
        open.addListener((obs, oldv, newv) -> changeImage());
        state.addListener((obs, oldv, newv) -> changeImage());
    }


    private void changeImage(){
         //errors bc of imageMap and hiddenImage here, define these/rename and then the method is done
        myImageView.setImage(getImage());

//         if (open.get()) myImageView.setImage(getImage(state.get())); // however you want to get the image associated with this state
//         else myImageView.setImage(hiddenImage); // however you want to store the image displayed for "hidden" cells
    }

    //TODO: get either integer or cell to retrieve information about the cell type, return image view
    private Image getImage(){
        try{
            String stringInt = Integer.toString(state.get());
            String imageName = myResources.getString(stringInt);
            String imagePath = IMAGERESOURCES + imageName + ".png";
            FileInputStream input = new FileInputStream(imagePath);
            return new Image(input);
        }
        catch (FileNotFoundException e){
            //TODO: deal with exception later
        }
        return null;
    }

    public ImageView getImageView() {
        return myImageView;
    }
//
//     some info for if we want cells to be highlighted when selected
//     https://stackoverflow.com/questions/28253169/javafx-how-to-make-the-border-of-imageview-when-i-click-the-imageview
}
