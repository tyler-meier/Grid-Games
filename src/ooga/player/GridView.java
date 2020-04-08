package ooga.player;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ResourceBundle;

public class GridView {
    private static final String RESOURCES = "ooga/player/Resources/Properties";
    private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");

    private static final String IMAGERESOURCES = "ooga/player/Resources/Images";
    private static final String DEFAULT_IMAGERESOURCE_PACKAGE = RESOURCES.replace("/", ".");

    private ResourceBundle myResources;

    public GridView(String gameType) {
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + gameType);
    }

    /**
     * return grid for the gameplay
     * @param rownum
     * @param colnum
     * @return
     */
    public GridPane makeGrid(int rownum, int colnum) {
        GridPane grid = new GridPane();
        grid.setGridLinesVisible(true);

        for (int row = 0; row < rownum; row++) {
            for (int col = 0; col < colnum; col++) {

            }
        }

        return grid;
    }

    //TODO: get either integer or cell to retrieve information about the cell type, return image view
    private ImageView getImage(int state) throws FileNotFoundException {
        String stringInt = Integer.toString(state);
        String imageName = myResources.getString(stringInt);
        String imagePath = DEFAULT_IMAGERESOURCE_PACKAGE + imageName + ".png";
        FileInputStream input = new FileInputStream(imagePath);
        Image image = new Image(input);
        ImageView imageview = new ImageView(image);
        return imageview;
    }

}
