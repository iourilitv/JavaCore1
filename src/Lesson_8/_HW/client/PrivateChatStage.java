package Lesson_8._HW.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

//TODO L8hwTask5.Добавил
/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 8. Написание сетевого чата. Часть II.
 * Home work.
 * @author Yuriy Litvinenko.
 * 5. Переработать отправку л/с с учетом он-лайн списка (разработать диалоговое окно).
 */
public class PrivateChatStage extends Stage {

    private List<String> clientList;
    private String nick;
    Controller controller;
    DataOutputStream out;

    //TODO L8hwTask5.С одним контроллером.Удалил
    /*//создаем экземпляр контроллера персонального чата
    PrivateChatController prChController;*/

    public PrivateChatStage(List<String> clientList, String nick, Controller controller) throws IOException {
        this.clientList = clientList;
        this.nick = nick;
        this.controller = controller;

        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream("privateChat.fxml"));

        //TODO L8hwTask5.С одним контроллером.Удалил
        //prChController = loader.getController();

        Stage privateStage = new Stage();
        privateStage.setTitle("Private chat");//primaryStage.
        Scene scene = new Scene(root, 348, 348);
        privateStage.setScene(scene);
        privateStage.show();

        //TODO L8hwTask5.С одним контроллером.Удалил
        //prChController.out = controller.out;

        //TODO временно.
        System.out.println("PrivateChatStage.");
        System.out.println(".out: " + out);
    }



}
