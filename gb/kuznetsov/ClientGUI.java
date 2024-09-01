package ru.gb.kuznetsov;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;

public class ClientGUI extends JFrame {
    private static final int POS_X = 600;
    private static final int POS_Y = 250;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private Date date = new Date();

    ServerWindow serverWindow;
    private boolean connected;
    private String name;

    JTextArea log;
    JTextField ipAddress, port, login, message;
    JPasswordField password;
    JButton btnLogin, btnSend;
    private JPanel loginPanel;

    public ClientGUI(ServerWindow serverWindow) {
        this.serverWindow = serverWindow;

        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Client");
        setLocation(POS_X, POS_Y);

        createPanel();
        setVisible(true);
    }

    public void answer(String message) {
        appendLog(message);
    }

    private void connectToServer() {
        if (serverWindow.connectUser(this)) {
            appendLog("Вы успешно подключились к серверу\n");
            loginPanel.setVisible(false);
            connected = true;
            name = login.getText();
            String log = serverWindow.getLog();
            if (log != null) {
                appendLog(log);
            } else {
                appendLog("Подключение не удалось!");
            }
        }
    }

    public void disconnectFromServer() {
        if (connected) {
            loginPanel.setVisible(true);
            connected = false;
            serverWindow.disconnectUser(this);
            appendLog("Вы были отключены от сервера!");
        }
    }

    private void message() {
        if (connected) {
            String text = message.getText();
            if (!text.equals("")) {
                serverWindow.message(name + ": " + text);
                message.setText("");
            }
        } else {
            appendLog("Нет подключения к серверу!");
        }
    }

    private void appendLog(String test) {
        log.setText(date + ": " + test + "\n");
    }

    private void createPanel() {
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createLog());
        add(createFooterPanel(), BorderLayout.SOUTH);
    }
    private Component createHeaderPanel() {
        loginPanel = new JPanel(new GridLayout(2, 3));
        ipAddress = new JTextField("127.0.0.1");
        port = new JTextField("8040");
        login = new JTextField("Vasiliy Kuznetsov");
        password = new JPasswordField("111");
        btnLogin = new JButton("login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer();
            }
        });
        loginPanel.add(ipAddress);
        loginPanel.add(port);
        loginPanel.add(new JPanel());
        loginPanel.add(login);
        loginPanel.add(password);
        loginPanel.add(btnLogin);
        return loginPanel;
    }

    private Component createLog() {
        log = new JTextArea();
        log.setEditable(false);
        return new JScrollPane(log);
    }

    private Component createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        message = new JTextField();
        message.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    message();
                }
            }
        });

        btnSend = new JButton("send");
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message();
            }
        });

        footerPanel.add(message);
        footerPanel.add(btnSend, BorderLayout.EAST);
        return footerPanel;
    }

    @Override
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            disconnectFromServer();
        }
        super.processWindowEvent(e);
    }
}