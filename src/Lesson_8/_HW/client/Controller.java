package Lesson_8._HW.client;

import Lesson_8._HW.server.ClientHandler;
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
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Controller {
    @FXML
    HBox mainChatPanel;

    @FXML
    VBox vBoxChat;

    @FXML
    HBox bottomPanel;

    @FXML
    TextField textField;

    @FXML
    Button btn1;
    //ListView - динамическое представление для работы с GUI framework JavaFX
    @FXML
    ListView<String> clientList;

    @FXML
    VBox registrationForm;

    @FXML
    VBox regFormTopLabelsBox;

    @FXML
    HBox regFormNicknameBox;

    @FXML
    TextField regFormNickField;

    @FXML
    TextField regFormLoginField;

    @FXML
    PasswordField regFormPasswordField;

    @FXML
    TextArea regFormTextArea;

    @FXML
    HBox regFormRegBtnsBox;

    @FXML
    Button regFormSendToRegisterBtn;

    @FXML
    Button regFormCancelBtn;

    @FXML
    HBox regFormAuthBtnsBox;

    @FXML
    Button regFormAuthBtn;

    @FXML
    Button regFormRegisterBtn;

    //TODO ???переменные с pr в начале - для privateChat.fxml???
    @FXML
    VBox prVBoxChat;//TODO проверить ввод сообщения при выходе.Добавил static.ERR NullPointerException.Удалил

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
    //static - чтобы использовать в потоке FX application
    private static String chatCompanionNick;

    //TODO L8hwTask5.initChatPreviously.Добавил
    private String nick;
    //TODO L8hwTask5.Closing window if partner has left.Добавил.ERR NullPointerException.Добавил static
    //чтобы закрыть окно приватного чата в методе connect(не работает) или sendMsgInPrivateChat
    private static PrivateChatStage prStage;

    //TODO не помогло.Удалить
    //private static boolean onPrivateChat;//для взаимодействия потоков в главного и приватного чата

    final String IP_ADRESS = "localhost";
    final int PORT = 8189;

    //метод отображения элементов GUI в режиме Авторизован/Неавторизован
    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;

        //очищаем поле от сообщений
        regFormTextArea.clear();

        if (!isAuthorized) {
            registrationForm.setVisible(true);//панель авторизации и регистрации
            registrationForm.setManaged(true);
            mainChatPanel.setVisible(false);
            mainChatPanel.setManaged(false);

        } else {
            registrationForm.setVisible(false);//делаем окно видимым (по умолчанию в sample visible="false")
            registrationForm.setManaged(false);//выделяется место под HBox, если окно видимо (по умолчанию в sample managed="false")
            mainChatPanel.setVisible(true);
            mainChatPanel.setManaged(true);
        }
    }

    //метод отображения элементов GUI в режиме Зарегистрирован/Незарегистрирован
    public void setRegistered(boolean isRegistered){

        //на всякий случай скрываем панель основного чата
        mainChatPanel.setVisible(false);
        mainChatPanel.setManaged(false);

        if(!isRegistered){
            //если не зарегистрирован, то
            //открываем блок верхних меток, пояснений для регистрации
            regFormTopLabelsBox.setVisible(true);
            regFormTopLabelsBox.setManaged(true);
            //открываем блок Имени
            regFormNicknameBox.setVisible(true);
            regFormNicknameBox.setManaged(true);
            //открываем блок кнопок для регистрации
            regFormRegBtnsBox.setVisible(true);
            regFormRegBtnsBox.setManaged(true);
            //скрываем блок кнопок авторизации
            regFormAuthBtnsBox.setVisible(false);
            regFormAuthBtnsBox.setManaged(false);

        } else{
            //если зарегистрирован, то наоборот
            //скрываем блок верхних меток, пояснений для регистрации
            regFormTopLabelsBox.setVisible(false);
            regFormTopLabelsBox.setManaged(false);
            //скрываем блок Имени
            regFormNicknameBox.setVisible(false);
            regFormNicknameBox.setManaged(false);
            //скрываем блок кнопок для регистрации
            regFormRegBtnsBox.setVisible(false);
            regFormRegBtnsBox.setManaged(false);
            //открываем блок кнопок авторизации
            regFormAuthBtnsBox.setVisible(true);
            regFormAuthBtnsBox.setManaged(true);
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

                    //TODO временно
                    System.out.println("connect.new Thread.currentThread(): " + Thread.currentThread().getId());

                    try {
                        boolean serverClosed = false;

                        //***Блок для авторизации и регистрации***
                        while (true) {
                            String str = in.readUTF();

                            //TODO Временно
                            System.out.println("блок для авторизации и регистрации. while str: " + str);

                            //ловим все служебные сообщения, чтобы не выводить их в TextArea
                            if (str.startsWith("/")) {

                                //ловим сообщение от сервера об отключении
                                if (str.equals("/serverclosed")) {
                                    //устанавливаем флаг, что сервер отключен, чтобы не перейти в блок отправки сообщений
                                    serverClosed = true;
                                    break;
                                }

                                //если пришло подтверждение авторизации, переходим в форму чата и прерываем процесс
                                if (str.startsWith("/authok")) {
                                    //скрываем элементы GUI для регистрации
                                    setRegistered(true);
                                    //скрываем элементы GUI для авторизации
                                    setAuthorized(true);

                                    //TODO L8hwTask5.Добавил
                                    //выделяем полученный с сервера собственный ник пользователя
                                    int splitLimit = 2;
                                    String[] tokens = str.split(" ", splitLimit);
                                    nick = tokens[1];

                                    break;
                                }

                                //если пришло подтверждение регистрации, отправляем запрос на авторизацию, не прерывая процесс
                                if (str.startsWith("/regok")) {
                                    //скрываем элементы GUI для регистрации
                                    setRegistered(true);
                                    //открываем элементы GUI для авторизации
                                    setAuthorized(false);

                                    //разделяем сообщение на три части
                                    //TODO Не работает авторизация после регистрации.Удалил.Не достаточно
                                    //int splitLimit = 3;
                                    //String[] tokens = str.split(" ", splitLimit);
                                    //отправляем сообщение с логином и паролем для регистрации
                                    //out.writeUTF("/auth " + tokens[1] + tokens[2]);
                                }
                            } else {
                                //выводим сообщения в панель регистрационной формы
                                regFormTextArea.appendText(str + "\n");
                            }
                        }

                        //TODO L8hwTask5.Добавил
                        //Устанавливаем пустой ник партнера для приватного чата пользователя
                        chatCompanionNick = null;
                        //***Блок для разбора сообщений***
                        //проверяем флаг, что сервер отключен, чтобы не начать отслеживать сообщения после отключения сервера
                        while (!serverClosed) {
                            String str = in.readUTF();
                            if (str.equals("/serverclosed")) {
                                break;
                            }

                            //TODO L8hwTask5.Добавил
                            //***Обработка запросов на инициализацию персонального чата***
                            if (str.startsWith("/inv")) {//всегда в строке ник партнера
                                //передаем сообщения на проверку в метод инициализации прив.чата
                                initPrivateChat(str);
                            }
                            //***Обработка запроса на закрытие персонального чата***
                            if (str.startsWith("/invend")) {
                                //закрываем свое окно приватного чата, если партнер его закрыл

                                //TODO не помогло.Удалить
                                //onPrivateChat = false;//TODO ERR Exception in thread "Thread-4" java.lang.IllegalStateException: Not on FX application thread; currentThread = Thread-4.Добавил
                                //prStage.close();//TODO ERR Exception in thread "Thread-4" java.lang.IllegalStateException: Not on FX application thread; currentThread = Thread-4.Удалил

                                //Обнуляем ник партнера по чату
                                chatCompanionNick = null;
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
                        System.out.println("Server connection is lost: " + e);
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //скрываем элементы GUI для регистрации
                        setRegistered(true);
                        //открываем элементы GUI для авторизации
                        setAuthorized(false);
                        //выводим сообщение пользователю
                        regFormTextArea.appendText("Waiting for server connection...Please log in.\n");
                    }
                }
            }).start();
        } catch (IOException e) {
            System.out.println("Waiting for server connection...: " + e);
        }
    }

    //метод запроса на регистрацию по нажатию элемента регистрация в форме авторизации(upperPanel)
    public void tryToRegister(){
        //открываем окно регистрационной формы
        setRegistered(false);
        regFormTextArea.clear();
    }

    //метод запроса на регистрацию(сохранение) данных из регистрационной формы по событию кнопки Отправить
    public void getRegistration(){
        if (socket == null || socket.isClosed()) {//TODO лишнее, если есть while?
            // сначала подключаемся к серверу, если не подключен(сокет не создан или закрыт)
            //если сервер еще не запущен, выводим сообщение и пытаемся подключиться в бесконечном цикле
            while(socket == null || socket.isClosed()){
                connect();
            }
        }
        try {
            //TODO удалить? Лучше очищать перед выводом текста.
            //очищаем поле от старых сообщений
            regFormTextArea.clear();
            // отправка на сервер запроса на регистрацию данных введенных в форме регистрации
            out.writeUTF("/reg " + regFormNickField.getText() + " " + regFormLoginField.getText() + " " + regFormPasswordField.getText());

            //TODO Временно
            System.out.println("getRegistration() str:" + "/reg " + regFormNickField.getText() + " " + regFormLoginField.getText() + " " + regFormPasswordField.getText());

            //Не нужно очищать поле, если не зарегистрировался, чтобы не вбивать еще раз.//TODO Проверить надо ли?
            regFormNickField.clear();//очищаем поле имени в чате
            regFormLoginField.clear();//очищаем поле логина
            regFormPasswordField.clear();//очищаем поле пароля
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Метод по нажатию кнопки в форме регистрации
    public void cancelRegistration(){
        //возвращаемся из регистрационной формы в авторизационную
        setRegistered(true);
        regFormLoginField.clear();
        regFormPasswordField.clear();
        regFormTextArea.clear();
    }

    //Метод для авторизации. Запускается кнопкой Авторизоваться в форме регистрации(upperPanel)
    public void tryToAuth() {
        if (socket == null || socket.isClosed()) {//TODO лишнее, если есть while?
            // сначала подключаемся к серверу, если не подключен(сокет не создан или закрыт)
            //если сервер еще не запущен, выводим сообщение и пытаемся подключиться в бесконечном цикле
            while(socket == null || socket.isClosed()){
                connect();
            }
        }
        try {
            //TODO удалить? Лучше очищать перед выводом текста.
            //очищаем поле от старых сообщений
            regFormTextArea.clear();
            // отправка сообщений на сервер для авторизации
            out.writeUTF("/auth " + regFormLoginField.getText() + " " + regFormPasswordField.getText());
            regFormLoginField.clear();//очищаем поле логина
            regFormPasswordField.clear();//очищаем поле пароля
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

            //запрещаем кликать на свой ник в списке
            if (clientList.getSelectionModel().getSelectedItem().equals(nick)){

                //TODO L8hwTask5. Вывести предупреждение пользователю
                //?????  "Нельзя выбрать самого себя!"

                //Обнуляем ник партнера по чату
                chatCompanionNick = null;

                //TODO временно
                System.out.println("Нельзя выбрать самого себя!");

                return;
            }

            //проверяем не начат ли уже с кем-то приватный чат
            //другой не сможет пригласить, если пользователь уже чатится с кем-то приватно
            if (chatCompanionNick == null) {

                //TODO L8hwTask5.Добавил
                //резервируем прив.чат для партнера
                chatCompanionNick = clientList.getSelectionModel().getSelectedItem();

                //отправляем партнеру приглашение початиться
                try {
                    // отправляем сообщение приглашение партнеру начать приватный чат
                    out.writeUTF("/invto " + chatCompanionNick);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else{
                //TODO L8hwTask5. Вывести предупреждение пользователю
                //????? "Ваш приватный чат занят!"

                //TODO временно
                System.out.println("Ваш приватный чат занят!");

            }
        }
    }

    //TODO L8hwTask5.Добавил
    public boolean initPrivateChat(String str) {//TODO boolean лишнее?
        try {

            //TODO Временно.
            System.out.println("0.Controller.initPrivateChat. str: " + str);

            //выделяем ник партнера по чату из служебного сообщения
            String[] temp = str.split(" ", 2);

            //если приглашение початиться пришло от партнера
            if (str.startsWith("/invto")) {
                //проверяем свободен ли мой приватный чат
                if (chatCompanionNick == null) {
                    //если мой чат тоже свободен, резервируем его для ника партнера
                    chatCompanionNick = temp[1];
                    //инициализируем приватный чат
                    startPrivateChat();
                    //отправляем на мой сервер подтверждение
                    out.writeUTF("/invok " + chatCompanionNick);
                    return true;
                } else {
                    //отправляем на мой сервер отказ чатиться
                    out.writeUTF("/invnot " + chatCompanionNick);
                    return false;
                }
            }
            //если от партнера пришел отказ, его чат занят
            if (str.startsWith("/invnot")) {

                //TODO. Вывести сообщение в GUI.
                //..."Адресат занят другим приватным чатом!"

                //TODO Временно.
                System.out.println("Адресат занят другим приватным чатом!");

                return false;
            }
            //если от партнера пришло подтверждение, что все готово начать чат
            if (str.startsWith("/invok")) {
                //инициализируем приватный чат
                startPrivateChat();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //TODO L8hwTask5.Adding in into private chat.Добавил
    //если приватный чат инициализирован, открыть новое окно
    //и подтверждаем готовность инициализации приватного чата
    private void startPrivateChat(){

        //TODO не помогло.Удалить
        /*//TODO ERR Exception: Not on FX application thread.Добавил.Не нужно?
        //устанавливаем метку разрешения на приватный чат(для взаимодействия между потоками основного и приватного чата)
        onPrivateChat = true;*/

        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                //TODO временно
                System.out.println("initPrivateChat.Platform.runLater.currentThread(): " + Thread.currentThread().getId());

                try {
                    //открываем отдельное окно для приватного чата
                    prStage = new PrivateChatStage(Controller.this);
                    prStage.show();

                    //обработчик закрытия окна персонального чата
                    //prStage.setOnCloseRequest(event -> {//TODO ERR stage.close
                    prStage.setOnHiding(event -> {
                        //отправляем запрос партнеру по чату о закрытии окна чата
                        //если окно закрывается после сообщения от партнера о выходе из приватного чата,
                        // а не по крестику закрыть окно
                        if(chatCompanionNick != null){
                            try {
                                out.writeUTF("/invend " + chatCompanionNick);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            //Обнуляем ник партнера по чату
                            chatCompanionNick = null;

                            //TODO не помогло.Удалить
                            /*//сбрасываем метку разрешения на приватный чат(для взаимодействия между потоками основного и приватного чата)
                            onPrivateChat = false;*/

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
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
                    ) {//if (если мое - сообщение справа)//TODO Удалить this?
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
        //TODO L8hwTask5.Closing window if partner has left.Добавил
        //закрываем свое окно приватного чата, если партнер вышел
        if (chatCompanionNick == null){

            //TODO временно
            System.out.println("Your partner has left. Closing the window...");

            Platform.runLater(new Runnable() {//TODO Лишнее? Не помогло
                @Override
                public void run() {
                    //выводим сообщение пользователю в окно приватного чата
                    Label label = new Label("Your partner has left. Closing the window...");
                    VBox vBox = new VBox();
                    vBox.setAlignment(Pos.TOP_LEFT);
                    //добавляем метку в бокс
                    vBox.getChildren().add(label);
                    //добавляем vBox в общий бокс чата
                    prVBoxChat.getChildren().add(vBox);
                }
            });

            //TODO временно
            System.out.println("sendMsgInPrivateChat.Platform.runLater.currentThread(): " + Thread.currentThread().getId());

            //TODO L8hwTask5.Closing window if partner has left.ERR Не выводится сообщ.пользователю.Добавил
            // хотя бы выводим сообщение в основной чат.Работает НЕВЕРНО - приходит не самому, а партнеру
            try {
                DataOutputStream out = ((PrivateChatStage)prBtnSend.getScene().getWindow()).out;
                out.writeUTF("Your partner has left. The private chat window has been closed.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            //закрываем свое окно приватного чата
            prStage.close();//TODO L8hwTask5.Closing window if partner has left.Добавил.ERR NullPointerException.Добавил static

            return;
        }

        //TODO временно
        System.out.println("sendMsgInPrivateChat.Platform.runLater.currentThread(): " + Thread.currentThread().getId());

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
            //System.out.println("Controller.sendMsgInPrivateChat.");
            //System.out.println(".actionEvent.getTarget().toString(): " + actionEvent.getTarget().toString());
            //System.out.println(".prTextField.getText(): " + prTextField.getText());

            //TODO L8hwTask5.Добавил
            DataOutputStream out = ((PrivateChatStage)prBtnSend.getScene().getWindow()).out;
            out.writeUTF("/w " + chatCompanionNick + " " + prTextField.getText());

            //TODO временно.
            System.out.println("Controller.sendMsgInPrivateChat. after out.writeUTF. out(str): " + "/w " + chatCompanionNick + " " + prTextField.getText());

            prTextField.clear();
            prTextField.requestFocus();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public VBox getPrVBoxChat() {
        return prVBoxChat;
    }

    public TextField getPrTextField() {
        return prTextField;
    }

    public Button getPrBtnSend() {
        return prBtnSend;
    }

    public HBox getPrBottomPanel() {
        return prBottomPanel;
    }

    public Socket getSocket() {
        return socket;
    }

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public String getChatCompanionNick() {
        return chatCompanionNick;
    }
}
