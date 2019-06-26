package Lesson_6.console;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 6. Работа с сетью.
 * @author Artem Evdokimov.
 * Написание консольного чата сервера и клиента.
 * Клиентская часть.
 */
public class ClientMain {
    public static void main(String[] args) {
        Socket socket = null;

        try {
            socket = new Socket("localhost", 8189);

            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner console = new Scanner(System.in);

            //в этом потоке мы принимаем данные из входящего потока и выводим их на консоль
            //закрываем поток по break как только приходит сообщение /end.
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String str = in.nextLine();
                        if(str.equals("/end")) {
                            out.println("/end");
                            break;
                        }
                        System.out.println("Server: " + str);
                    }
                }
            });
            t1.start();

            //в этом потоке мы читаем в консоли данные(сообщения) от клиента и
            // отправляем на сторону сервера
            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        System.out.println("Введите сообщение");
                        String str = console.nextLine();
                        System.out.println("Сообщение отправлено!");
                        out.println(str);
                    }
                }
            });
            //помечаем его как демон поток, чтобы закрыть его, как только прервется связь,
            // второй участник отключился(закончит работу поток t1, для этого t1.join ниже) и
            // нам теперь нет смысла что-то считывать и отправлять.
            t2.setDaemon(true);
            t2.start();

            try {
                t1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
