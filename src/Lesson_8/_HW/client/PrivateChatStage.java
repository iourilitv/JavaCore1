package Lesson_8._HW.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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

    String chatCompanionNick;//кому
    DataOutputStream out;
    DataInputStream in;
    VBox prVBoxChat;//TODO Не помогло.Удалить

    public PrivateChatStage(Controller controller) throws IOException {
        this.chatCompanionNick = controller.getChatCompanionNick();
        this.in = controller.getIn();
        this.out = controller.getOut();
        this.prVBoxChat = controller.getPrVBoxChat();//TODO Не помогло.Удалить

        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream("privateChat.fxml"));
        setTitle("Private chat with " + chatCompanionNick);
        Scene scene = new Scene(root, 300, 400);
        setScene(scene);

        //TODO временно.
        System.out.println("PrivateChatStage.chatCompanionNick: " + chatCompanionNick);
    }

}
