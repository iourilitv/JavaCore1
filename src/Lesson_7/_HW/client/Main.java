package Lesson_7._HW.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 7. Написание сетевого чата. Часть I. Прикручиваем наш чат к DB SQLite.
 * Home work.
 * @author Yuriy Litvinenko.
 * 1. Разобраться с кодом.
 *  - если ничего не ввести в поле login или password, то в клиенте возникает java.io.EOFException,
 *    а в сервере - Exception in thread "Thread-0" java.lang.ArrayIndexOutOfBoundsException: 1
 * 	    at Lesson_7._HW.server.ClientHandler$1.run(ClientHandler.java:35).
 *    Исправить, чтобы выводилось "Неверный логин/пароль!"
 *  - при вводе в TextField в клиенте /end, в TextArea выводится /serverclosed, что вызывает в клиенте java.io.EOFException.
 * 2. *Реализовать личные сообщения так: если клиент пишет «/w nick3 Привет», то только клиенту
 *    с ником nick3 должно прийти сообщение «Привет».
 * 3. *Добавить в авторизацию проверка пользователя и не авторизовывать пользователя
 *    под ником, который уже авторизован.
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
