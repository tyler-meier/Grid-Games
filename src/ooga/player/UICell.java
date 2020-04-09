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
    BooleanProperty moveInProgress = new SimpleBooleanProperty();
    BooleanProperty selected = new SimpleBooleanProperty();
    BooleanProperty open = new SimpleBooleanProperty();
    IntegerProperty state = new SimpleIntegerProperty();
    ImageView myImageView = new ImageView();

    private static final String RESOURCES = "ooga/player/Resources/";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
    private static final String IMAGERESOURCES = "src/ooga/player/Resources/Images/";
    private static final String DEFAULT_IMAGERESOURCE_PACKAGE = IMAGERESOURCES.replace("/", ".");
    private ResourceBundle myResources;

    public UICell(Cell cell, String gameType){
        selected.bindBidirectional(cell.isSelected());
        open.bind(cell.isOpen());
        state.bind(cell.cellState());
        //setListeners();
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + gameType);
    }

    public void setMoveInProgress(BooleanProperty inProgress){ moveInProgress.bind(inProgress);}


    private void setListeners(){
        open.addListener((obs, oldv, newv) -> changeImage());
        state.addListener((obs, oldv, newv) -> changeImage());
        // toggle selected by clicking on a cell
        myImageView.setOnMouseClicked(e -> {
            System.out.println("a cell has been clicked");
            if (!moveInProgress.get()) selected.set(!selected.get());
        });
    }


    private void changeImage(){
         //errors bc of imageMap and hiddenImage here, define these/rename and then the method is done

         //if (open.get()) myImage.setImage(getImage(state.get())); // however you want to get the image associated with this state
         //else myImage.setImage(hiddenImage); // however you want to store the image displayed for "hidden" cells
    }

    //TODO: get either integer or cell to retrieve information about the cell type, return image view
    public Image getImage(int state){
        try{
            String stringInt = Integer.toString(state);
            String imageName = myResources.getString(stringInt);
            String imagePath = IMAGERESOURCES + imageName + ".png";
            FileInputStream input = new FileInputStream(imagePath);
            Image myImage = new Image(input);
            myImageView = new ImageView(myImage);
            myImageView.setPreserveRatio(true);
            setListeners();
            return myImage;
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
