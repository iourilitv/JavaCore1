package a1netChatWithPrWin.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.IOException;

public class PrivateChatStage extends Stage {

    String chatCompanionNick;//кому
    DataOutputStream out;
    VBox prVBoxChat;//TODO Не помогло.Удалить

    public PrivateChatStage(Controller controller, VBox prVBoxChat) throws IOException {
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

    }

}
