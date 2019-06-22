package Lesson_6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 6. Работа с сетью.
 * @author Artem Evdokimov.
 * Написание эхо-сервера.
 * Клиентская часть.
 */
public class EchoClient extends JFrame {
    //поля клиента, связанные с сетевой частью
    //задаём адрес и номер порта эхо-сервера, к которому будет подключаться клиент
    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8189 ;

    //поле ввода тескта
    private JTextField msgInputField;
    //основное текстовое поле нашего чата
    private JTextArea chatArea;

    //Для открытия соединения с сервером и обмена сообщениями используются объекты классов Socket,
    // DataInputStream и DataOutputStream, по аналогии с серверной частью.
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    //Конструктор отвечает за выполнение инициализации интерфейса через prepareGUI() и
    //подключение к серверу с помощью метода openConnection().
    public EchoClient() {
        try {
            openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        prepareGUI();
    }

    /**
     * Метод подключения клиента к серверу.
     * @throws IOException
     */
    public void openConnection() throws IOException {
        //инициализируем сетевое соединение и обработчики
        //открываем сокет с указанием ip-адреса и порта сервера
        socket = new Socket(SERVER_ADDR, SERVER_PORT);

        //запрашиваем у сокета доступ к исходящему(в сторону сервера), и входящему
        //(направленному к нашему клиенту) потокам, но так как имея просто потоки мы
        //ничего сделать не сможем(не сможем передавать данные), заворачиваем их в обработчики
        //DataInputStream in и DataOutputStream out.
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        //запускаем отдельный поток, чтобы слушать что же нам пришлет сервер. Если попробуем
        // это сделать в текущем потоке, то ничего не выйдет, так как зависнем в цикле while(),
        // и построение нашего объекта (клиентского окна) так и не завершится
        new Thread( new Runnable() {
            @Override
            public void run() {
                try {
                    //слушаем входящие сообщения, операция readUTF() блокирующая, и поток будет
                    //периодически переходить в режим ожидания, пока сервер что-нибудь не пришлет.
                    while ( true ) {
                        //записываем полученное сообщение в строку strFromServer и выводим в
                        //основное текстовое поле нашего чата charArea
                        String strFromServer = in.readUTF();
                        //добавляем условие выхода клиента из сеанса
                        if (strFromServer.equalsIgnoreCase( "/end" )) {
                            break ;
                        }
                        //выводим в текстовое поле полученное от сервера сообщение
                        chatArea.append(strFromServer);
                        //переходим на следующую строку
                        chatArea.append( "\n" );
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * Метод закрытия соединения
     */
    public void closeConnection() {
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

    /**
     * Метод для отправки сообщения от клиента в сторону сервера
     * Клиент берет содержимое текстового поля для отправки и с помощью метода writeUTF()
     * отправляет его серверу, после чего очищает текстовое поле и переводит на него фокус.
     * Если вдруг не удалось отправить сообщение, то будет показано всплывающее окно с ошибкой.
     */
    public void sendMessage() {
        if (!msgInputField.getText().trim().isEmpty()) {
            try {
                //передаем на сервер сообщение, введенное в текстовое поле
                out.writeUTF(msgInputField.getText());
                //очищаем текстовое поле и возвращаем курсор в текстовое поле
                msgInputField.setText("");
                msgInputField.grabFocus();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog( null , "Ошибка отправки сообщения" );
            }
        }
    }

    /**
     * Метод инициализации интерфейса клиента
     * подготавливает интерфейс Swing к работе
     */
    public void prepareGUI() {
        // Параметры окна
        setBounds( 600 , 300 , 500 , 500 );
        setTitle( "Клиент" );
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // Текстовое поле для вывода сообщений
        chatArea = new JTextArea();
        chatArea.setEditable( false );
        chatArea.setLineWrap( true );
        add( new JScrollPane(chatArea), BorderLayout.CENTER);
        // Нижняя панель с полем для ввода сообщений и кнопкой отправки сообщений
        JPanel bottomPanel = new JPanel( new BorderLayout());
        JButton btnSendMsg = new JButton( "Отправить" );
        bottomPanel.add(btnSendMsg, BorderLayout.EAST);
        msgInputField = new JTextField();
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(msgInputField, BorderLayout.CENTER);
        btnSendMsg.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        msgInputField.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
        // Настраиваем действие на закрытие окна
        addWindowListener( new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super .windowClosing(e);
                try {
                    out.writeUTF( "/end" );
                    closeConnection();
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        });
        setVisible( true );
    }

    /**
     * Гланный метод запускающий наше клиентское приложение
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater( new Runnable() {
            @Override
            public void run() {
                new EchoClient();
            }
        });
    }
}
