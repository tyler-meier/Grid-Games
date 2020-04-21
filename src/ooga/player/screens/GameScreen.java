package ooga.player.screens;

import java.io.File;
import java.util.*;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import ooga.engine.grid.Grid;
import ooga.player.GridView;
import ooga.player.Player;

public class GameScreen extends SuperScreen {
  private static final String TIME = "Time";
  private static final String SCORE = "Score";
  private static final int ONE_SECOND = 1000;
  private static final int PADDING_TOP_BOTTOM = 10;
  private static final int PADDING_LEFT_RIGHT = 20;
  private static final int GRID_SIZE = 400;
  private static final int SPACING_1 = 30;
  private static final int SPACING_2 = 45;
  private static final int SPACING_3 = 10;
  private static final int WIDTH = 900;
  private static final int HEIGHT = 600;
  private static final String SOUND_RESOURCES = "src/ooga/player/Resources/sounds/";

  private GridView myGrid;
  private BorderPane myRoot;
  private BooleanProperty isLoss = new SimpleBooleanProperty();
  private BooleanProperty isWin = new SimpleBooleanProperty();
  private BooleanProperty paused = new SimpleBooleanProperty(false);
  //private String myTime = "00:00:000";
  private Timer timer;
  private VBox verticalPanel = new VBox();
  private Button pausePlayButton, resetGameButton;

  public GameScreen(String gameType, Player player){
    super(gameType, player);
    myGrid = new GridView(gameType, GRID_SIZE);
  }

