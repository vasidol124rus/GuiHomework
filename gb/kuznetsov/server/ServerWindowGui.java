package ru.gb.kuznetsov.server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerWindowGUI extends JFrame implements ServerWindowView {

    ServerWindowController serverWindowController;
    private ChatHistoryWindow chatHistoryWindow;


    private static final int WINDOW_HEIGHT = 230;
    private static final int WINDOW_WIDTH = 500;
    private static final int POST_X = 850;
    private static final int POST_Y = 200;



    JButton btnStart, btnStop;
    JTextArea serverTextArea;

    public ServerWindowGUI() {

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(POST_X , POST_Y);
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
        Color backGroundColor = new Color(2,5,85);
        Color fontColor = new Color(2,155,15);
        Font font = new Font("Serif",Font.BOLD,15);
        serverTextArea = new JTextArea();
        serverTextArea.setBackground(backGroundColor);
        serverTextArea.setForeground(fontColor);
        serverTextArea.setFont(font);
        add(serverTextArea);
        add(createButtons(), BorderLayout.SOUTH);
    }

    private Component createButtons() {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        ImageIcon tick = new ImageIcon("src/main/java/org/example/DZ2/Server/Png/tick.png");
//        btnStart = new JButton("Start");
        btnStart = new JButton(tick);
        ImageIcon cross = new ImageIcon("src/main/java/org/example/DZ2/Server/Png/cross.png");
//        btnStop = new JButton("Stop");
        btnStop = new JButton(cross);

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
