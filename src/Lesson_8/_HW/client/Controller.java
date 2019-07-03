package Lesson_8._HW.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Controller {
    @FXML
    VBox vBoxChat;

    @FXML
    TextField textField;

    @FXML
    Button btn1;

    @FXML
    HBox bottomPanel;

    @FXML
    HBox upperPanel;

    @FXML
    TextField loginFiled;

    @FXML
    PasswordField passwordField;

    //ListView - динамическое представление для работы с GUI framework JavaFX
    @FXML
    ListView<String> clientList;

    //для privateChat.fxml
    @FXML
    VBox prVBoxChat;

    @FXML
    TextField prTextField;

    @FXML
    Button prBtnSend;

    @FXML
    HBox prBottomPanel;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    private boolean isAuthorized;

    //TODO L8hwTask5.initChatPreviously.Добавил
    private String chatCompanionNick = "";

    final String IP_ADRESS = "localhost";
    final int PORT = 8189;

    //метод для показа нижней панели или верхней
    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;

        if (!isAuthorized) {
            upperPanel.setVisible(true);
            upperPanel.setManaged(true);
            bottomPanel.setVisible(false);
            bottomPanel.setManaged(false);
            clientList.setVisible(false);
            clientList.setManaged(false);
        } else {
            upperPanel.setVisible(false);//делаем окно видимым (по умолчанию в sample visible="false")
            upperPanel.setManaged(false);//выделяется место под HBox, если окно видимо (по умолчанию в sample managed="false")
            bottomPanel.setVisible(true);
            bottomPanel.setManaged(true);
            clientList.setVisible(true);
            clientList.setManaged(true);
        }
    }

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
                            if (str.startsWith("/authok")) {
                                setAuthorized(true);//устанавливаем признак успешной авторизации
                                break;
                            } else {
                                //TODO вывод служ.сообщ. до авторизации клиента.Оставить
                                //TODO.ERR Не приходят от других сообщения.Удалил
                                //textArea.appendText(str + "\n");
                            }
                        }

                        // блок для разбора сообщений
                        while (true) {
                            String str = in.readUTF();
                            if (str.equals("/serverclosed")) {
                                break;
                            }

                            //TODO L8hwTask5.Добавил chat_communication
                            if (str.startsWith("/inv")) {//всегда в строке ник партнера
                                //передаем первое сообщение на проверку в метод инициализации прив.чата
                                initPrivateChat(str);
                                //запускаем цикл инициализации прив.чата
                                while (true){
                                    String strInv = in.readUTF();
                                    if(initPrivateChat(strInv)){
                                        //если инициализация прошла успешно начинаем прив.чат
                                        startPrivateChat();
                                    }
                                    //после выхода их прив.чата освобождаем прив.чат
                                    chatCompanionNick = "";
                                    break;
                                }
                            }

                            //обрабатываем запрос от сервера на добавление клиента в список
                            if (str.startsWith("/clientlist")) {
                                String[] tokens = str.split(" ");
                                //всегда, когда меняем графическую часть интерфейса, нужно
                                //всю работу осуществлять в отдельном потоке для работы с интеграцией с GUI.
                                // Это особенность JavaFX, возможно это уже реализовано в TreeView? Но лучше вставлять всегда
                                Platform.runLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        //обновляем список пользователей
                                        clientList.getItems().clear();
                                        for (int i = 1; i < tokens.length; i++) {
                                            clientList.getItems().add(tokens[i]);
                                        }
                                    }
                                });
                            } else {
                                //TODO L8hwTask5.Изменил
                                //отсеиваем служебные сообщения, чтобы не показывать в окне чата
                                if (!str.startsWith("/")) {
                                    //выводим сообщение в свое окно чата
                                    //TODO L8hwTask1.Удалил
                                    //textArea.appendText(str + "\n");
                                    //TODO L8hwTask1.Добавил
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            Label label = new Label(str + "\n");
                                            VBox vBox = new VBox();
                                            vBox.setAlignment(Pos.TOP_LEFT);
                                            //добавляем метку в бокс
                                            vBox.getChildren().add(label);
                                            //добавляем vBox в общий бокс чата
                                            vBoxChat.getChildren().add(vBox);
                                        }
                                    });
                                }
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
                        setAuthorized(false);//устанавливаем признак отмены авторизации
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //метод для авторизации
    public void tryToAuth() {
        if (socket == null || socket.isClosed()) {
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

    //TODO L8hwTask5.Добавил
    //метод для приглашения в приватный чат выбранного в списке ник
    public void tryToInviteIntoPrivateChat(MouseEvent mouseEvent) throws IOException {
        //проверяем сколько было кликов мышью
        if (mouseEvent.getClickCount() == 2) {

            //TODO Временно
            System.out.println("Двойной клик");

            //проверяем не начат ли приватный чат
            //другой не сможет пригласить, если пользователь уже чатится с кем-то приватно
            if (chatCompanionNick.equals("")) {

                //TODO L8hwTask5.ERR.Пустое имя партнера по чату.Добавил
                //резервируем прив.чат для партнера
                chatCompanionNick = clientList.getSelectionModel().getSelectedItem();

                //отправляем партнеру приглашение початиться
                try {
                    // отправляем сообщение приглашение партнеру начать приватный чат
                    out.writeUTF("/invto " + clientList.getSelectionModel().getSelectedItem());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else{
                System.out.println("Ваш приватный чат занят!");
            }
        }
    }

    //TODO L8hwTask5.Добавил
    public boolean initPrivateChat(String str) {
        try {

            //TODO Временно.
            System.out.println("0.Controller.initPrivateChat. str:" + str);

            //выделяем ник партнера по чату из служебного сообщения
            String[] temp = str.split(" ", 2);

            //если приглашение початиться пришло от партнера
            if (str.startsWith("/invto")) {

                //TODO Временно.OK
                System.out.println(".1. /invto str:" + str);

                //проверяем свободен ли мой приватный чат
                if (chatCompanionNick.equals("")) {
                    //если мой чат тоже свободен, резервируем его для ника партнера
                    chatCompanionNick = temp[1];

                    //TODO Временно.OK
                    System.out.println(".2. before out:/invok + chatCompanionNick:" + chatCompanionNick);

                    //инициализируем приватный чат
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //открываем отдельное окно для приватного чата
                                new PrivateChatStage(chatCompanionNick);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    //отправляем на мой сервер подтверждение
                    out.writeUTF("/invok " + chatCompanionNick);
                    return true;

                } else {

                    //TODO Временно.OK
                    System.out.println(".3. before out:/invnot + chatCompanionNick:" + chatCompanionNick);

                    //отправляем на мой сервер отказ чатиться
                    out.writeUTF("/invnot " + chatCompanionNick);

                    return false;
                }
            }
            //если от партнера пришел отказ, его чат занят
            if (str.startsWith("/invnot")) {

                //TODO Временно.OK
                System.out.println(".1. /invnot str:" + str);

                //TODO Временно.//TODO. Вывести сообщение в GUI.
                System.out.println("Адресат занят другим приватным чатом!");

                return false;
            }
            //если от партнера пришло подтверждение, что все готово начать чат
            if (str.startsWith("/invok")) {

                //TODO Временно.OK
                System.out.println(".2.1. /invok str:" + str);
                //TODO Временно.OK
                System.out.println(".2.2. /invok chatCompanionNick:" + chatCompanionNick);

                try {
                    //если приватный чат инициализирован, открыть новое окно
                    //и подтверждаем готовность инициализации приватного чата
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //открываем отдельное окно для приватного чата
                                new PrivateChatStage(chatCompanionNick);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    return true;

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //TODO L8hwTask5.Добавил
    public void startPrivateChat() throws IOException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    //TODO Временно.
                    System.out.println("Controller.startPrivateChat chatCompanionNick:" + chatCompanionNick);

                    // блок для разбора сообщений
                    while (true) {
                        String str = in.readUTF();

                        //TODO временно.
                        System.out.println("1.startPrivateChat. str:" + str);

                        //закрыть чат
                        if (str.equals("/chatclosed")) {
                            break;
                        }
                        //отображаем поступающие сообщения в окне своего приватного чата
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Label label = new Label(str + "\n");
                                VBox vBox = new VBox();
                                vBox.setAlignment(Pos.TOP_LEFT);
                                //добавляем метку в бокс
                                vBox.getChildren().add(label);
                                //добавляем vBox в общий бокс чата
                                prVBoxChat.getChildren().add(vBox);
                            }
                        });
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
    }

    //TODO Удалить!
            /*Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    //MiniStage ms = new MiniStage(clientList.getSelectionModel().getSelectedItem(), out, tex
                    //ms.show();

                }
            });*/

    //Метод отправки запроса об отключении на сервер
    public void dispose() {
        System.out.println("Отправляем сообщение о закрытии");
        try {
            //проверяем подключен ли клиент
            if (out != null && !socket.isClosed()) {
                out.writeUTF("/end");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO L8hwTask1.Удалил
    /*//метод для отправки сообщений
    public void sendMsg() {
        try {
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
    //TODO L8hwTask1.Добавил
    //метод для отправки сообщений в общем чате //TODO L8hwTask5.Добавил - в общем чате
    public void sendMsg(ActionEvent actionEvent) {
        try {
            //не показываем служебные сообщения у себя
            if(!textField.getText().startsWith("/")) {
                Label label = new Label(textField.getText());
                VBox vBox = new VBox();

                //условие (от кого?) для определения расположения
                //вытаскиваем из события источник (кнопка или текстовое поле)
                //и сравниваем с теми же источниками контроллера отправителя
                if (actionEvent.getSource().equals(this.btn1) ||
                        actionEvent.getSource().equals(this.textField)
                    ) {//if (если мое - сообщение справа)
                    vBox.setAlignment(Pos.TOP_RIGHT);
                } else {
                    vBox.setAlignment(Pos.TOP_LEFT);
                }
                //добавляем метку в бокс
                vBox.getChildren().add(label);
                //добавляем vBox в общий бокс чата
                vBoxChat.getChildren().add(vBox);

            }
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();

        } catch(IOException e){
            e.printStackTrace();
        }
    }

    //TODO L8hwTask5.Добавил
    //метод для отправки сообщений в приватном чате
    public void sendMsgInPrivateChat (ActionEvent actionEvent) {
        try {
            //не показываем служебные сообщения у себя
            if (!prTextField.getText().startsWith("/")) {//TODO Удалить?
                Label label = new Label(prTextField.getText());
                VBox vBox = new VBox();

                //условие (от кого?) для определения расположения
                //вытаскиваем из события источник (кнопка или текстовое поле)
                //и сравниваем с теми же источниками контроллера отправителя
                if (actionEvent.getSource().equals(prBtnSend) ||
                        actionEvent.getSource().equals(prTextField)//TODO Удалил this из prBtnSend и prTextField
                ) {//if (если мое - сообщение справа)
                    vBox.setAlignment(Pos.TOP_RIGHT);
                } else {
                    vBox.setAlignment(Pos.TOP_LEFT);
                }
                //добавляем метку в бокс
                vBox.getChildren().add(label);
                //добавляем vBox в общий бокс чата
                prVBoxChat.getChildren().add(vBox);
            }

            //TODO временно.
            System.out.println("Controller.sendMsgInPrivateChat.");
            System.out.println(".actionEvent.getTarget().toString(): " + actionEvent.getTarget().toString());
            System.out.println(".prTextField.getText(): " + prTextField.getText());

            //TODO L8hwTask5.Добавил
            out.writeUTF("/w " + chatCompanionNick + prTextField.getText());
            prTextField.clear();
            prTextField.requestFocus();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
