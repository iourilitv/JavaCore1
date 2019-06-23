package Lesson_6;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 6. Работа с сетью.
 * @author Artem Evdokimov.
 * Написание эхо-сервера.
 * До разделения на серверную и клиентскую часть.
 * Клиент подключается по telnet через приложение puTTV
 */
public class MainBefore {

    public static void main(String[] args) {
        ServerSocket server = null;
        Socket socket = null;

        try {
            //создаем сокет сервера
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен");

            //создаем сокет клиента на стороне сервера и передаем его клиенту
            socket = server.accept();
            System.out.println("Клиент подключился");

            //дабавим исходящий от сервера поток через перегруженный конструктор PrintWriter
            //autoFlush=true, чтобы обойти особенность PrintWriter, не отправлять сообщение
            // пока оно полностью не собрано. Иначе пришлось бы в конце цикла добавить out.flush(); - обрезать
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            //Читаем данные из сети с помощью сканнера
            //НО у Scanner есть проблемы с кодировкой
            //пока мы читаем входящий поток от клиента(односторонний обмен)
            Scanner in = new Scanner(socket.getInputStream());

            //слушаем входящий поток в бесконечном цикле, т.к.не известно сколько
            // длина потока сообщений
            while (true){
                //читаем данные из сканнера
                String str = in.nextLine();

                //условие выхода из сеанса
                if (str.equals("/end")) {
                    break;
                }

                //выводим сообщения от клиента в консоль сервера
                System.out.println("Client " + str);

                //отправляем сообщение от сервера в виде эха от входного(от клиента)
                //теперь наш обмен стал двусторонним
                out.println("echo: " + str);
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
}
