package Lesson_4.HW.netChat.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class NetChatController {

    @FXML
    TextArea textArea;

    @FXML
    TextField textField;

    public void sendMsg() {
        textArea.appendText(textField.getText() + "\n");
        textField.clear();
        textField.requestFocus();
    }

}
