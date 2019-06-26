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
public class MainServer {
    private Vector<ClientHandler> clients;

    public MainServer() throws SQLException {
        //создаем список клиентов в виде синхронизированного ArrayList
        clients = new Vector<>();
        //инициализируем объекты с пустыми значениями, чтобы не получить исключение, что объекта нет
        ServerSocket server = null;
        Socket socket = null;

        try {
            //устанавливаем связь с БД в момент запуска сервера
            AuthService.connect();

            //создаем сокет для серверной части
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен");

            while (true) {
                //создаем сокет для клиентской части. При создании объекта типа Socket неявно
                //устанавливается соединение клиента с сервером
                socket = server.accept();
                System.out.println("Клиент подключился");
                //создаем объект нового клиента
                new ClientHandler(socket, this);
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
            //отключаем БД при закрытии серверного приложения
            AuthService.disconnect();
        }
    }

    //TODO HW_task3.Добавил
    public Vector<ClientHandler> getClients() {
        return clients;
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

    //TODO HW_task2.Добавил
    /**
     * Метод сортировки и отправки персональных сообщений
     * @param str
     */
    public void sendMsgToNick(ClientHandler sender, String str){
        //TODO когда добавится адресная книга, этот блок не понадобится
        //разделяем по пробелу на три ячейки массива
        //3 - limit - количество возвращаемых строк.
        String[] temp = str.split(" ", 3);
        //выделяем ник адресата
        String nickOfRecipient = temp[1];
        //выделяем собственно текст сообщения
        String msg = temp[2];

        if(!sender.getNick().equals(nickOfRecipient)){
            for (ClientHandler c: clients) {
                if(c.getNick().equals(nickOfRecipient)){
                    c.sendMsg("Персонально от " + sender.getNick() + ": " + msg);
                    break;
                }
            }
        }
        else{
            //отправка предупреждение отправителю
            sender.sendMsg("Нельзя отправлять самому себе!");
        }
    }

}
