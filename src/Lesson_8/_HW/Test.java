package Lesson_8._HW;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Test /*extends Application*/ {

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