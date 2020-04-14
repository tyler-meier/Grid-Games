package ooga.player.screens;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ooga.player.Player;

public class LossScreen extends SuperScreen {


  public LossScreen(Player thisPlayer){
    super(thisPlayer);
  }

//  public Scene setUpScene(String username){
//    Node contents = setUpContents(username);
//    myNodes.clear();
//    myNodes.add(contents);
//    Scene scene = styleScene(myNodes);
//    return scene;
//  }

//  public Node setUpContents(String username){
//    VBox myVBox = new VBox();
//
//    Label loginLabel = new Label(myStringResources.getString("Login"));
//
//
//    topVBox.getChildren().addAll(loginLabel, username, password);
//    topVBox.setSpacing(10);
//    topVBox.setAlignment(Pos.CENTER);
//    return topVBox;
//  }

}
