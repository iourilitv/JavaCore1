package Lesson_7._HW.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private MainServer server;
    private String nick;

    public ClientHandler(Socket socket, MainServer server) {
        try {
            this.socket = socket;
            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            //анонимный класс
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // цикл для авторизации. Крутится бесконечно, пока не авторизуется
                        while (true) {
                            String str = in.readUTF();
                            // если сообщение начинается с /auth
                            if(str.startsWith("/auth")) {
                                //TODO HW_task1.ERR_1.Удалил
                                //String[] tokens = str.split(" ");
                                //TODO HW_task1.ERR_1.Добавил.Работает
                                //чтобы избежать ошибки при пустом вводе в поля login или пароль
                                int splitLimit = 3;
                                String[] tokens = str.split(" ", splitLimit);

                                // Вытаскиваем данные из БД //здесь: tokens[1] - логин, tokens[2] - пароль
                                String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                                if (newNick != null ) {

                                    //проверяем не авторизовался ли кто-то уже под этим ником
                                    //TODO HW_task3.Вариант1.Удалил
                                    //if(!isThisNickAuthorized(newNick)){
                                    //TODO HW_task3.Вариант2.Добавил
                                    if(!server.isThisNickAuthorized(newNick)){
                                        //отправляем сообщение об успешной авторизации
                                        sendMsg("/authok");
                                        nick = newNick;
                                        //подписываем клиента при успешной авторизации и выходим из цикла
                                        server.subscribe(ClientHandler.this);

                                        //TODO ERR. Server always write Client have connected.Добавил
                                        System.out.println("Клиент с ником " + nick + " подключился.");

                                        break;
                                    }
                                    else{
                                        sendMsg("Пользователь с таким ником уже авторизован!");
                                    }

                                    //TODO HW_task3.Удалил
                                    /*//отправляем сообщение об успешной авторизации
                                    sendMsg("/authok");
                                    nick = newNick;
                                    //подписываем клиента при успешной авторизации и выходим из цикла
                                    server.subscribe(ClientHandler.this);
                                    break;*/

                                } else {
                                    sendMsg("Неверный логин/пароль!");
                                }
                            }
                        }

                        // блок для отправки сообщений
                        while (true) {
                            String str = in.readUTF();
                            //TODO hw7Update.Добавил. Все служебные сообщения в одном блоке для удобства
                            //отлавливаем все служебные сообщения
                            if (str.startsWith("/")){

                                //запрос на отключение
                                if (str.equals("/end")) {
                                    //закрываем клиента после удаления его из списка
                                    out.writeUTF("/serverclosed");//TODO выдает исключение в клиенте
                                    break;
                                }
                                //TODO HW_task2.Добавил
                                //оправка персонального сообщения
                                if(str.startsWith("/w")) {
                                    //ClientHandler.this вместо nick, чтобы отправить предупреждение отправителю,
                                    //что нельзя отправлять самому себе
                                    //TODO hw7Update.Можно по другому, если проверку реализовать здесь перед sendMsgToNick
                                    server.sendMsgToNick(ClientHandler.this, str);
                                    //TODO hw7Update.Можно по другому, если проверку реализовать здесь перед sendMsgToNick
                                    //server.sendPersonalMsg(ClientHandler.this, tokens[1], tokens[2]);
                                }
                                //TODO ERR. Не отправлялись сообщения всем.Удалил
                                /*else{
                                    server.broadCastMsg(nick + ": " + str);
                                }*/
                            }
                            //TODO ERR. Не отправлялись сообщения всем.Удалил
                            else{
                                server.broadCastMsg(nick + ": " + str);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    //Важно. this здесь - это объект анонимного класса Thread и обратиться к нему
                    // можно только через основной класс ClientHandler
                    server.unsubscribe(ClientHandler.this);
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    //TODO HW_task2 и HW_task3.Вариант2.Добавил
    //геттер для nick
    public String getNick() {
        return nick;
    }

    //TODO hw7Update.Лучше этот метод реализовать в MainServer
    //TODO HW_task3.Вариант1.Добавил
    /**
     * Метод проверки не авторизовался ли кто-то уже под этим ником(есть ли в списке клиент с таким ником)
     * @param nick - проверяемый ник
     * @return true, если такой клиент с таким ником уже авторизован
     */
    /*boolean isThisNickAuthorized(String nick){
        boolean flag = false;

        for (ClientHandler c: server.getClients()) {
            if(c.nick.equals(nick)){
                flag = true;
                break;
            }
        }
        return flag;
    }*/

}
