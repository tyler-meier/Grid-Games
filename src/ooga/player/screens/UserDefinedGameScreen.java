package ooga.player.screens;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
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

/**
 * Super screen for all of the screens related to making a new user defined game.
 * @author Natalie Novitsky.
 */
public abstract class UserDefinedGameScreen extends SuperScreen {
    protected static final String SPACE = " ";
    protected static final String KEYS_RESOURCES_PATH = "resources.";
    protected static final String STRINGS_RESOURCES_PATH = "NewGameStrings";
    protected static final int SPACING_1 = 10;
    protected Map<String, Node> userInputFields = new HashMap<>();
    protected Map<String, String> selectedAttributes = new HashMap<>();
    protected Map<String, Label> labelMap = new HashMap<>();
    protected ResourceBundle myKeysResources, newGameStringsResources;
    protected String myButtonText, gameLabel;
    protected EventHandler<ActionEvent> myButtonEvent;
    protected VBox inputField;
    protected int[] stateRange;

    /**
     * Constructor for this class, calls super screen superclass, but is also a superclass itself so subclasses
     * also call this to set up
     * @param thisPlayer current player
     */
    public UserDefinedGameScreen(Player thisPlayer) {
        super(thisPlayer);
        newGameStringsResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + STRINGS_RESOURCES_PATH);
    }

    /**
     * Sets up the scene for the appropriate user defined game screen.
     * @return the styled scene
     */
    public Scene setUpScene(){
        Label newGameLabel = new Label(newGameStringsResources.getString(gameLabel));
        inputField = buildInputFields();
        ScrollPane myGameScroller = new ScrollPane();
        myGameScroller.setContent(inputField);
        myGameScroller.setFitToWidth(true);
        Node nextButton = makeButton(myButtonText, myButtonEvent);
        VBox buttons = new VBox(nextButton, makeHomeButton());
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(SPACING_1);
        screenSpecificSetup();
        return styleScene(newGameLabel, myGameScroller, buttons);
    }

    /**
     * @return a map of the user selected game attributes for their new game.
     */
    public Map<String, String> getUserSelectedAttributes() {
        return selectedAttributes;
    }

    /**
     * Sets the range of the allowed states for the user's new game.
     * @param range
     */
    public void setStateRange(int[] range){
        stateRange = range;
    }

    protected abstract void screenSpecificSetup();

    protected boolean inRange(int value){
        return value>=stateRange[0] && value<=stateRange[1];
    }

    protected VBox buildInputFields(){
        VBox myVBox = new VBox();
        myVBox.setSpacing(SPACING_1);
        myVBox.setAlignment(Pos.CENTER);
        for(String key : Collections.list(myKeysResources.getKeys())){
            Label label = new Label(newGameStringsResources.getString(key));
            makeVBoxContents(key);
            labelMap.put(key, label);
            myVBox.getChildren().addAll(label, userInputFields.get(key));
        }
        return myVBox;
    }

    private void makeVBoxContents(String key){
        if (isInteger(myKeysResources.getString(key))) {
            userInputFields.put(key, new TextField());
            selectedAttributes.put(key, myKeysResources.getString(key));
        }
        else {
            ComboBox<String> dropDown = new ComboBox<>();
            String[] options = myKeysResources.getString(key).split(SPACE);
            for (String s:options) dropDown.getItems().add(s);
            userInputFields.put(key, dropDown);
        }
    }

    protected void buildMap(){
        for (String key:userInputFields.keySet()){
            String value = getValidText(userInputFields.get(key));
            if (value==null) {
                if (inputField.getChildren().contains(userInputFields.get(key))) throw new NewUserDefinedGameException();
            } else selectedAttributes.put(key, value);
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
            int value = Integer.parseInt(text);
            return value>=0;
        }catch (Exception e){
            return false;
        }
    }
}
