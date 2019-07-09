package Lesson_8._HW.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 8. Написание сетевого чата. Часть II.
 * Home work.
 * @author Yuriy Litvinenko.
 * DONE 1. Форматирование сообщения, свой-чужой. Свои справа, чужие слева.
 * DONE 2. Регистрация нового пользователя. Через БД.
 * 3. Переподключение клиента при падении сервера.
 * 4. Хранить черный список в БД
 * DONE + проверка л/с(чтобы нельзя было отправлять общее или личное сообщение, если отправитель
 *     в черном списке у отправителя и получателя). Добавить исключение получения сообщения всем на стороне клиента,
 *     у которого отправитель в черном списке.
 * 5. Переработать отправку л/с с учетом он-лайн списка (разработать диалоговое окно).
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
        //рассылаем новый список клиентов
        broadcastClientList();
    }

    /**
     * Метод удаления клиента из списочного массива
     * @param client - отключивщийся клиент
     */
    public void unsubscribe(ClientHandler client){
        clients.remove(client);
        //рассылаем новый список клиентов
        broadcastClientList();
    }

    /**
     * Метод отправки всем одного сообщения с проверкой черного списка отправителя
     * @param sender - отправитель
     * @param msg - рассылаемое сообщение
     */
    public void broadcastMsg(ClientHandler sender, String msg) {
        for (ClientHandler o : clients) {
            //проверяем не отправляет ли он сам себе,
            //а также не находится ли отправитель в черном списке получателя и наоборот,

            //TODO L8hwTask4.AddBlacklistsToDB.Удалил
            //if (!o.equals(sender) && !o.checkBlackList(sender.getNick()) &&
                    //!sender.checkBlackList(o.getNick())
            //) {
            //TODO L8hwTask4.AddBlacklistsToDB.Добавил
            if (!o.equals(sender) && !AuthService.checkUserInBlacklistDB(o.getNick(), sender.getNick()) &&
                    !AuthService.checkUserInBlacklistDB(sender.getNick(), o.getNick())
                ) {

                //отправляем сообщение адресату(получателю)
                o.sendMsg(msg);
            }
        }
    }

    /**
     * Метод отправки персональных сообщений с проверкой черного списка получателя
     * @param sender - объект отправителя
     * @param nickOfRecipient - ник адресата(получателя)
     * @param msg - отправляемое сообщение
     */
    public void sendMsgToNick(ClientHandler sender, String nickOfRecipient, String msg){
        //в списке авторизованных ищем адресата(получателя) по нику
        for (ClientHandler r: clients) {
            //проверяем есть ли соотвествие
            if(r.getNick().equals(nickOfRecipient)){

                //проверяем не находится ли отправитель черном списке получателя
                //TODO L8hwTask4.AddBlacklistsToDB.Удалил
                //if(!r.checkBlackList(sender.getNick())){
                //TODO L8hwTask4.AddBlacklistsToDB.Добавил
                if(!AuthService.checkUserInBlacklistDB(nickOfRecipient, sender.getNick())){

                    //TODO L8hwTask5.Удалил
                    //r.sendMsg("from " + sender.getNick() + ": " + msg);
                    //отправляем сообщение отправителю
                    //sender.sendMsg("to " + nickOfRecipient + ": " + msg);
                    //TODO L8hwTask5.Добавил
                    if(msg.startsWith("/inv")){

                        //TODO Временно.OK
                        //System.out.println("MainServer.sendMsgToNick. nick:" + sender.getNick() + " sent msg:" + msg);

                        //отправляем сообщение адресату //отправляем приглашение партнеру початиться приватно
                        r.sendMsg(msg);
                        return;
                    }

                    //отправляем сообщение адресату
                    r.sendMsg("from " + sender.getNick() + ": " + msg);
                    //отправляем сообщение отправителю
                    sender.sendMsg("to " + nickOfRecipient + ": " + msg);
                    return;
                } else{
                    //если отправитель черном списке получателя (цикл не прервался по return)
                    sender.sendMsg("Вы в черном списке адресата с ником " + nickOfRecipient + " !");
                    return;
                }
            }
        }
        //если в списке не нашлось клиента с таким ником (цикл не прервался по return)
        sender.sendMsg("Адресат с ником " + nickOfRecipient + " не найден в чате!");
    }

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

    //Метод отправки списка пользователей в виде строки всем клиентам
    public void broadcastClientList() {
        StringBuilder sb = new StringBuilder();
        sb.append("/clientlist ");
        //дополняем строку списком ников подключенных клиентов
        for (ClientHandler o : clients) {
            sb.append(o.getNick() + " ");
        }
        //собираем окончательное сообщение
        String out = sb.toString();
        //отправляем список каждому пользователю
        for (ClientHandler o : clients) {
            o.sendMsg(out);
        }
    }
}
