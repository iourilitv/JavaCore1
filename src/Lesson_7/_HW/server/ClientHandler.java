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
    //TODO 1. DB connect.Добавил
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
                        //TODO 1. DB connect.Добавил
                        // цикл для авторизации. Крутится бескоечно, пока не авторизуется
                        while (true) {
                            String str = in.readUTF();
                            // если сообщение начинается с /auth
                            if(str.startsWith("/auth")) {
                                String[] tokens = str.split(" ");
                                // Вытаскиваем данные из БД //здесь: tokens[1] - логин, tokens[2] - пароль
                                String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                                if (newNick != null) {
                                    // отправляем сообщение об успешной авторизации
                                    sendMsg("/authok");
                                    nick = newNick;
                                    //подписываем клиента при успешной авторизации и выходим из цикла
                                    server.subscribe(ClientHandler.this);
                                    break;
                                } else {
                                    sendMsg("Неверный логин/пароль!");
                                }
                            }
                        }

                        // блок для отправки сообщений
                        while (true) {
                            String str = in.readUTF();
                            if (str.equals("/end")) {
                                //закрываем клиента после удаления его из списка
                                out.writeUTF("/serverclosed");
                                break;
                            }
                            //TODO 1. DB connect.Удалил
                            //server.broadCastMsg(str);
                            //TODO 1. DB connect.Добавил
                            server.broadCastMsg(nick + ": " + str);//"Client: "
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
                    //TODO Важно. this здесь - это объект анонимного класса Thread и обратиться к нему
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

}
