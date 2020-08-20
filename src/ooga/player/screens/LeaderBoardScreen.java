package ooga.player.screens;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
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
  private static final String LABEL = "LeaderBoard";
  private static final String SEPARATOR = ":   ";
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
  public void setUpScene(String modeType){
    Stage popUpWindow = new Stage();
    popUpWindow.initModality(Modality.APPLICATION_MODAL);
    popUpWindow.setTitle(TITLE);
    popUpWindow.setScene(styleScene(makeTitle(), makePanel()));
    setStyle(modeType);
    popUpWindow.showAndWait();
  }

  private Label makeTitle(){
    Label title = new Label(myStringResources.getString(LABEL) + myGameNameResources.getString(myPlayer.getGameType()));
    title.setId("title-label");
    return title;
  }

  private VBox makePanel(){
    VBox highScores = new VBox();
    for (String user : myPlayer.getHighScoreMap().keySet()){
      Integer score = myPlayer.getHighScoreMap().get(user);
      highScores.getChildren().add(new Label(user+ SEPARATOR + score.toString()));
    }
    highScores.setSpacing(SPACING);
    highScores.setAlignment(Pos.CENTER);
    return highScores;
  }
}
