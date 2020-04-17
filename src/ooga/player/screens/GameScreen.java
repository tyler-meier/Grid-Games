package ooga.player.screens;

import java.util.*;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ooga.engine.grid.Grid;
import ooga.player.Player;
import ooga.player.TimeKeeper;
import ooga.player.GridView;

public class GameScreen extends SuperScreen{
  private static final String RESOURCES = "ooga/player/Resources/";
  private static final String DEFAULT_RESOURCE_PACKAGE = RESOURCES.replace("/", ".");
  private static final String DEFAULT_RESOURCE_FOLDER = "/" + RESOURCES;
  private static final String TIME = "Time";
  private static final int ONE_SECOND = 1000;
  private static final int PADDING_TOP_BOTTOM = 10;
  private static final int PADDING_LEFT_RIGHT = 20;
  private static final int GRID_SIZE = 400;
  private static final int SPACING_1 = 30;
  private static final int SPACING_2 = 45;
  private int myHeight;
  private int myWidth;
  private GridView myGrid;
  private GridPane myGridPane;
  private BorderPane myRoot;
  private BooleanProperty isLoss = new SimpleBooleanProperty();
  private BooleanProperty isWin = new SimpleBooleanProperty();
  private BooleanProperty paused = new SimpleBooleanProperty(false);
  //String myTime = "00:00:000";
  private Timer timer;
  private VBox verticalPanel;
  private Button pauseButton;
  private IntegerProperty score = new SimpleIntegerProperty();
  private Button resetGameButton;
  //TODO: make pause work with selecting

  public GameScreen(EventHandler<ActionEvent> engine, String gameType, Player player){
    super(engine, gameType, player);
    myGrid = new GridView(gameType, GRID_SIZE); //TODO: magic number
  }

  /**
   * returns scene for game
   * @param height
   * @param width
   * @return
   */
  public Scene makeScene(int height, int width) {
    myRoot = new BorderPane();
    myHeight = height;
    myWidth = width;
    myRoot.setPadding(new Insets(PADDING_TOP_BOTTOM, PADDING_LEFT_RIGHT, PADDING_TOP_BOTTOM, PADDING_LEFT_RIGHT));

    Node toolBar = makeToolBar();
    myRoot.setTop(toolBar);

    verticalPanel = new VBox();
    Node buttonPanel = makeButtonPanel();
    //Node statsPanel = makeStatsPanel();
    verticalPanel.setSpacing(SPACING_1);
    verticalPanel.setAlignment(Pos.CENTER);
    verticalPanel.getChildren().addAll(buttonPanel);//, statsPanel);
    myRoot.setRight(verticalPanel);

    Scene scene = new Scene(myRoot, height, width);
    scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + styleSheet).toExternalForm());

    myScene = scene;
    return myScene;
  }

  public void setGrid(Grid backendGrid){
    myGridPane = myGrid.setGrid(backendGrid, paused);
    myGridPane.setAlignment(Pos.CENTER);
    myRoot.setCenter(myGridPane);
  }

  private Node makeButtonPanel() {
    Button logoutButton = makeButton("LogoutCommand", e-> myPlayer.setUpLoginScreen());
    //TODO: fix this
    Button resetGameButton = makeButton("ResetGameCommand", myEventEngine);
//    Button resetLevelButton = makeButton("ResetLevelCommand", e-> myPlayer.setUpGameScreen(myPlayer.getGrid()));
    Button saveButton = makeButton("SaveCommand", e->{
      if(verticalPanel.getChildren().contains(pauseButton))
      {
        timer.cancel();
        pauseButton.setText(myStringResources.getString("Play"));
        paused.set(true);
      }
      if(myPlayer.getMyUserProfile()!=null){
        myPlayer.getMyUserProfile().addHighScore(myGameType, score.getValue());
        myPlayer.getSaveButtonEvent().handle(e);
      }
    });

    Node buttons = styleContents(logoutButton, resetGameButton, saveButton, myErrorMessage);
    return buttons;
  }

  private Node makeToolBar() {
    HBox toolBar = new HBox();
    Button homeButton = makeButton("HomeCommand", e-> myPlayer.setUpStartScreen(myErrorMessage.textProperty()));

//    TimeKeeper timer = new TimeKeeper();
//    timer.addTimeline();
//    String time = timer.getText();
//    Label stopWatch = new Label("TIME: " + time);

    Label name = new Label(myGameType);
    Button customView = makeButton("CustomCommand", e-> myPlayer.setUpCustomView());

    toolBar.getChildren().addAll(homeButton, customView, name);
    toolBar.setSpacing(SPACING_2);
    return toolBar;
  }


  private Node makeLabel(IntegerProperty integerProperty, String key) {
    HBox box = new HBox();
    Label name = new Label(key+": ");
    Label value = new Label();
    value.textProperty().bind(integerProperty.asString());
    if (key.equals("Score")){
      score.bind(integerProperty);
    }
    box.getChildren().addAll(name, value);
    return box;
  }

  public void setStats(Map<String, IntegerProperty> gameStats){
    VBox stats = new VBox();
    for (String key:gameStats.keySet()){
      IntegerProperty stat = gameStats.get(key);
      stats.getChildren().add(makeLabel(stat, myStringResources.getString(key)));
    }
    if (myPlayer.getMyUserProfile() != null){
      stats.getChildren().add(new HBox(new Label("High Score: "), new Label(myPlayer.getMyUserProfile().getHighScore(myGameType))));
    }
    stats.setSpacing(10);
    stats.setAlignment(Pos.CENTER);
    verticalPanel.getChildren().add(stats);
    addTimeButton(gameStats);
  }

  private void addTimeButton(Map<String, IntegerProperty> gameStats){
    if (!gameStats.containsKey(TIME)) return;
    pauseButton = new Button(myStringResources.getString("Play"));
    paused.set(true);
    //TODO: hard coded text/ where should this button be?
    pauseButton.setOnMouseClicked(e -> {
      if (paused.get()) {
        pauseButton.setText(myStringResources.getString("Pause"));
        startTimer(gameStats.get(TIME));
        paused.set(false);
      } else {
        pauseButton.setText(myStringResources.getString("Play"));
        timer.cancel();
        paused.set(true);
      }
    });
    verticalPanel.getChildren().add(pauseButton);
  }

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

  private void decrementTime(IntegerProperty timeProperty){
    Platform.runLater(() -> {
      int time = timeProperty.get();
      time--;
      timeProperty.set(time);
    });
  }

  public void setGameStatus(BooleanProperty isLoss, BooleanProperty isWin){
    this.isLoss.bind(isLoss);
    this.isWin.bind(isWin);
    this.isLoss.addListener((obs, oldv, newv) -> {
      if(myPlayer.getMyUserProfile() != null){
        myPlayer.getMyUserProfile().addHighScore(myGameType, score.getValue());
      }
      myPlayer.setUpLossScreen();
    });
    this.isWin.addListener((obs, oldv, newv) -> {
      if(myPlayer.getMyUserProfile() != null){
        myPlayer.getMyUserProfile().addHighScore(myGameType, score.getValue());
      }
      myPlayer.setUpWonLevelScreen();
    }); //TODO: fix for when level is won or game
  }

}