package ooga.player.screens;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
  private static final int SPACING_1 = 40;
  private static final int SPACING_2 = 5;
  private static final int WIDTH = 750;
  private static final int HEIGHT = 600;

  private GridView myGrid;
  private BorderPane myRoot;
  private BooleanProperty isLoss = new SimpleBooleanProperty();
  private BooleanProperty isWin = new SimpleBooleanProperty();
  private BooleanProperty paused = new SimpleBooleanProperty(false);
  private Timer timer;
  private VBox verticalPanel = new VBox();
  private Button pausePlayButton = new Button();

  /**
   * Constructor of this class, calls super to set up instance variables, creates display grid
   * @param gameType current game being played
   * @param player current player
   */
  public GameScreen(String gameType, Player player){
    super(gameType, player);
    String imagePath = player.getMyUserProfile().getImagePreference(gameType);
    myGrid = new GridView(imagePath, GRID_SIZE);
  }

  /**
   * Returns gamescreen scene to set on a stage and display
   * @return the gamescreen to be displayed
   */
  public Scene makeScene() {
    myRoot = new BorderPane();
    myRoot.setPadding(new Insets(PADDING_TOP_BOTTOM, PADDING_LEFT_RIGHT, PADDING_TOP_BOTTOM, PADDING_LEFT_RIGHT));

    HBox toolBar = makeToolBar();
    myRoot.setTop(toolBar);

    Scene scene = new Scene(myRoot, WIDTH, HEIGHT);
    scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + styleSheet + ".css").toExternalForm());
    myScene = scene;
    return myScene;
  }

  /**
   * Sets grid created from engine grid on gamescreen
   * @param backendGrid grid from engine
   */
  public void setGrid(Grid backendGrid){
    VBox gridAndName = new VBox();
    GridPane myGridPane = myGrid.setGrid(backendGrid, paused);
    Label name = new Label(myGameNameResources.getString(myGameType));
    name.setId("game-name-label");
    myGridPane.setAlignment(Pos.CENTER);
    gridAndName.setAlignment(Pos.CENTER);
    gridAndName.setSpacing(SPACING_2);
    gridAndName.getChildren().addAll(name, myGridPane, myErrorMessage);
    myRoot.setCenter(gridAndName);
  }

  /**
   * Gets stats of game from the back (i.e. score, level number, etc.) and adds to screen
   * @param gameStats map of String name of property to IntegerProperty that represents the state of that property
   */
  public void setStats(Map<String, IntegerProperty> gameStats){
    makeButtonPanel();
    makePausePlayButton(gameStats);
    for (String key:gameStats.keySet()){
      IntegerProperty stat = gameStats.get(key);
      verticalPanel.getChildren().add(makeLabel(stat, myStringResources.getString(key)));
    }
    if (myPlayer.getMyUserProfile() != null){
      verticalPanel.getChildren().add(new HBox(new Label(myStringResources.getString("High")), new Label(myPlayer.getMyUserProfile().getHighScore(myGameType))));
    }
    verticalPanel.setAlignment(Pos.CENTER);
    verticalPanel.setSpacing(SPACING_2);
    myRoot.setRight(verticalPanel);
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
    });
    this.isWin.addListener((obs, oldv, newv) -> {
      if(myPlayer.getMyUserProfile() != null){
        myPlayer.getMyUserProfile().addHighScore(myGameType, highScore.getValue());
      }
      myPlayer.setUpWonLevelScreen();
    });
  }

  //makes toolbar for the top of gamescreen
  private HBox makeToolBar() {
    HBox toolBar = new HBox();
    Button customView = makeButton("CustomCommand", e-> myPlayer.setUpCustomView());
    toolBar.getChildren().addAll(makeHomeButton(), customView);
    toolBar.setSpacing(SPACING_1);
    return toolBar;
  }

  //puts all essential buttons into a vbox
  private void makeButtonPanel() {
    Button leaderBoardButton = makeButton("LeaderBoardCommand", e -> myPlayer.setUpLeaderBoardScreen());
    verticalPanel.getChildren().addAll(makeLogoutButton(), makeResetLevelButton(), makeResetGameButton(),
        makeThisSaveButton(), leaderBoardButton);
  }

  //sets event on save button on action
  private Button makeThisSaveButton(){
    return makeButton("SaveCommand", e->{   //TODO: see if you  can get this method out somehow
      if(verticalPanel.getChildren().contains(pausePlayButton) && timer != null) {
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
  }

  //method for making any label in this class
  private HBox makeLabel(IntegerProperty integerProperty, String key) {
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

  //makes pause and play button, sets on action
  private void makePausePlayButton(Map<String, IntegerProperty> gameStats){
    if (!gameStats.containsKey(TIME)) return;
    paused.set(true);
    pausePlayButton = makeButton("PlayCommand", e -> {
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
}