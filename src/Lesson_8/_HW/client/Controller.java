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
import java.net.Socket;

public class Controller {
    //TODO L8hwTask1.Удалил
    //@FXML
    //TextArea textArea;//TODO проверяем служ.сообщ до авторизации.ERR Не приходят от других сообщения.Удалил
    //TODO L8hwTask1.Добавил
    //@FXML
    //ScrollPane scrollPane; //TODO ERR.Панели сообщений все с одной стороны
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

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    private boolean isAuthorized;

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

            //TODO L8hwTask1.Добавил
            //scrollPane.setVisible(false);
            //scrollPane.setManaged(false);

            //TODO L8hwTask1.Добавил.ERR Не приходят от других сообщения.Удалил
            //textArea.setVisible(true);
            //textArea.setManaged(true);

        } else {
            upperPanel.setVisible(false);//делаем окно видимым (по умолчанию в sample visible="false")
            upperPanel.setManaged(false);//выделяется место под HBox, если окно видимо (по умолчанию в sample managed="false")
            bottomPanel.setVisible(true);
            bottomPanel.setManaged(true);
            clientList.setVisible(true);
            clientList.setManaged(true);

            //TODO L8hwTask1.Добавил
            //scrollPane.setVisible(true);
            //scrollPane.setManaged(true);

            //TODO L8hwTask1.Добавил.ERR Не приходят от других сообщения.Удалил
            //textArea.setVisible(false);
            //textArea.setManaged(false);

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

                            //обрабатываем запрос от сервера на добавление клиента в список
                            if (str.startsWith("/clientlist")) {
                                String[] tokens = str.split(" ");
                                //всегда, когда меняем графическую часть интерфейса, нужно
                                //всю работу осуществлять в отдельном потоке для работы с интеграцией с GUI.
                                // Это особенность JavaFX
                                //TODO возможно это уже реализовано в TreeView? Но лучше вставлять всегда
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
    //Метод обработки двойного клика на пользователе в списке
    public void selectClient(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            //TODO Временно
            System.out.println("Двойной клик");

            //TODO L8hwTask5.Добавил
            //добавить действия
            /*Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    //MiniStage ms = new MiniStage(clientList.getSelectionModel().getSelectedItem(), out, tex
                    //ms.show();

                }
            });*/
        }
    }

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
    //метод для отправки сообщений
    public void sendMsg(ActionEvent actionEvent) {
        try {
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

                //TODO переписал по своему ниже до catch
            }
            //добавляем метку в бокс
            vBox.getChildren().add(label);
            //добавляем vBox в общий бокс чата
            vBoxChat.getChildren().add(vBox);

            //msg_label.requestFocus();//TODO проверка показа нижнего сообщения.Не работает

            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

}
