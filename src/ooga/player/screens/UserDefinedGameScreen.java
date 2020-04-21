package ooga.player.screens;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import ooga.player.Player;

import java.util.*;

public abstract class UserDefinedGameScreen extends SuperScreen {
    protected static final String SPACE = " ";
    protected static final String KEYS_RESOURCES_PATH = "resources.";
    protected static final String STRINGS_RESOURCES_PATH = "NewGameStrings";
    protected List<String> integerInputs = new ArrayList<>();
    protected Map<String, Node> userInputFields = new HashMap<>();
    protected ResourceBundle myKeysResources;
    protected ResourceBundle newGameStringsResources;
    protected String myButtonText;
    protected String gameLabel;
    protected EventHandler<ActionEvent> myButtonEvent;

    public UserDefinedGameScreen(Player thisPlayer) {
        super(thisPlayer);
        newGameStringsResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + STRINGS_RESOURCES_PATH);
    }

    protected Node buildInputFields(){
        VBox myVBox = new VBox();
        myVBox.setSpacing(10);
        myVBox.setAlignment(Pos.CENTER);
        for(String key : Collections.list(myKeysResources.getKeys())){
            Label label = new Label(newGameStringsResources.getString(key));
            if (isInteger(myKeysResources.getString(key))) userInputFields.put(key, new TextField());
            else {
                ComboBox<String> dropDown = new ComboBox<>();
                String[] options = myKeysResources.getString(key).split(SPACE);
                for (String s:options) dropDown.getItems().add(s);
                userInputFields.put(key, dropDown);
            }
            myVBox.getChildren().addAll(label, userInputFields.get(key));
        }
        return myVBox;
    }

    public Scene setUpScene(){
        Label newGameLabel = new Label(myStringResources.getString(gameLabel));
        Node gameCharacteristicSelection = buildInputFields();
        ScrollPane myGameScroller = new ScrollPane();
        myGameScroller.setContent(gameCharacteristicSelection);
        Node nextButton = makeButton(myButtonText, myButtonEvent);
        return styleScene(newGameLabel, myGameScroller, nextButton);
    }


    protected boolean isValid(Node node){
        if (node instanceof ComboBox) return ((ComboBox) node).getValue() != null;
        try{
            TextField textNode = (TextField) node;
            return isInteger(textNode.getText());
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean isInteger(String text){
        try{
            Integer.parseInt(text);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
