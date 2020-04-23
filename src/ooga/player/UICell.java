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

    public void setPauseProperty(BooleanProperty paused){
        this.paused.bind(paused);
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