  /**
   * Returns gamescreen scene to set on a stage and display
   * @return the gamescreen to be displayed
   */
  public Scene makeScene() {
    myRoot = new BorderPane();
    myRoot.setPadding(new Insets(PADDING_TOP_BOTTOM, PADDING_LEFT_RIGHT, PADDING_TOP_BOTTOM, PADDING_LEFT_RIGHT));

    HBox toolBar = makeToolBar();
    makeSideBar();
    myRoot.setTop(toolBar);
    myRoot.setRight(verticalPanel);

    Scene scene = new Scene(myRoot, WIDTH, HEIGHT);
    scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + styleSheet).toExternalForm());
    myScene = scene;
    return myScene;
  }

  /**
   * Sets grid created from engine grid on gamescreen
   * @param backendGrid grid from engine
   */
  public void setGrid(Grid backendGrid){
    GridPane myGridPane = myGrid.setGrid(backendGrid, paused);
    myGridPane.setAlignment(Pos.CENTER);
    myRoot.setCenter(myGridPane);
  }

  /**
   * Gets stats of game from the back (i.e. score, level number, etc.) and adds to screen
   * @param gameStats map of String name of property to IntegerProperty that represents the state of that property
   */
  public void setStats(Map<String, IntegerProperty> gameStats){
    VBox stats = new VBox();
    for (String key:gameStats.keySet()){
      IntegerProperty stat = gameStats.get(key);
      stats.getChildren().add(makeLabel(stat, myStringResources.getString(key)));
    }
    if (myPlayer.getMyUserProfile() != null){
      stats.getChildren().add(new HBox(new Label(myStringResources.getString("High")), new Label(myPlayer.getMyUserProfile().getHighScore(myGameType))));
    }
    stats.setSpacing(SPACING_3);
    stats.setAlignment(Pos.CENTER);
    verticalPanel.getChildren().add(stats);
    makePausePlayButton(gameStats);
  }

  /**
   * Binds win and loss status, triggers loss or won screen according to outcome of game
   * @param isLoss boolean property that indicates loss or not
   * @param isWin boolean property that indicates win or not
   */
  public void setGameStatus(BooleanProperty isLoss, BooleanProperty isWin){
    this.isLoss.bind(isLoss);
    this.isWin.bind(isWin);
    this.isLoss.addListener((obs, oldv, newv) -> {
      if(myPlayer.getMyUserProfile() != null){
        myPlayer.getMyUserProfile().addHighScore(myGameType, highScore.getValue());
      }
      myPlayer.setUpLossScreen();
      playSound("loss");
    });
    this.isWin.addListener((obs, oldv, newv) -> {
      if(myPlayer.getMyUserProfile() != null){
        myPlayer.getMyUserProfile().addHighScore(myGameType, highScore.getValue());
      }
      //TODO: check if this works
      myPlayer.setUpWonLevelScreen();
      playSound("levelup");
    }); //TODO: fix for when level is won or game
  }

  //method for making any label in this class
  private Node makeLabel(IntegerProperty integerProperty, String key) {
    HBox box = new HBox();
    Label name = new Label(key+": ");
    Label value = new Label();
    value.textProperty().bind(integerProperty.asString());
    if (key.equals(SCORE)){
      highScore.bind(integerProperty);
    }
    box.getChildren().addAll(name, value);
    return box;
  }

  //sets event on save button on action
  private Button makeThisSaveButton(){
    Button saveButton = makeButton("SaveCommand", e->{   //TODO: see if you  can get this method out somehow
      if(verticalPanel.getChildren().contains(pausePlayButton)) {
        timer.cancel();
        pausePlayButton.setText(myButtonResources.getString("PlayCommand"));
        paused.set(true);
      }
      if(myPlayer.getMyUserProfile() != null) {
        myPlayer.getMyUserProfile().addHighScore(myGameType, highScore.getValue());
        myPlayer.getSaveButtonEvent().handle(e);
      }
      else {
        myErrorMessage.textProperty().setValue(myStringResources.getString("GuestSave"));
        myErrorMessage.setWrapText(true);
      }
    });
    return saveButton;
  }

  //makes pause and play button, sets on action
  private void makePausePlayButton(Map<String, IntegerProperty> gameStats){
    if (!gameStats.containsKey(TIME)) return;
    pausePlayButton = new Button(myButtonResources.getString("PlayCommand"));
    paused.set(true);
    pausePlayButton.setOnMouseClicked(e -> {
      if (paused.get()) {
        pausePlayButton.setText(myButtonResources.getString("PauseCommand"));
        startTimer(gameStats.get(TIME));
        paused.set(false);
      } else {
        pausePlayButton.setText(myButtonResources.getString("PlayCommand"));
        timer.cancel();
        paused.set(true);
      }
    });
    verticalPanel.getChildren().add(pausePlayButton);
  }

  //makes toolbar for the top of gamescreen
  private HBox makeToolBar() {
    HBox toolBar = new HBox();
    Button customView = makeButton("CustomCommand", e-> myPlayer.setUpCustomView());
    Label name = new Label(myGameNameResources.getString(myGameType));
    toolBar.getChildren().addAll(makeHomeButton(), customView, name);
    toolBar.setSpacing(SPACING_2);
    return toolBar;
  }


 //puts all essential buttons into a vbox
  private VBox makeButtonPanel() {
    VBox buttons = styleContents(makeLogoutButton(), makeResetLevelButton(), makeResetGameButton(), makeThisSaveButton(), myErrorMessage);
    return buttons;
  }

  //adds vbox of buttons to side panel and styles
  private void makeSideBar() {
    VBox buttonPanel = makeButtonPanel();
    verticalPanel.setSpacing(SPACING_1);
    verticalPanel.setAlignment(Pos.CENTER);
    verticalPanel.getChildren().add(buttonPanel);
  }

  //creates new timer object, defines how it starts
  private void startTimer(IntegerProperty timeProperty) {
    timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        int time = timeProperty.get();
        if (time<=0 || isWin.get()) {
          timer.cancel();
        } else decrementTime(timeProperty);
      }
    }, ONE_SECOND, ONE_SECOND);
  }

  //makes timer count down
  private void decrementTime(IntegerProperty timeProperty){
    Platform.runLater(() -> {
      int time = timeProperty.get();
      time--;
      timeProperty.set(time);
    });
  }

  //makes new sound
  private void playSound(String soundName) {
    String soundPath = SOUND_RESOURCES + soundName + ".mp3";
    Media sound = new Media(new File(soundPath).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.play();
  }
}