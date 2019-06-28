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
 * DONE - если ничего не ввести в поле login или password, то в клиенте возникает java.io.EOFException,
 *    а в сервере - Exception in thread "Thread-0" java.lang.ArrayIndexOutOfBoundsException: 1
 * 	    at Lesson_7._HW.server.ClientHandler$1.run(ClientHandler.java:35).
 *    Исправить, чтобы выводилось "Неверный логин/пароль!"
 *  - при вводе в TextField в клиенте /end, в TextArea выводится /serverclosed, что вызывает в клиенте java.io.EOFException.
 * DONE 2. *Реализовать личные сообщения так: если клиент пишет «/w nick3 Привет», то только клиенту
 *    с ником nick3 должно прийти сообщение «Привет».
 * DONE 3. *Добавить в авторизацию проверка пользователя и не авторизовывать пользователя
 *    под ником, который уже авторизован.
 *     Реализовал двумя способами отличия в расположении метода isThisNickAuthorized:
 *     Вариант1 - в классе ClientHandler; Вариант2 - в классе MainServer.
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

                //TODO ERR. Server always write Client have connected.Удалил
                //System.out.println("Клиент подключился");

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
        String nickOfRecipient;//ник адресата
        String msg;//текст сообщения адресату
        //разделяем по пробелу на splitLimit ячеек массива,
        //чтобы избежать ошибки при неполном вводе сервисного сообщения
        //limit = splitLimit - количество возвращаемых строк.
        int splitLimit = 3;
        String[] temp = str.split(" ", splitLimit);

        //проверка корректности синтаксиса сервисного сообщения
        if(temp.length >= splitLimit){
            //выделяем ник адресата
            nickOfRecipient = temp[1];
            //выделяем собственно текст сообщения
            msg = temp[2];

            //проверка не отправляется ли сообщение самому себе
            if(!sender.getNick().equals(nickOfRecipient)){

                //TODO hw7Update.Можно проще - через return и добавил сообщение себе - кому отправил.Удалил
                //boolean flag = false;

                    for (ClientHandler c: clients) {
                        if(c.getNick().equals(nickOfRecipient)){
                        //TODO hw7Update.Можно проще - через return и добавил сообщение себе - кому отправил.Удалил
                        /*c.sendMsg("Персонально от " + sender.getNick() + ": " + msg);
                        flag = true;
                        break;*/
                        //TODO hw7Update.Можно проще - через return и добавил сообщение себе - кому отправил.Добавил
                        //отправляем сообщение адресату
                        c.sendMsg("from " + sender.getNick() + ": " + msg);
                        //отправляем сообщение отправителю
                        sender.sendMsg("to " + nickOfRecipient + ": " + msg);
                        return;
                    }
                }
                //если в списке не нашлось клиента с таким ником (цикл не прервался по return)
                //TODO hw7Update.Можно проще - через return и добавил сообщение себе - кому отправил.Удалил
                /*if (!flag){
                    //отправка предупреждения отправителю
                    sender.sendMsg("Адресат с таким ником не авторизован!");
                }*/
                //TODO hw7Update.Можно проще - через return и добавил сообщение себе - кому отправил.Добавил
                sender.sendMsg("Адресат " + nickOfRecipient + " не найден в чате!");
            }
            else{
                //отправка предупреждения отправителю
                sender.sendMsg("Нельзя отправлять самому себе!");
            }
        }
        else{
            //отправка предупреждения отправителю
            sender.sendMsg("Неверный синтаксис сервисного сообщения!");
        }
    }

    //TODO hw7Update.Можно по другому, если проверку реализовать в ClientHandler перед вызовом sendMsgToNick
    /*public void sendPersonalMsg(ClientHandler from, String nickTo, String msg) {
        for (ClientHandler o : clients) {
            if (o.getNick().equals(nickTo)) {
                o.sendMsg("from " + from.getNick() + ": " + msg);
                from.sendMsg("to " + nickTo + ": " + msg);
                return;
            }
        }
        from.sendMsg("Клиент с ником " + nickTo + " не найден в чате");
    }*/

    //TODO HW_task3.Вариант2.Добавил.
    /**
     * Метод проверки не авторизовался ли кто-то уже под этим ником(есть ли в списке клиент с таким ником)
     * @param nick - проверяемый ник
     * @return true, если такой клиент с таким ником уже авторизован
     */
    //TODO hw7Update.Удалил
    /*boolean isThisNickAuthorized(String nick){
        boolean flag = false;

        for (ClientHandler c: clients) {
            if(c.getNick().equals(nick)){
                flag = true;
                break;
            }
        }
        return flag;
    }*/
    //TODO hw7Update.Добавил
    boolean isThisNickAuthorized(String nick){
        for (ClientHandler c: clients) {
            if(c.getNick().equals(nick)){
                return true;
            }
        }
        return false;
    }
}
