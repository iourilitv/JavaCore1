package Lesson_8.server;

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
                                    out.writeUTF("/serverclosed");//TODO выдает исключение в клиенте
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

    //геттер для nick
    public String getNick() {
        return nick;
    }

}
