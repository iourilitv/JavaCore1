package Lesson_6.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class Main {
    //TODO добавляем синхронизированный списочный массив клиентов
    private Vector<ClientHandler> clients;

    //TODO точку входа переносим в класс StartServer, чтобы обращаться к Main как к объекту.Удалил
    //public static void main(String[] args){
    //TODO точку входа переносим в класс StartServer, чтобы обращаться к Main как к объекту.Добавил
    // иначе его(this) не передать в объект ClientHandler
    public Main() {
        //TODO добавляем синхронизированный списочный массив клиентов
        clients = new Vector<>();

        ServerSocket server = null;
        Socket socket = null;

        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен");

            //TODO переносим точку подключения в цикл while
            //socket = server.accept();
            //System.out.println("Клиент подключился");

            //TODO переносим в конструктор класса ClientHandler
            //DataInputStream in = new DataInputStream(socket.getInputStream());
            //DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            while (true) {
                //TODO переносим в конструктор класса ClientHandler
                //String str = in.nextLine();
                //TODO удаляем за ненадобностью
                /*if (str.equals("/end")) {
                    break;
                }
                System.out.println("Client " + str);
                out.writeUTF(str);//было out.println("echo: " + str);*/

                //TODO переносим точку подключения из тела try
                socket = server.accept();
                System.out.println("Клиент подключился");

                //TODO добавляем синхронизированный списочный массив клиентов.Удалил
                //TODO добавляем
                //создаем объект слушателя клиента
                //new ClientHandler(socket, this);
                //TODO добавляем синхронизированный списочный массив клиентов.Добавил
                //теперь при каждом подключении нового клиента создается элемент списка,
                //в котором запускается поток двустороннего обмена данными с сервером
                clients.add(new ClientHandler(socket, this));
                //TODO HW добавить удаление клиента из списка при его закрытии
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
        }
    }
    //TODO добавляем синхронизированный списочный массив клиентов
    /**
     * Метод отправки сообщений всем
     * Перебираем список клиентов и отправляем ему сообщение
     * @param msg
     */
    public void broadCastMsg(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }
}
