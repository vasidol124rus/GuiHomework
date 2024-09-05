package ru.gb.kuznetsov.server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerWindowGui extends JFrame implements ServerWindowView {

    private static final int WINDOW_HEIGHT = 350;
    private static final int WINDOW_WIDTH = 500;
    private static final int POST_X = 100;
    private static final int POST_Y = 300;
    ServerWindowController serverWindowController;
    JButton btnStart, btnStop;
    JTextArea serverTextArea;
    private ChatHistoryWindow chatHistoryWindow;

    public ServerWindowGui() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(POST_X, POST_Y);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);


        setTitle("Chat server");
        setLocationRelativeTo(null);

        createPanel();

        setVisible(true);


    }

    public void setServerWindowController(ServerWindowController serverWindowController) {
        this.serverWindowController = serverWindowController;
    }


    private void createPanel() {
        Color backGroundColor = new Color(2, 5, 85);
        Color fontColor = new Color(5, 155, 15);
        Font font = new Font("Serif", Font.BOLD, 15);
        serverTextArea = new JTextArea();
        serverTextArea.setBackground(backGroundColor);
        serverTextArea.setForeground(fontColor);
        serverTextArea.setFont(font);
        add(serverTextArea);
        add(createButtons(), BorderLayout.SOUTH);
    }

    private Component createButtons() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");
        btnStart.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (serverWindowController.isServerWorking) {
                    showMessage("Сервер уже был запущен");

                } else {
                    serverWindowController.isServerWorking = true;
                    showMessage("Сервер запущен!");
                    serverWindowController.getServerTextArea();
//                    serverWindowController.getServerTextAreaToServer();

                    String chatHistory = serverWindowController.getServerTextArea();
                    chatHistoryWindow = new ChatHistoryWindow(chatHistory);

                }
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!serverWindowController.isServerWorking) {
                    showMessage("Сервер уже был остановлен");
                } else {
                    serverWindowController.isServerWorking = false;
                    while (!serverWindowController.clientControllerList.isEmpty()) {
                        serverWindowController.disconnectUser(serverWindowController.clientControllerList.get(serverWindowController.clientControllerList.size() - 1));

                    }
                    showMessage("Сервер остановлен!");
                    if (chatHistoryWindow != null) {
                        chatHistoryWindow.dispose();
                        chatHistoryWindow = null;
                    }
                }
            }
        });

        panel.add(btnStart);
        panel.add(btnStop);
        return panel;
    }

    @Override
    public void userConnectionInfo(String username) {
        serverTextArea.append("Пользователь: " + username + " подключи(лся)/лась к серверу\n");
    }

    @Override
    public void userDisconnectInfo(String username) {
        serverTextArea.append("Пользователь: " + username + " отключи(лся)/лась от сервера\n");
    }


    @Override
    public void showMessage(String message) {
        serverTextArea.append(message + "\n");
    }

    @Override
    public void connectToServer() {

    }


}

