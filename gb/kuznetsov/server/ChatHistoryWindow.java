package ru.gb.kuznetsov.server;

import javax.swing.*;
import java.awt.*;

public class ChatHistoryWindow extends JFrame {
    private static final int POST_X = 850;
    private static final int POST_Y = 200;
    private final JTextArea chatHistoryTextArea;

    public ChatHistoryWindow(String chatHistory) {
        setTitle("Chat History");
        setSize(500, 300);
        setLocation(POST_X + 10, POST_Y + 10);
//        setLocationRelativeTo(null);

        Color backGroundColor = new Color(2, 5, 85);
        Color fontColor = new Color(2, 155, 15);
        Font font = new Font("Serif", Font.BOLD, 15);

        chatHistoryTextArea = new JTextArea(chatHistory);
        chatHistoryTextArea.setBackground(backGroundColor);
        chatHistoryTextArea.setForeground(fontColor);
        chatHistoryTextArea.setFont(font);
        chatHistoryTextArea.setEditable(false);
        add(new JScrollPane(chatHistoryTextArea));

        setVisible(true);
    }
}
