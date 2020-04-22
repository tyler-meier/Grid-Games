package ooga.player.screens;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ooga.player.Player;

/**
 * Leader Board screen class which shows the profiles saved and their high scores for the specific game
 * @author Tyler Meier
 */
public class LeaderBoardScreen extends SuperScreen {

  private static final String TITLE = "High Score Leaderboard";
  private static final int SPACING = 10;

  /**
   * Constructor of this class, calls super to set up instance variables
   * @param thisPlayer  the current player
   */
  public LeaderBoardScreen(Player thisPlayer) {
    super(thisPlayer);
  }

  /**
   * Sets up the leader board scene and creates new window
   */
  public void setUpScene(){
    Stage popUpWindow = new Stage();
    popUpWindow.initModality(Modality.APPLICATION_MODAL);
    popUpWindow.setTitle(TITLE);
    popUpWindow.setScene(styleScene(makeTitle(), makePanel()));
    popUpWindow.showAndWait();
  }

  private Label makeTitle(){
    Label title = new Label(myStringResources.getString("LeaderBoard") + myGameNameResources.getString(myPlayer.getGameType()));
    title.setId("title-label");
    return title;
  }

  private VBox makePanel(){
    VBox highScores = new VBox();
    for (String user : myPlayer.getHighScoreMap().keySet()){
      Integer score = myPlayer.getHighScoreMap().get(user);
      highScores.getChildren().add(makeLabel(user, score));
    }
    highScores.setSpacing(SPACING);
    return highScores;
  }

  private Node makeLabel(String user, Integer score){
    HBox box = new HBox();
    Label name = new Label(user+": ");
    Label value = new Label(score.toString());
    box.setSpacing(30);
    box.setAlignment(Pos.CENTER);
    box.getChildren().addAll(name, value);
    return box;
  }
}
