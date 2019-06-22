package Lesson_6;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 6. Работа с сетью.
 * @author Artem Evdokimov.
 * Написание эхо-сервера.
 * Серверная часть.
 */
public class EchoServer {
    public static void main(String[] args) {
        Socket socket = null ;

        //Для начала создается объект класса ServerSocket, представляющий собой сервер, который
        //прослушивает порт 8189.
        try (ServerSocket serverSocket = new ServerSocket( 8189 )) {
            System.out.println( "Сервер запущен, ожидаем подключения..." );

            //Метод server.accept() переводит основной поток в режим ожидания,
            //поэтому, пока никто не подключится, следующая строка кода выполнена не будет. Как только клиент
            //подключился, информация о соединении с ним запишется в объект типа Socket.
            socket = serverSocket.accept();
            System.out.println( "Клиент подключился" );

            //создаем обработчики входящего и исходящего потока,
            // объекты для доступа к потокам ввода-вывода, связанным с классом Socket
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            //запускаем бесконечный обмен данными между клиентов и сервером
            while ( true ) {
                //принимаем на сервер данные от клиента
                String str = in.readUTF();
                //добавляем условие выхода сервера из сеанса
                if (str.equals( "/end" )) {
                    break ;
                }
                //возвращаем клиенту эхо от сервера
                out.writeUTF( "Эхо: " + str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}