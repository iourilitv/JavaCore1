package Lesson_7.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Vector;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 6. Работа с сетью.
 * Home work.
 * @author Yuriy Litvinenko.
 * 1. Разобраться с кодом.
 * 2. Корректно закрывать сокеты и удалять клиентов и списка.
 *   Это позволит побороть только исключение из-за отправки сервером сообщения в закрытый сокет.
 * Серверная часть сетевого чата. Все сообщения клиентов транслируются друг другу через сервер.
 */
public class Main {
    private Vector<ClientHandler> clients;

    public Main() {
        //создаем список клиентов в виде синхронизированного ArrayList
        clients = new Vector<>();
        //инициализируем объекты с пустыми значениями, чтобы не получить исключение, что объекта нет
        ServerSocket server = null;
        Socket socket = null;

        try {
            //создаем сокет для серверной части
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен");

            while (true) {
                //создаем сокет для клиентской части. При создании объекта типа Socket неявно
                //устанавливается соединение клиента с сервером
                socket = server.accept();
                System.out.println("Клиент подключился");

                //добавляем клиента в список
                //TODO UPD HW.Удалил
                //clients.add(new ClientHandler(socket, this));
                //TODO UPD HW.Добавил
                subscribe(new ClientHandler(socket, this));

                //TODO временно
                System.out.println("Main.try.while. after line clients.add(...) - Arrays.toString(clients.toArray()):\n" + Arrays.toString(clients.toArray()));

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

    //TODO UPD HW.Добавил
    /**
     * Метод добавления клиента в списочный массив
     * @param client - подключивщийся клиент
     */
    public void subscribe(ClientHandler client){
        clients.add(client);
    }

    //TODO UPD HW.Добавил
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

    //TODO UPD HW.Удалил
    /*//TODO исправление ошибки выхода.Добавил
    /**
     * Метод перебирающий списочный массив и удаляющий элемент списка с совпадающим сокетом
     * @param socket - сокет элемента, который требуется удалить из списка
     /
    public void closeClient(Socket socket){
        for (int i = 0; i < clients.size(); i++) {
            if(socket.equals(clients.get(i).getSocket())){
                clients.remove(i);

                //TODO временно
                System.out.println("Main. closeClient. /end. after remove. clients.toString:\n" + clients.toString());
            }
        }
    }*/
}
