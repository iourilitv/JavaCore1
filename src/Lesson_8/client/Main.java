package Lesson_8.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 8. Написание сетевого чата. Часть II.
 * @author Artem Evdokimov.
 * Improving the home work of Lesson_7.
 * DONE 1. Закрывать сокет, если нажать крестик закрытия окна в клиенте. Сейчас - исключение.
 * DONE 2. Добавить "черный" список каждому клиенту. Чтобы нельзя было отправлять общее
 *    или личное сообщение, если отправитель в черном списке у получателя - режим Антиспам.
 * DONE 3. Добавить отображение списка авторизованных пользователей.
 * DONE ERR1. В клиенте возникает java.net.SocketException: Socket closed. В коде урока таже проблема.
 * Это происходит только в следующем случае,
 * клиент авторизовался;
 * отлогинился, отправив /end (сообщение на отключение в консоли не появилось);
 * и закрыл окно по кресту.
 * Но, если не авторизовываться и закрыть окно или не отправлять /end и закрыть окно, то все работает.
 */
public class Main extends Application {

    //TODO hwImproving1.Добавил
    //создаем экземпляр контроллера
    Controller contr;

    @Override
    public void start(Stage primaryStage) throws Exception{

        //TODO hwImproving1.Удалил
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        //TODO hwImproving1.Добавил
        //чтобы получить доступ к контроллеру
        //лоадер вынесли отдельно, чтобы с ним удобнее было работать
        FXMLLoader loader = new FXMLLoader();
        //с помощью метода getResourceAsStream извлекаем данные из лоадера, чтобы
        //вызвать метод getController для получения контроллера
        Parent root = loader.load(getClass().getResourceAsStream("sample.fxml"));
        contr = loader.getController();

        primaryStage.setTitle("Chat 2k19");
        Scene scene = new Scene(root, 350, 350);
        primaryStage.setScene(scene);
        primaryStage.show();

        //TODO hwImproving1.Добавил
        //определяем действия по событию закрыть окно по крестику через лямбда
        //лямбда здесь - это замена анонимного класса типа new Runnable
        //в лямбда event - аргумент(здесь некое событие), {тело лямбды - операции}
        primaryStage.setOnCloseRequest(event -> {
            contr.dispose();//dispose - располагать, размещать
            //сворачиваем окно
            Platform.exit();
            //указываем системе, что выход без ошибки
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
