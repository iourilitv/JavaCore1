package Lesson_8._HW;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Test /*extends Application*/ {

    public static void main(String[] args) {

    }
    /*
    Артем, добрый день. Спасибо, что не бросаете попытки мне помоч.
Ссылка на проект https://drive.google.com/open?id=18TIipnukKGI0gRMJzyUZxUaX8MoDvF8Q .
Если я не использую статик для prStage, то у меня возникает проблема с закрытием окна по полученному сообщению /invend в методе connect.
Аналогичная ситуация и с переменной chatCompanionNick. Пока я ее не сделал статик, она была null при вызове из потока javafx.
У меня два файла fxml, но контроллер один, иначе будут сложности с вызовом переменных между контроллерами. Если честно, не думаю, что проблема в этом. Могу переписать код, но это может потребовать измения всей схемы реализации, а это много работы. Так что, пока пробую другие варианты.
     */


    /*
Конечно же, не работает.
Я нашел несколько отличий моего подхода от вашего.
Можете подсказать, что из этого может давать такой эффект? Не хочется в слепую перелапачивать весь код. Все в классе Controller.
1. Я создаю объект prStage = new PrivateChatStage(Controller.this, prVBoxChat); в потоке Platform.runLater. Кроме того, мне пришлось сделать prStage статической. Иначе в методе connect я не мог закрыть окно по получению /invend.
2. Я использую единый контроллер для обоих файлов fxml и для основного и для приватного чата. А у вас их два.
3. Я не использую List для VBoxов с метками сообщений. В вашем примере используются TextArea, но это вроде не должно влиять.
Можете подсказать?
Кстати, еще вопрос.
Вы видите мой код полностью?
Мне важно понимать, что вы видите, чтобы и вам было легче и мне меньше действий делать.
     */


    /*public void setMsg(String str) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label message = new Label(str);
                VBox messageBox = new VBox(message);
                    if(nick != "") {
                    String[] mass = str.split(":");
                        if(nick.equalsIgnoreCase(mass[0])) {
                            messageBox.setAlignment(Pos.CENTER_RIGHT);
                        }
                    }
                messagesView.getItems().add(messageBox);
            }
        });
    }
    вот такой вариант используйте*/
    /*Ответ.
    Артем добрый день.
    Сомневаюсь, что это поможет.
    Если предположить, что аналогом переменной messagesView из вашего примера является переменная prVBoxChat из моего, то это работать не будет по той же причине.
    Проблема не в выводе информации, а в том, что я не могу вызвать переменную prVBoxChat, которая инициализирована при инициализации объекта класса PrivateChatStage и должна быть привязана к приватному окну.
    Но при попытке обращения к этой переменной из метода коннект, она оказывается null, то есть я обращаюсь не к ней.
    Переменная prVBoxChat точно уже инициализирована к тому моменту и я могу к ней обратиться. Ведь собственное сообщение в приватном чате появляется, т.е. метод sendMsgInPrivateChat в классе Controller работает корректно.
    Но это и понятно, т.к. метод вызывается нажатием кнопки send непосредственно из окна приватного чата и выполняется в потоке приложения javafx (Platform).
    А в моем случае, нужно запустить этот метод из метода connect, т.е. из главного потока, если я правильно понимаю.
    Я уже перепробовал несколько вариантов и точно не работают такие(в методе connect):
    1. showMessage(prVBoxChat, Pos.TOP_LEFT, msg);
    2. VBox vB = ((PrivateChatStage)prBtnSend.getScene().getWindow()).prVBoxChat
       showMessage(vB, Pos.TOP_LEFT, msg);
    3. Platform.runLater(new Runnable() {
         @Override
         public void run() {
           Label label = new Label(msg + "\n");
           VBox vBox = new VBox();
           vBox.setAlignment(Pos.TOP_LEFT);
           vBox.getChildren().add(label);
           prVBoxChat.getChildren().add(vBox);
         }
       });
       Во всех трех случаях исключение NullPointerException возникает на строке, где есть prVBoxChat.
       В тоже время, при работе с окном главного чата тот же метод showMessage(vBoxChat, Pos.TOP_RIGHT, str) работает в обоих случаях.
       То есть, переменная vBoxChat вызывается из метода connect корректно.
       Не пойму в чем разница.
       Прошу предложить другие варианты.
     */



    /*
    Артем, добрый день.
    Вроде понемногу разобрался, но осталась одна принципиальная проблема.
    Хочу: в методе connect в Controller вывести полученное сообщение в окно приватного чата.
    На входе корректно получаю строку "/w nick2 p1" выделяю msg(p1) и запускаю код:
       Platform.runLater(new Runnable() {
         @Override
         public void run() {
            Label label = new Label(msg + "\n");
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.TOP_LEFT);
            vBox.getChildren().add(label);
            prVBoxChat.getChildren().add(vBox);
         }
      });

    Но на строке: prVBoxChat.getChildren().add(vBox); получаю исключение:
    Exception in thread "JavaFX Application Thread" java.lang.NullPointerException
	at Lesson_8._HW.client.Controller$1$3.run(Controller.java:325)
	at com.sun.javafx.application.PlatformImpl.lambda$null$172(PlatformImpl.java:295)
	at java.security.AccessController.doPrivileged(Native Method)
	at com.sun.javafx.application.PlatformImpl.lambda$runLater$173(PlatformImpl.java:294)
	at com.sun.glass.ui.InvokeLaterDispatcher$Future.run(InvokeLaterDispatcher.java:95)
	at com.sun.glass.ui.win.WinApplication._runLoop(Native Method)
	at com.sun.glass.ui.win.WinApplication.lambda$null$147(WinApplication.java:177)
	at java.lang.Thread.run(Thread.java:748)

	В инете так ничего путного и не нашел.
	В тоже время, собственное сообщение в окно приватного чата выводится нормально в методе
    public void sendMsgInPrivateChat (ActionEvent actionEvent) {
        try {
            //принимаем строку из текстового поля
            String str = prTextField.getText();
            //не показываем служебные сообщения у себя
            if(!str.startsWith("/")) {
                //выводим пользователю собственное сообщение в окно приватного чата.
                showMessage(prVBoxChat, Pos.TOP_RIGHT, prTextField.getText());
            }
            //отправляем сообщение на сервер(ClientHandler)
            DataOutputStream out = ((PrivateChatStage)prBtnSend.getScene().getWindow()).out;
            out.writeUTF("/w " + chatCompanionNick + " " + str);
            //очищаем текстовое поле и возвращаем ему курсор
            prTextField.clear();
            prTextField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Вызываемый здесь метод showMessage - эквивалент кода в методе connect(его я еще в начале попробовал).
    private void showMessage(VBox vBoxCh, Pos position, String msg){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Label label = new Label(msg + "\n");
                VBox vBox = new VBox();
                vBox.setAlignment(position);
                //добавляем метку в бокс
                vBox.getChildren().add(label);
                //добавляем vBox в общий бокс чата
                vBoxCh.getChildren().add(vBox);
            }
        });
    }



     */

    /*@Override
    public void start(Stage stage) {
        Text text = new Text("!");
        text.setFont(new Font(40));
        VBox box = new VBox();
        box.getChildren().add(text);
        final Scene scene = new Scene(box,300, 250);
        scene.setFill(null);
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");
            }
        });
        stage.close();

    }*/

    /*public static void main(String[] args) {
        launch(args);
    }*/
}

/*
import javafx.concurrent.Task;

        Task<Integer> task = new Task<Integer>() {
@Override protected Integer call() throws Exception {
        int iterations = 0;
        for (iterations = 0; iterations < 100000; iterations++) {
        if (isCancelled()) {
        break;
        }
        System.out.println("Iteration " + iterations);
        }
        return iterations;
        }

@Override protected void succeeded() {
        super.succeeded();
        updateMessage("Done!");
        }

@Override protected void cancelled() {
        super.cancelled();
        updateMessage("Cancelled!");
        }

@Override protected void failed() {
        super.failed();
        updateMessage("Failed!");
        }
};*/