package Lesson_6.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    @FXML
    TextArea textArea;

    @FXML
    TextField textField;

    @FXML
    Button btn1;

    Socket socket;
    DataInputStream in;//входящий на клиента от сервера поток
    DataOutputStream out;//исходящий поток от клиента на сервер

    final String IP_ADRESS = "localhost";
    final int PORT = 8189;

    /**
     * Метод запуска клиента
     * Переопределяем метод интерфейса Initializable
     * @param location
     * @param resources
     */
    @Override
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
                            //читаем сообщение в кодировке UTF
                            String str = in.readUTF();
                            textArea.appendText(str + "\n");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //TODO перенесен из главного потока.
                    //иначе возникает исключение java.io.EOFException
                    finally {
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
        //TODO перенесен поток клиента.
        //иначе возникает исключение java.io.EOFException
        /*finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }

    /**
     * Метод отправки сообщения от клиента
     * Переопределяем метод интерфейса Initializable
     */
    public void sendMsg() {
        try {
            //сообщение из текстового поля клиента отправляем на сервер в кодировке UTF
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
