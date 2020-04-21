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
import ooga.player.exceptions.NewUserDefinedGameException;

import java.util.*;

public abstract class UserDefinedGameScreen extends SuperScreen {
    protected static final String SPACE = " ";
    protected static final String KEYS_RESOURCES_PATH = "resources.";
    protected static final String STRINGS_RESOURCES_PATH = "NewGameStrings";
    protected Map<String, Node> userInputFields = new HashMap<>();
    protected Map<String, String> selectedAttributes = new HashMap<>();
    protected ResourceBundle myKeysResources;
    protected ResourceBundle newGameStringsResources;
    protected String myButtonText;
    protected String gameLabel;
    protected EventHandler<ActionEvent> myButtonEvent;

    public UserDefinedGameScreen(Player thisPlayer) {
        super(thisPlayer);
        newGameStringsResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + STRINGS_RESOURCES_PATH);
    }

    public Scene setUpScene(){
        Label newGameLabel = new Label(newGameStringsResources.getString(gameLabel));
        Node gameCharacteristicSelection = buildInputFields();
        ScrollPane myGameScroller = new ScrollPane();
        myGameScroller.setContent(gameCharacteristicSelection);
        Node nextButton = makeButton(myButtonText, myButtonEvent);
        return styleScene(newGameLabel, myGameScroller, nextButton);
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

    public Map<String, String> getUserSelectedAttributes() { return selectedAttributes; }

    protected void buildMap(){
        for (String key:userInputFields.keySet()){
            String value = getValidText(userInputFields.get(key));
            if (value==null) throw new NewUserDefinedGameException();
            selectedAttributes.put(key, value);
        }
        if (!additionalValidation()) throw new NewUserDefinedGameException();
    }

    protected abstract boolean additionalValidation();


    protected String getValidText(Node node){
        if (node instanceof ComboBox) {
            ComboBox boxNode = (ComboBox) node;
            if (!boxNode.getSelectionModel().isEmpty()) return boxNode.getValue().toString();
        } else if (node instanceof TextField){
            TextField textNode = (TextField) node;
            if (isInteger(textNode.getText())) return textNode.getText();
        }
        return null;
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
