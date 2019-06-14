package Lesson_4.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Java Core. Продвинутый уровень.
 * Вебинар 03 июня 2019 MSK (UTC+3).
 * Урок 4.2. Продвинутые вопросы создания графического интерфейса.
 * Повторение библиотеки SWING.
 * @author Artem Evdokimov.
 */
public class MainSwing {
    public static void main(String[] args) {
        new Lesson_4.swing.MyWindow();
    }
}

class MyWindow extends JFrame {
    public MyWindow(){
        setTitle("Hello!");
        setBounds(800,400,400,400);

        JPanel panel = new JPanel(new GridLayout(1,2));

        JButton ok = new JButton("Open");
        JButton cancel = new JButton("Cancel");

        panel.add(ok);
        panel.add(cancel);

        add(panel, BorderLayout.SOUTH);

        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Lesson_4.swing.SecondWindow();
            }
        });

       // setLayout(null);

//        JButton jButton = new JButton("test");
//        jButton.setBounds(20,20,50,100);
//        add(jButton);
//
//        JButton jButton1 = new JButton("test1");
//        jButton1.setBounds(80,120,40,30);
//        add(jButton1);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}


class SecondWindow extends JFrame {
    public SecondWindow(){
        setTitle("SecondWindow!");
        setBounds(500,300,200,200);

        JButton btn1 = new JButton("SecondWindow");

        add(btn1, BorderLayout.CENTER);

        // setLayout(null);

//        JButton jButton = new JButton("test");
//        jButton.setBounds(20,20,50,100);
//        add(jButton);
//
//        JButton jButton1 = new JButton("test1");
//        jButton1.setBounds(80,120,40,30);
//        add(jButton1);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
