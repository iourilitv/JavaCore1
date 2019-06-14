package Lesson_4.swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatWindow extends JFrame {

    public ChatWindow() {

        setTitle("Chat");

        setBounds(800,300,400,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel bottomPanel = new JPanel();
        JPanel centerPanel = new JPanel();

        centerPanel.setBackground(Color.gray);
        bottomPanel.setBackground(Color.darkGray);

        bottomPanel.setPreferredSize(new Dimension(1,40));

        add(bottomPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        centerPanel.setLayout(new BorderLayout());
        bottomPanel.setLayout(new FlowLayout());

        JButton jb = new JButton("Send!");

        JTextArea jta = new JTextArea();
        JScrollPane jsp = new JScrollPane(jta);
        centerPanel.add(jsp, BorderLayout.CENTER);

        JTextField jtf = new JTextField();

        bottomPanel.add(jtf);
        bottomPanel.add(jb);

        jtf.setPreferredSize(new Dimension(300,28));

        jta.setEditable(false);

        jb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jta.append(jtf.getText() + "\n");
                jtf.setText("");
                jtf.grabFocus();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new Lesson_4.swing.ChatWindow();
    }
}
