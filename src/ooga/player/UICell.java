package ooga.player;

import java.util.Map;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import ooga.engine.Cell;
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
    private static final int COLOR_RADIUS = 30;

    private BooleanProperty paused = new SimpleBooleanProperty();
    private Map<Integer, Image> imageMap;
    private Image hiddenImage;

    public UICell(Cell cell, int cellHeight, int cellWidth, Map<Integer, Image> imageMap, Image hiddenImage){
        open.bind(cell.isOpen());
        state.bind(cell.cellState());
        this.imageMap = imageMap;
        this.hiddenImage = hiddenImage;
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

    private Image getImage(){
        return imageMap.get(state.getValue());
    }
}
