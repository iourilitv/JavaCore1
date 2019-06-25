package Lesson_6._HW.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 6. Работа с сетью.
 * Home work.
 * @author Yuriy Litvinenko.
 * 1. Разобраться с кодом.
 * 2. Корректно закрывать сокеты и удалять клиентов и списка.
 *    Это позволит побороть только исключение из-за отправки сервером сообщения в закрытый сокет.
 * Клиентская часть сетевого чата. Все сообщения клиентов транслируются друг другу через сервер.
 * Чтобы запустить несколько клиентов одновременно, нужно в Edit Configurations
 * класса client.Main установить Allow parallel run(справа вверху)
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Chat 2k19");
        Scene scene = new Scene(root, 310, 350);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
