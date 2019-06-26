package Lesson_7.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

//TODO 1. DB connect.Удалил
//public class Controller implements Initializable {//Initializable - для автоматического подключения клиента
//TODO 1. DB connect.Удалил
public class Controller {//теперь не нужна автоматическое подключение
    @FXML
    TextArea textArea;

    @FXML
    TextField textField;

    @FXML
    Button btn1;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    //TODO 1. DB connect.Добавил
    @FXML
    HBox bottomPanel;

    //TODO 1. DB connect.Добавил
    @FXML
    HBox upperPanel;

    //TODO 1. DB connect.Добавил
    @FXML
    TextField loginFiled;

    //TODO 1. DB connect.Добавил
    @FXML
    PasswordField passwordField;

    //TODO 1. DB connect.Добавил
    private boolean isAuthorized;

    final String IP_ADRESS = "localhost";
    final int PORT = 8189;

    //TODO 1. DB connect.Добавил
    // метод для показа нижней панели или верхней
    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;

        if(!isAuthorized) {
            upperPanel.setVisible(true);
            upperPanel.setManaged(true);
            bottomPanel.setVisible(false);
            bottomPanel.setManaged(false);
        } else {
            upperPanel.setVisible(false);//делаем окно видимым (по умолчанию в sample visible="false")
            upperPanel.setManaged(false);//выделяется место под HBox, если окно видимо (по умолчанию в sample managed="false")
            bottomPanel.setVisible(true);
            bottomPanel.setManaged(true);
        }
    }

    //TODO 1. DB connect.Удалил.
    /*@Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            socket = new Socket(IP_ADRESS, PORT);

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            //TODO UPD HW.Есть у препода
                            ///TODO Исправление err1.java.io.EOFException.Удалил.Работает
                            //String str = in.readUTF();

                            //TODO UPD HW.Добавил чтобы клиенты не висели после закрытия сервера
                            //контроллер клиента принимает сообщение от ClientHandler сервера
                            //if(str.equals("/serverclosed")) break;

                            //textArea.appendText(str + "\n");/

                            //TODO UPD HW.Нет у препода. Не нужно было бороться с исплючением в клиенте
                            //TODO Исправление err1.java.io.EOFException.Добавил.Работает
                            try{
                                String str = in.readUTF();
                                textArea.appendText(str + "\n");
                            } catch (EOFException e) {
                                //TODO временно
                                System.out.println("Приложение закрыто по EOFException");

                                //TODO закрыть приложение(окно) после остановки.Добавил.Работает
                                Platform.exit();

                                //TODO удалять нельзя, иначе бесконечный цикл
                                break;
                            }
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
            }).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    //TODO 1. DB connect.Добавил
    /**
     * Метод подсоединения аналогичный серверному
     */
    public void connect() {
        try {
            socket = new Socket(IP_ADRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        // блок для авторизации
                        while (true) {
                            String str = in.readUTF();
                            if(str.startsWith("/authok")) {
                                setAuthorized(true);//устанавливаем признак успешной авторизации
                                break;
                            } else {
                                textArea.appendText(str + "\n");
                            }
                        }

                        // блок для разбора сообщений
                        while (true) {
                            String str = in.readUTF();
                            if(str.equals("/serverClosed")) {
                                break;
                            }
                            textArea.appendText(str + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        setAuthorized(false);//устанавливаем признак отмены авторизации
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO 1. DB connect.Добавил
    //метод для авторизации
    public void tryToAuth() {
        if(socket == null || socket.isClosed()) {
            // сначала подключаемся к серверу, если не подключен(сокет не создан или закрыт)
            connect();
        }
        try {
            // отправка сообщений на сервер для авторизации
            out.writeUTF("/auth " + loginFiled.getText() + " " + passwordField.getText());
            loginFiled.clear();//очищаем поле логина
            passwordField.clear();//очищаем поле пароля
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //метод для отправки сообщений
    public void sendMsg() {
        try {
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
