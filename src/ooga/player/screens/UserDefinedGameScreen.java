package ooga.player.screens;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ooga.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class UserDefinedGameScreen extends SuperScreen {
    protected static final String KEYS_RESOURCES_PATH = "resources.";
    protected List<String> integerInputs = new ArrayList<>();
    protected Map<String, Node> userInputFields = new HashMap<>();

    public UserDefinedGameScreen(Player thisPlayer) {
        super(thisPlayer);
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
