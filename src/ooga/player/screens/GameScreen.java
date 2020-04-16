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
  private int myHeight;
  private int myWidth;
  private GridView myGrid;
  private GridPane myGridPane;
  private BorderPane myRoot;
  private Scene thisScene;
//  IntegerProperty myHighScore = new SimpleIntegerProperty();
//  IntegerProperty myScore = new SimpleIntegerProperty();
//  IntegerProperty myLives = new SimpleIntegerProperty();
//  IntegerProperty myLevel = new SimpleIntegerProperty();
//  IntegerProperty myMovesLeft = new SimpleIntegerProperty();
  private BooleanProperty isLoss = new SimpleBooleanProperty();
  private BooleanProperty isWin = new SimpleBooleanProperty();
  private BooleanProperty paused = new SimpleBooleanProperty(false);
  String myTime = "00:00:000";
  private Timer timer;
  private VBox verticalPanel;
  private Button pauseButton;
  //TODO: make pause work with selecting

  public GameScreen(EventHandler<ActionEvent> engine, String gameType, Player player){
    super(engine, gameType, player);
    myGrid = new GridView(gameType, 400); //TODO: magic number
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
    myRoot.setPadding(new Insets(10, 20, 10, 20));

    Node toolBar = makeToolBar();
    myRoot.setTop(toolBar);

    verticalPanel = new VBox();
    Node buttonPanel = makeButtonPanel();
    //Node statsPanel = makeStatsPanel();
    verticalPanel.setSpacing(30);
    verticalPanel.setAlignment(Pos.CENTER);
    verticalPanel.getChildren().addAll(buttonPanel);//, statsPanel);
    myRoot.setRight(verticalPanel);

    Scene scene = new Scene(myRoot, height, width);
    scene.getStylesheets().add(getClass().getResource(DEFAULT_RESOURCE_FOLDER + styleSheet).toExternalForm());

    thisScene = scene;
    return scene;
  }

  public void setGrid(Grid backendGrid){
    myGridPane = myGrid.setGrid(backendGrid, paused);
    myGridPane.setAlignment(Pos.CENTER);
    myRoot.setCenter(myGridPane);
  }

  private Node makeButtonPanel() {
    Button logoutButton = makeButton("LogoutCommand", e-> myPlayer.setUpLoginScreen());
    //TODO: fix this
    Button resetGameButton = makeButton("ResetGameCommand", e-> {
      try {
        myEventEngine.handle(e);
      } catch (NullPointerException p){ //TODO: change to actual set error thing
        System.out.println("WRONG");
      }
    });
//    Button resetLevelButton = makeButton("ResetLevelCommand", e-> myPlayer.setUpGameScreen(myPlayer.getGrid()));
    Button saveButton = makeButton("SaveCommand", e->{
      if(verticalPanel.getChildren().contains(pauseButton))
      {
        timer.cancel();
        pauseButton.setText("Play");
        paused.set(true);
      }
      myPlayer.getSaveButtonEvent().handle(e);
    });

    Node buttons = styleContents(logoutButton, resetGameButton, saveButton, myErrorMessage);
    return buttons;
  }

  private Node makeToolBar() {
    HBox toolBar = new HBox();
    Button homeButton = makeButton("HomeCommand", e-> myPlayer.setUpStartScreen(myErrorMessage.textProperty()));

    TimeKeeper timer = new TimeKeeper();
    timer.addTimeline();
    String time = timer.getText();
    Label stopWatch = new Label("TIME: " + time);

//    Button customView = makeButton("Customize", e-> myPlayer.setUpCustomView());

    toolBar.getChildren().addAll(homeButton, stopWatch);
    toolBar.setSpacing(45);
    return toolBar;
  }

//  private Node makeStatsPanel() {
//    //TODO: refactor this, use keys from the gamestats to display correct stirng
//    VBox stats = new VBox();
//    //stats.getChildren().addAll(makeLabel(myHighScore), makeLabel(myScore), makeLabel(myLives), makeLabel(myLevel), makeLabel(myMovesLeft), makeLabel(lossStat));
//    stats.setSpacing(10);
//    stats.setAlignment(Pos.CENTER);
//    return stats;
//  }


  private Node makeLabel(IntegerProperty integerProperty, String key) {
    HBox box = new HBox();
    Label name = new Label(key+": ");
    Label value = new Label();
    value.textProperty().bind(integerProperty.asString());
    box.getChildren().addAll(name, value);
    return box;
  }

  public void setStats(Map<String, IntegerProperty> gameStats){
    //TODO: how do you get high score of profile?, use game type that is global variable
//    myScore.bind(gameStats.get("Score"));
//    myHighScore.bind(gameStats.get("Score"));
//    //TODO: get number of lives
//    myLives.bind(gameStats.get("Level"));
//    myLevel.bind(gameStats.get("Level"));
//    myMovesLeft.bind(gameStats.get("MovesUsed"));
//    //TODO: implement timekeeper
//    gameStats.get("Time").bind(myTime);

    VBox stats = new VBox();
    for (String key:gameStats.keySet()){
      IntegerProperty stat = gameStats.get(key);
      stats.getChildren().add(makeLabel(stat, key));
    }
    stats.setSpacing(10);
    stats.setAlignment(Pos.CENTER);
    verticalPanel.getChildren().add(stats);
    addTimeButton(gameStats);
  }

  private void addTimeButton(Map<String, IntegerProperty> gameStats){
    if (!gameStats.containsKey(TIME)) return;
    pauseButton = new Button("Play");
    paused.set(true);
    //TODO: hard coded text/ where should this button be?
    pauseButton.setOnMouseClicked(e -> {
      if (paused.get()) {
        pauseButton.setText("Pause");
        startTimer(gameStats.get(TIME));
        paused.set(false);
      } else {
        pauseButton.setText("Play");
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
    this.isLoss.addListener((obs, oldv, newv) -> myPlayer.setUpLossScreen());
    this.isWin.addListener((obs, oldv, newv) -> myPlayer.setUpWonLevelScreen()); //TODO: fix for when level is won or game
  }

}