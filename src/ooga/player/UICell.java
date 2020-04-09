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


public class UICell {
    BooleanProperty moveInProgress = new SimpleBooleanProperty();
    BooleanProperty selected = new SimpleBooleanProperty();
    BooleanProperty open = new SimpleBooleanProperty();
    IntegerProperty state = new SimpleIntegerProperty();
    ImageView myImage = new ImageView();

    public UICell(Cell cell){
        selected.bindBidirectional(cell.isSelected());
        open.bind(cell.isOpen());
        state.bind(cell.cellState());
        setListeners();
    }

    public void setMoveInProgress(BooleanProperty inProgress){ moveInProgress.bind(inProgress);}

    public ImageView getImage() { return myImage; }

    private void setListeners(){
        open.addListener((obs, oldv, newv) -> changeImage());
        state.addListener((obs, oldv, newv) -> changeImage());
        // toggle selected by clicking on a cell
        myImage.setOnMouseClicked(e -> {
            if (!moveInProgress.get()) selected.set(!selected.get());
        });
    }

    private void changeImage(){
         //errors bc of imageMap and hiddenImage here, define these/rename and then the method is done

         //if (open.get()) myImage.setImage(getImage(state.get())); // however you want to get the image associated with this state
         //else myImage.setImage(hiddenImage); // however you want to store the image displayed for "hidden" cells
    }

//    //TODO: get either integer or cell to retrieve information about the cell type, return image view
//    private Image getImage(int state) throws FileNotFoundException {
////        String stringInt = Integer.toString(state);
////        String imageName = myResources.getString(stringInt);
////        String imagePath = DEFAULT_IMAGERESOURCE_PACKAGE + imageName + ".png";
////        FileInputStream input = new FileInputStream(imagePath);
////        return new Image(input);
//    }

    // some info for if we want cells to be highlighted when selected
    // https://stackoverflow.com/questions/28253169/javafx-how-to-make-the-border-of-imageview-when-i-click-the-imageview
}
