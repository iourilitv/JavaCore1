package Lesson_7._HW.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Vector;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 7. Написание сетевого чата. Часть I.
 * Прикручиваем наш чат к DB SQLite.
 * Организуем сервис авторизации.
 * @author Artem Evdokimov.
 */
public class MainServer {
    private Vector<ClientHandler> clients;

    //TODO 1. DB connect.Удалил
    //public MainServer() {
    //TODO 1. DB connect.Добавил
    public MainServer() throws SQLException {//TODO лишнее  throws SQLException
        //создаем список клиентов в виде синхронизированного ArrayList
        clients = new Vector<>();
        //инициализируем объекты с пустыми значениями, чтобы не получить исключение, что объекта нет
        ServerSocket server = null;
        Socket socket = null;

        try {
            //TODO 1. DB connect.Добавил
            //устанавливаем связь с БД в момент запуска сервера
            AuthService.connect();
            //TODO временно.тестируем запрос авторизации//TODO ERR.java.lang.ClassNotFoundException: org.sqlite.JDBC
            //String str = AuthService.getNickByLoginAndPass("login1", "password1");

            //создаем сокет для серверной части
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен");

            while (true) {
                //создаем сокет для клиентской части. При создании объекта типа Socket неявно
                //устанавливается соединение клиента с сервером
                socket = server.accept();
                System.out.println("Клиент подключился");

                //TODO 1. DB connect.Удалил
                // подписку пересена в ClientHandler в цикл авторизации while, т.к.нельзя подписывать неавторизованных клиентов
                //добавляем клиента в список
                //subscribe(new ClientHandler(socket, this));
                //TODO 1. DB connect.Добавил
                new ClientHandler(socket, this);

                //TODO временно
                System.out.println("MainServer.try.while. after line clients.add(...) - Arrays.toString(clients.toArray()):\n" + Arrays.toString(clients.toArray()));

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //TODO 1. DB connect.Добавил
            //отключаем БД при закрытии серверного приложения
            AuthService.disconnect();
        }
    }

    /**
     * Метод добавления клиента в списочный массив
     * @param client - подключивщийся клиент
     */
    public void subscribe(ClientHandler client){
        clients.add(client);
    }

    /**
     * Метод удаления клиента из списочного массива
     * @param client - отключивщийся клиент
     */
    public void unsubscribe(ClientHandler client){
        clients.remove(client);
    }

    /**
     * Метод отправки всем одного сообщения
     * @param msg
     */
    public void broadCastMsg(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }

}
