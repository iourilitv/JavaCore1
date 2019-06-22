package Lesson_6._HW.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 6. Работа с сетью.
 * Home work.
 * @author Yuriy Litvinenko.
 * 1. Разобраться с кодом.
 * 2. Корректно закрывать сокеты и удалять клиентов и списка.
 * Серверная часть сетевого чата. Все сообщения клиентов транслируются друг другу через сервер.
 */
public class Main {
    private Vector<ClientHandler> clients;

    public Main() {
        clients = new Vector<>();
        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен");

            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился");
                clients.add(new ClientHandler(socket, this));
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
        }
    }

    public void broadCastMsg(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }
}
