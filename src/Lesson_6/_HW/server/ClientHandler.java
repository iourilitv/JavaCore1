package Lesson_6._HW.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Main server;

    public ClientHandler(Socket socket, Main server) {
        try {
            this.socket = socket;
            this.server = server;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            //TODO исправление ошибки выхода.Удалил
            /*new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();
                            if (str.equals("/end")) {
                                break;
                            }
                            server.broadCastMsg(str);
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
                }
            }).start();*/
            //TODO исправление ошибки выхода.Добавил
            //анонимный класс
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();
                            if (str.equals("/end")) {

                                //TODO UPD HW.Добавил
                                //закрываем клиента после удаления его из списка
                                out.writeUTF("/serverclosed");

                                //TODO UPD HW.Удалил
                                /*//TODO Исправление StartServer/java.net.SocketException: Socket closed.Добавил
                                //TODO работает и здесь и в finally. Правильнее в finally
                                //удаляем клиента их списка
                                //server.closeClient(socket);*/

                                break;
                            }
                            server.broadCastMsg(str);
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

                        //TODO UPD HW.Удалил
                        /*//TODO Исправление StartServer/java.net.SocketException: Socket closed.Добавил
                        //TODO работает и здесь и в if (str.equals("/end"))
                        //удаляем клиента из списка
                        //TODO не нашел как вернуть индекс элемента с этим сокетом
                        // поэтому реализовано, через метод перебирающий список
                        server.closeClient(socket);*/

                    }
                    //TODO UPD HW.Добавил
                    //TODO Важно. если указать просто this - здесь это объект анонимного класса Thread.
                    // А обратится обратиться к объекту основного класса ClientHandler, нужно добавить его к this
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

    //TODO исправление ошибки выхода.Добавил
    public Socket getSocket() {
        return socket;
    }

}
