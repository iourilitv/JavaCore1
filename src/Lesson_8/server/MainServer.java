package Lesson_8.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 8. Написание сетевого чата. Часть II.
 * @author Artem Evdokimov.
 * Improving the home work of Lesson_7.
 * 1. Закрывать сокет, если нажать крестик закрытия окна в клиенте. Сейчас - исключение.
 * 2.
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
                for (ClientHandler c: clients) {
                    //в списке авторизованных ищем адресата по нику
                    if(c.getNick().equals(nickOfRecipient)){

                        //отправляем сообщение адресату
                        c.sendMsg("from " + sender.getNick() + ": " + msg);
                        //отправляем сообщение отправителю
                        sender.sendMsg("to " + nickOfRecipient + ": " + msg);
                        return;
                    }
                }
                //если в списке не нашлось клиента с таким ником (цикл не прервался по return)
                sender.sendMsg("Адресат с ником " + nickOfRecipient + " не найден в чате!");
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

    /**
     * Метод проверки не авторизовался ли кто-то уже под этим ником(есть ли в списке клиент с таким ником)
     * @param nick - проверяемый ник
     * @return true, если такой клиент с таким ником уже авторизован
     */
    boolean isThisNickAuthorized(String nick){
        for (ClientHandler c: clients) {
            if(c.getNick().equals(nick)){
                return true;
            }
        }
        return false;
    }
}
