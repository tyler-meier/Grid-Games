package ooga.player;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.ImageView;
import ooga.engine.Cell;


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

    private void setListeners(){
        open.addListener((obs, oldv, newv) -> changeImage());
        state.addListener((obs, oldv, newv) -> changeImage());
        // toggle selected by clicking on a cell
        myImage.setOnMouseClicked(e -> {
            if (!moveInProgress.get()) selected.set(!selected.get());
        });
    }

    private void changeImage(){
        // errors bc of imageMap and hiddenImage here, define these/rename and then the method is done

        // if (open.get()) myImage.setImage(imageMap.get(state.get())); // however you want to get the image associated with this state
        // else myImage.setImage(hiddenImage); // however you want to store the image displayed for "hidden" cells
    }

    // some info for if we want cells to be highlighted when selected
    // https://stackoverflow.com/questions/28253169/javafx-how-to-make-the-border-of-imageview-when-i-click-the-imageview
}
