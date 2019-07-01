package Lesson_8._HW.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

//TODO L8hwTask5.С одним контроллером.Удалил
//TODO L8hwTask5.Добавил
/**
 * Класс отвечает за сервис персональной переписки в отдельном окне.
 */
/*public class PrivateChatController {
    @FXML
    VBox vBoxChat;

    @FXML
    TextField textField;

    @FXML
    Button btn1;

    @FXML
    HBox bottomPanel;

    //TODO удалить?
    //Socket socket;
    //DataInputStream in;
    DataOutputStream out;

    //TODO удалить?
    //private boolean isAuthorized;

    //TODO удалить?
    PrivateChatStage privateChatStage;

    //метод для отправки сообщений
    public void sendMsg(ActionEvent actionEvent) {
        try {
            //не показываем служебные сообщения у себя
            if(!textField.getText().startsWith("/")) {
                Label label = new Label(textField.getText());
                VBox vBox = new VBox();

                //условие (от кого?) для определения расположения
                //вытаскиваем из события источник (кнопка или текстовое поле)
                //и сравниваем с теми же источниками контроллера отправителя
                if (actionEvent.getSource().equals(this.btn1) ||
                        actionEvent.getSource().equals(this.textField)
                ) {//if (если мое - сообщение справа)
                    vBox.setAlignment(Pos.TOP_RIGHT);
                } else {
                    vBox.setAlignment(Pos.TOP_LEFT);
                }
                //добавляем метку в бокс
                vBox.getChildren().add(label);
                //добавляем vBox в общий бокс чата
                vBoxChat.getChildren().add(vBox);

            }

            //TODO временно.
            System.out.println("PrivateChatController.sendMsg.");
            System.out.println(".textField.getText(): " + textField.getText());
            System.out.println(".out: " + out);

            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();

        } catch(IOException e){
            e.printStackTrace();
        }
    }

}*/
