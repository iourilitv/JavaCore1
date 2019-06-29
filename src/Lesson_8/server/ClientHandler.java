package Lesson_8.server;

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

    //TODO hwImproving2.Добавил
    List<String> blackList;

    public ClientHandler(Socket socket, MainServer server) {
        try {
            //TODO hwImproving2.Добавил
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

                                        //TODO ERR. Server always write Client have connected.Добавил
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
                                    out.writeUTF("/serverclosed");//TODO ERR.выдает исключение в клиенте.Не соотвествовало на приеме в Controller.Заработало
                                    break;
                                }

                                //оправка персонального сообщения
                                if(str.startsWith("/w")) {
                                    //ClientHandler.this вместо nick, чтобы отправить предупреждение отправителю,
                                    //что нельзя отправлять самому себе
                                    //TODO hw7Update.Можно по другому, если проверку реализовать здесь перед sendMsgToNick
                                    server.sendMsgToNick(ClientHandler.this, str);
                                    //TODO hw7Update.Можно по другому, если проверку реализовать здесь перед sendMsgToNick
                                    //server.sendPersonalMsg(ClientHandler.this, tokens[1], tokens[2]);
                                }

                                //TODO hwImproving2.Добавил
                                //добавляем пользователя в черный список
                                if (str.startsWith("/blacklist ")) {
                                    String[] tokens = str.split(" ");
                                    blackList.add(tokens[1]);
                                    sendMsg("Вы добавили пользователя " + tokens[1] + " в черный список");
                                }

                                //TODO зачем?
                                /*else{
                                    //server.broadCastMsg(nick + ": " + str);
                                    server.broadcastMsg(ClientHandler.this,nick + ": " + str);
                                }*/
                            }
                            //TODO ERR. Не отправлялись сообщения всем.Удалил
                            else{
                                //TODO hwImproving2.Удалил
                                //server.broadcastMsg(nick + ": " + str);
                                //TODO hwImproving2.Добавил
                                server.broadcastMsg(ClientHandler.this,nick + ": " + str);
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

    //геттер для nick
    public String getNick() {
        return nick;
    }

    //TODO hwImproving2.Добавил
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
