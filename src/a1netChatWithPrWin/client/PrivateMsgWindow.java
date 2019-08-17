package a1netChatWithPrWin.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.IOException;

public class PrivateMsgWindow extends Stage {

    //TODO pr.window opening.Deleted
    //String chatCompanionNick;//кому
    //TODO pr.window opening.Added
    String nick;//имя партнера (кому/от кого)
    String msg;

    DataOutputStream out;

    //TODO pr.window opening.Deleted
    //VBox prVBoxChat;//TODO Не помогло.Удалить

    //TODO pr.window opening.Deleted
    /*public PrivateChatStage(Controller controller, VBox prVBoxChat) throws IOException {
        this.chatCompanionNick = controller.getChatCompanionNick();
        this.out = controller.getOut();
        this.prVBoxChat = prVBoxChat;//TODO Не помогло.Удалить

        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream("privateChat.fxml"));
        setTitle("Private chat with " + chatCompanionNick);
        Scene scene = new Scene(root, 300, 400);
        setScene(scene);

        //TODO временно.
        System.out.println("PrivateChatStage.chatCompanionNick: " + chatCompanionNick);

    }*/
    //TODO pr.window opening.Added
    public PrivateMsgWindow(Controller controller, String nick, String msg) throws IOException {
        this.nick = nick;
        this.msg = msg;
        this.out = controller.getOut();

        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream("privateMsgWindow.fxml"));

        //называем окно в зависимости от направления
        if(msg == null){//для отправки сообщения
            setTitle("Private message to " + nick);
        } else {//при получении сообщения
            setTitle("Private message from " + nick);
        }

        Scene scene = new Scene(root, 350, 100);
        setScene(scene);

        //TODO временно.
        System.out.println("PrivateChatStage.chatCompanionNick: " + nick);

    }

}
