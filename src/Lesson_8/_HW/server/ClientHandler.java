package Lesson_8._HW.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private MainServer server;
    private String nick;
    List<String> blackList;

    public ClientHandler(Socket socket, MainServer server) {
        try {
            this.blackList = new ArrayList<>();
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

                                //чтобы избежать ошибки при пустом вводе в поля login или пароль
                                int splitLimit = 3;
                                String[] tokens = str.split(" ", splitLimit);

                                // Вытаскиваем данные из БД //здесь: tokens[1] - логин, tokens[2] - пароль
                                String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                                if (newNick != null ) {

                                    //проверяем не авторизовался ли кто-то уже под этим ником
                                    if(!server.isThisNickAuthorized(newNick)){
                                        //отправляем сообщение об успешной авторизации
                                        sendMsg("/authok");
                                        nick = newNick;
                                        //подписываем клиента при успешной авторизации и выходим из цикла
                                        server.subscribe(ClientHandler.this);
                                        //выводим сообщение в консоль сервера об успешном подключении клиента
                                        System.out.println("Клиент с ником " + nick + " подключился.");
                                        break;
                                    }
                                    else{
                                        sendMsg("Пользователь " + newNick + " уже авторизован!");
                                    }
                                } else {
                                    sendMsg("Неверный логин/пароль!");
                                }
                            }
                        }

                        // блок для отправки сообщений
                        while (true) {
                            String str = in.readUTF();
                            //отлавливаем все служебные сообщения
                            if (str.startsWith("/")){
                                //запрос на отключение
                                if (str.equals("/end")) {
                                    //закрываем клиента после удаления его из списка
                                    out.writeUTF("/serverclosed");
                                    break;
                                }

                                //TODO L8hwTask5.initChatPreviously.Добавил.ERR.Добавил
                                //сервер принимает от клиента служебное сообщение и переправляет его на сервер партнеру
                                if (str.startsWith("/inv")) {

                                    //TODO Временно.OK
                                    System.out.println("1.ClientHandler. nick:" + ClientHandler.this.getNick() + " received. str" + str);

                                    //выделяем ник партнера по чату из служебного сообщения
                                    String[] temp = str.split(" ", 2);
                                    //кому отправлять
                                    String chatCompanionNick = temp[1];
                                    //TODO проверяем не отправляем ли самому себе
                                    if(!ClientHandler.this.getNick().equals(temp[1])){
                                        //переписываем изначальное сообщение от клиента - заменяем на отправителя

                                        //TODO L8hwTask5.initChatPreviously.Добавил.
                                        String msg = temp[0] + " " + ClientHandler.this.getNick();

                                        //TODO Временно.OK
                                        System.out.println("2.ClientHandler. nick:" + ClientHandler.this.getNick() + " sent. msg" + msg);

                                        //сервер отправляет измененное сообщение на сервер партнеру
                                        server.sendMsgToNick(ClientHandler.this, chatCompanionNick, msg);
                                    }
                                }

                                //TODO L8hwTask5.initChatPreviously.Добавил.ERR.Удалил
                                /*if (str.startsWith("/inv")){

                                    //TODO Временно.OK
                                    System.out.println("ClientHandler. inside startsWith(_/inv_). str" + str);

                                    //выделяем ник партнера по чату из служебного сообщения
                                    String[] temp = str.split(" ", 2);
                                    //кому отправлять
                                    String chatCompanionNick = temp[1];

                                    //TODO лишнее?
                                    //проверяем от кого пришло сообщение (invto - свое приглашение)
                                    //пришло от моего клиента на сервер - на сервер партнеру
                                    if(temp[0].equals("/invto")){//приглашение с ником партнера

                                        //TODO Временно.OK
                                        System.out.println(".1. inside if(temp[0].equals(\"/invto\") str:" + str);

                                        //если не от приглашающего
                                        //отправляем приглашение партнеру початиться приватно
                                        String msg = "/invfrom " + ClientHandler.this.getNick();//мой ник

                                        //TODO Временно.OK
                                        System.out.println(".2.inside if(temp[0].equals(\\\"/invto\\\") msg to out:" + msg);

                                        //сервер принимает от меня приглашение и переправляет его на сервер партнеру
                                        server.sendMsgToNick(ClientHandler.this, chatCompanionNick, msg);
                                    }
                                    //пришло от моего сервера на сервер партнера - клиенту партнера
                                    if(temp[0].equals("/invfrom")){//приглашение с моим ником
                                        //если не от приглашающего
                                        //партнер присылает подтверждение, что готов початиться приватно
                                        String msg = "/invfrom " + chatCompanionNick;//мой ник
                                        //сервер принимает от партнера подтверждение початиться и переправляет его мне
                                        //sendMsg(msg);
                                        server.sendMsgToNick(ClientHandler.this, chatCompanionNick, msg);
                                    }
                                    //пришло от клиента партнера на сервер партнера - на мой сервер
                                    if(temp[0].equals("/invfromok")){//приглашение с моим ником

                                        //TODO Временно.OK
                                        System.out.println(".3. inside if(temp[0].equals(\"/invfromok\") str:" + str);

                                        //если не от приглашающего
                                        //партнер присылает подтверждение, что готов початиться приватно
                                        String msg = "/invfromok " + ClientHandler.this.getNick();//его ник

                                        //TODO Временно.OK
                                        System.out.println(".4.inside if(temp[0].equals(\\\"/invfromok\\\") msg to out:" + msg);

                                        //сервер принимает от партнера подтверждение початиться и переправляет его мне
                                        //sendMsg(msg);
                                        server.sendMsgToNick(ClientHandler.this, chatCompanionNick, msg);
                                    }
                                    //пришло от сервера партнера на мой сервер - моему клиенту
                                    if (temp[0].equals("/invfromok")){//подтверждение с ником партнера

                                        //TODO Временно.OK
                                        //System.out.println("ClientHandler.startsWith_/inv_.str:" + str);

                                        //отправляем служебное сообщение для открытия окна приватной переписки
                                        String msg = "/invok " + chatCompanionNick;//его ник

                                        //TODO Временно.OK
                                        System.out.println("ClientHandler.startsWith_/inv_.temp[0].equals(invfromok).(/invok + chatCompanionNick):" + msg);

                                        sendMsg(msg);
                                        //server.sendMsgToNick(ClientHandler.this, chatCompanionNick, msg);
                                    }
                                }

                                //TODO L8hwTask5.initChatPreviously.Добавил.Удалил
                                if (str.startsWith("/invok")){

                                    //TODO Временно.OK
                                    //System.out.println("ClientHandler.startsWith_/inv_.str:" + str);

                                    //выделяем ник партнера по чату из служебного сообщения
                                    //String[] temp = str.split(" ", 2);
                                    //String chatCompanionNick = temp[1];

                                    //отправляем служебное сообщение для открытия окна приватной переписки
                                    String msg = "/invok " + chatCompanionNick;

                                    //TODO Временно.OK
                                    System.out.println("ClientHandler.startsWith_/inv_.str(/invok + chatCompanionNick):" + str);

                                    server.sendMsgToNick(ClientHandler.this, chatCompanionNick, msg);
                                }*/

                                //оправка персонального сообщения
                                if(str.startsWith("/w")) {
                                    //ClientHandler.this вместо nick, чтобы отправить предупреждение отправителю,
                                    //что нельзя отправлять самому себе

                                    //TODO L8hwTask4.Удалил
                                    //TODO hw7Update.Можно по другому, если проверку реализовать здесь перед sendMsgToNick
                                    //server.sendMsgToNick(ClientHandler.this, str);
                                    //TODO L8hwTask4.Добавил
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
                                        if(!ClientHandler.this.getNick().equals(nickOfRecipient)){
                                            //проверяем не находится ли получатель черном списке отправителя
                                            if(!ClientHandler.this.checkBlackList(nickOfRecipient)){
                                                //отправляем сообщение адресату
                                                server.sendMsgToNick(ClientHandler.this, nickOfRecipient, msg);
                                            } else{
                                                //если получатель находится в черном списке адресата (цикл не прервался по return)
                                                //отправляем сообщение отправителю(себе)
                                                ClientHandler.this.sendMsg("Адресат с ником " + nickOfRecipient + " в вашем черном списке!");
                                            }
                                        } else{
                                            //отправка предупреждения отправителю
                                            ClientHandler.this.sendMsg("Нельзя отправлять самому себе!");
                                        }
                                    } else{
                                        //отправка предупреждения отправителю
                                        ClientHandler.this.sendMsg("Неверный синтаксис сервисного сообщения!");
                                    }
                                }
                                //добавляем пользователя в черный список
                                if (str.startsWith("/blacklist ")) {
                                    String[] tokens = str.split(" ");
                                    blackList.add(tokens[1]);
                                    sendMsg("Вы добавили пользователя " + tokens[1] + " в черный список");
                                }
                            }//if "/"
                            else{
                                server.broadcastMsg(ClientHandler.this,nick + ": " + str);
                            }
                        }//while
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

    //геттер для nick
    public String getNick() {
        return nick;
    }

    //Метод проверки есть ли пользователь в черном списке
    public boolean checkBlackList(String nick) {
        return blackList.contains(nick);
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
