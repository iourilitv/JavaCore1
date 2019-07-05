package Lesson_8._HW.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
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
                        // цикл для авторизации и регистрации. Крутится бесконечно, пока не авторизуется
                        while (true) {
                            String str = in.readUTF();

                            //TODO Временно
                            System.out.println("while (true). str: " + str);

                            //TODO L8hwTask2.Registration logic.ERR.ServerSocketException при закрытии окна на этапе регистрации
                            if (str.equals("/end")) {
                                //закрываем клиента после удаления его из списка
                                out.writeUTF("/serverclosed");
                                break;
                            }

                            //TODO L8hwTask2.AddUserToDB.Удалил
                            /*//TODO L8hwTask2.Registration logic.Добавил
                            //если получено сообщение связанное с регистрацией
                            if(str.startsWith("/reg")) {
                                int splitLimit = 4;
                                String[] tokens = str.split(" ", splitLimit);
                                //Вытаскиваем данные из БД //здесь: tokens[2] - логин, tokens[3] - пароль
                                String newLogin = AuthService.checkLoginInDB(tokens[2]);
                                //делаем запрос в БП, есть ли такой логин и пароль(ник не уникальный)
                                if (newLogin == null) {//нет такого в базе
                                    //отправляем сообщение c логином и паролем для прохождения авторизации без повторного ввода
                                    sendMsg("/regok " + tokens[2] + tokens[3]);
                                    nick = newLogin;//TODO Заменить nick на login, т.к. nick не уникален?
                                    //выводим сообщение в консоль сервера об успешном подключении клиента
                                    System.out.println("Пользователь с логином " + nick + " зарегистрирован.");
                                    break;
                                } else {
                                    //нет, если этот логин уже занят
                                    sendMsg("Пользователь с логином " + nick + " уже зарегистрирован!\n Введите другой логин.");
                                }
                            }*/
                            //TODO L8hwTask2.AddUserToDB.Добавил
                            //TODO L8hwTask2.Registration logic.Добавил
                            //если получено сообщение связанное с регистрацией
                            if(str.startsWith("/reg")) {
                                int splitLimit = 4;
                                //String[] tokens = str.split(" ", splitLimit);//TODO ERR.Пустые поля
                                String[] tokens = str.split(" ");

                                //TODO Временно
                                System.out.println("if(str.startsWith(/reg/)). str: " + str);

                                //проверяем есть ли в форме пустые поля
                                if(tokens.length >= splitLimit) {

                                    String login = tokens[2];
                                    String password = tokens[3];
                                    String nickname = tokens[1];

                                    //делаем запрос в БП, есть ли такой логин или ник
                                    if (AuthService.checkLoginAndNicknameInDB(tokens)) {//нет такого в базе

                                        //записываем в БД данные из формы
                                        if (AuthService.addUserIntoDB(tokens)) {

                                            //отправляем сообщение c логином и паролем для прохождения авторизации без повторного ввода
                                            sendMsg("/regok " + login + " " + password);
                                            //выводим сообщение пользователю об успешной регистрации
                                            sendMsg("Вы зарегистрированы. Нажмите Авторизоваться, чтобы войти.");

                                            //выводим сообщение в консоль сервера об успешной регистрации клиента
                                            System.out.println("Пользователь " + nickname + " успешно зарегистрирован.");

                                            //TODO Удалить.
                                            //break;
                                        } else {
                                            //нет, если этот логин уже занят
                                            sendMsg("Неудачная попытка регистрации!\nПопробуйте еще раз.");
                                        }
                                    } else {
                                        //нет, если этот логин уже занят
                                        sendMsg("Пользователь с таким логином или именем уже зарегистрирован!\n Введите другие логин и имя.");
                                    }
                                } else {
                                    //есть пустые поля
                                    sendMsg("Нельзя отправлять форму с пустыми полями!");
                                }
                            }

                            //TODO L8hwTask2.AddUserToDB.Удалил
                            /*// если сообщение начинается с /auth
                            if(str.startsWith("/auth")) {

                                //чтобы избежать ошибки при пустом вводе в поля login или пароль
                                int splitLimit = 3;
                                String[] tokens = str.split(" ", splitLimit);

                                // Вытаскиваем данные из БД //здесь: tokens[1] - логин, tokens[2] - пароль
                                String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);
                                if (newNick != null) {

                                    //проверяем не авторизовался ли кто-то уже под этим ником
                                    if(!server.isThisNickAuthorized(newNick)){
                                        //отправляем сообщение об успешной авторизации
                                        sendMsg("/authok");

                                        //TODO Исправить. Идентифицировать нужно по логину или ID
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

                                    //TODO L8hwTask2.Registration logic.Удалил
                                    //sendMsg("Неверный логин/пароль!");
                                    //TODO L8hwTask2.Registration logic.Добавил
                                    sendMsg("Вы ввели неверный логин/пароль или не зарегистрированы!\nДля регистрации нажмите \"Регистрация\"");
                                }
                            }*/
                            //TODO L8hwTask2.AddUserToDB.Добавил
                            //если сообщение начинается с /auth
                            if(str.startsWith("/auth")) {

                                //TODO Временно
                                System.out.println("if(str.startsWith(\"/auth\" str: " + str);

                                //чтобы избежать ошибки при пустом вводе в поля login или пароль
                                int splitLimit = 3;
                                //String[] tokens = str.split(" ", splitLimit);//TODO ERR.Пустые поля
                                String[] tokens = str.split(" ");

                                //TODO Временно
                                System.out.println("if(str.startsWith(\"/auth\" tokens: " + Arrays.toString(tokens));

                                //проверяем есть ли в форме пустые поля
                                if(tokens.length >= splitLimit) {

                                    String login = tokens[1];
                                    String password = tokens[2];

                                    //TODO Временно
                                    System.out.println("if(tokens.length >= splitLimit. login:" + login + " password:" + password);

                                    // Вытаскиваем данные из БД //здесь: tokens[1] - логин, tokens[2] - пароль
                                    String newNick = AuthService.getNickByLoginAndPass(login, password);
                                    if (newNick != null) {

                                        //проверяем не авторизовался ли кто-то уже под этим ником
                                        if(!server.isThisNickAuthorized(newNick)){
                                            //отправляем сообщение об успешной авторизации
                                            sendMsg("/authok");

                                            //TODO Исправить. Идентифицировать нужно по логину или ID
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

                                        //TODO временно
                                        System.out.println("1. else newNick != null.");

                                        //TODO L8hwTask2.Registration logic.Удалил
                                        //sendMsg("Неверный логин/пароль!");
                                        //TODO L8hwTask2.Registration logic.Добавил
                                        sendMsg("Вы ввели неверный логин/пароль или не зарегистрированы!\nДля регистрации нажмите \"Регистрация\"");
                                    }
                                } else {
                                    //есть пустые поля
                                    sendMsg("Нельзя отправлять форму с пустыми полями!");
                                }
                            }
                        }//while

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

                                //TODO L8hwTask5.Добавил.
                                //сервер принимает от клиента служебное сообщение и переправляет его на сервер партнеру
                                if (str.startsWith("/inv")) {

                                    //TODO Временно.OK
                                    //System.out.println("1.ClientHandler. nick:" + ClientHandler.this.getNick() + " received. str" + str);

                                    //выделяем ник партнера по чату из служебного сообщения
                                    String[] temp = str.split(" ", 2);
                                    //кому отправлять
                                    String chatCompanionNick = temp[1];
                                    //TODO проверяем не отправляем ли самому себе.Лишнее?
                                    if(!ClientHandler.this.getNick().equals(temp[1])){
                                        //переписываем изначальное сообщение от клиента - заменяем на отправителя

                                        String msg = temp[0] + " " + ClientHandler.this.getNick();

                                        //TODO Временно.OK
                                        //System.out.println("2.ClientHandler. nick:" + ClientHandler.this.getNick() + " sent. msg" + msg);

                                        //сервер отправляет измененное сообщение на сервер партнеру
                                        server.sendMsgToNick(ClientHandler.this, chatCompanionNick, msg);
                                    }
                                }

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
