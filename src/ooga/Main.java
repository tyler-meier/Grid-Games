package ooga;


import javafx.application.Application;
import javafx.stage.Stage;
import ooga.player.Player;

/**
 * Feel free to completely change this code or delete it entirely. 
 */
public class Main extends Application {

    /**
     * Start of the program.
     */
    public static void main (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Player player = new Player();
        player.startView(primaryStage);
    }
}
