package a1netChatWithPrWin.client;

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

    //TODO Test in.read in PrivateChatStage.Добавил.Не работает
    //Controller controller;

    String chatCompanionNick;//кому
    DataOutputStream out;
    DataInputStream in;//TODO лишнее.Удалить
    VBox prVBoxChat;//TODO Не помогло.Удалить

    public PrivateChatStage(Controller controller, VBox prVBoxChat) throws IOException {

        //TODO Test in.read in PrivateChatStage.Добавил.Не работает
        //this.controller = controller;

        this.chatCompanionNick = controller.getChatCompanionNick();
        this.in = controller.getIn();//TODO лишнее.Удалить
        this.out = controller.getOut();
        //this.prVBoxChat = controller.getPrVBoxChat();//TODO Не помогло.Удалить
        this.prVBoxChat = prVBoxChat;//TODO Не помогло.Удалить

        //TODO Test in.read in PrivateChatStage.Добавил.Не работает
        //receivingMsg();//TODO ERR.Здесь запускается до или после открытия окна приватного чата, но in не слушает
        //TODO и на prVBoxChat - Exception in thread "JavaFX Application Thread" java.lang.NullPointerException

        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream("privateChat.fxml"));
        setTitle("Private chat with " + chatCompanionNick);
        Scene scene = new Scene(root, 300, 400);
        setScene(scene);

        //TODO временно.
        System.out.println("PrivateChatStage.chatCompanionNick: " + chatCompanionNick);

        //TODO Test in.read in PrivateChatStage.Добавил.Не работает
        //receivingMsg();//TODO ERR.Здесь не запускается до или после открытия окна приватного чата
        //TODO и на prVBoxChat - Exception in thread "JavaFX Application Thread" java.lang.NullPointerException
    }

    //TODO Test in.read in PrivateChatStage.Добавил.Не работает
    //метод получения сообщения
    /*void receivingMsg(){
        Thread prRecThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String str = null;
                try {
                    while (true) {

                        //TODO временно
                        System.out.println("receivingMsg().before in.readUTF() str: " + str);

                        //TODO временно//TODO и на prVBoxChat - Exception in thread "JavaFX Application Thread" java.lang.NullPointerException
                        //controller.showMessage(prVBoxChat, Pos.TOP_LEFT, str);

                        str = in.readUTF();

                        //TODO временно
                        System.out.println("receivingMsg().after in.readUTF() str: " + str);

                        //TODO временно
                        controller.showMessage(prVBoxChat, Pos.TOP_LEFT, str);

                        if (str.startsWith("/w")){

                            //TODO временно
                            System.out.println("receivingMsg().startsWith(\"/w\" str:" + str);

                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        prRecThread.start();
        //prRecThread.interrupt();

    }*/

}
