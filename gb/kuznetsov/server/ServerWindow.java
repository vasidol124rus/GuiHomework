package ru.gb.kuznetsov.server;

import ru.gb.kuznetsov.client.ClientGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class ServerWindow extends JFrame {


    private static final int WINDOW_HEIGHT = 250;
    private static final int WINDOW_WIDTH = 500;
    private static final int POST_X = 500;
    private static final int POST_Y = 200;

    private static final String LOG_FILE_PATH = "src/main/java/org/example/DZ1/V1/log.txt";
    private final JButton buttonStart = new JButton("Start");

    private final JButton buttonStop = new JButton("Stop");

    private final JTextArea textAreaServer = new JTextArea();

    private boolean isServerWorking;

    private final ArrayList<ClientGUI> clients = new ArrayList<>();


    JPanel panel = new JPanel(new GridLayout(1, 2));


    public ServerWindow() throws HeadlessException {
        setTitle(" Chat Server");
        panel.add(buttonStart);
        panel.add(buttonStop);
        add(textAreaServer, BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);


        isServerWorking = false;

        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isServerWorking) {
                    textAreaServer.append("Сервер уже остановлен " + isServerWorking + "\n");
                } else {
                    isServerWorking = false;
                    textAreaServer.append("Сервер остановлен " + isServerWorking + "\n");
                    resetClientPanels();
                }
            }
        });


        buttonStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (isServerWorking) {
                    textAreaServer.append("Сервер уже работает " + isServerWorking + "\n");
                } else {
                    isServerWorking = true;
                    textAreaServer.append("Сервер запушен " + isServerWorking + "\n");
                    readLogs();
                }
            }
        });


        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(POST_X, POST_Y);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
//        setResizable(false);
        textAreaServer.setEditable(false);
        setVisible(true);

    }

    public boolean isServerWorking() {
        return isServerWorking;
    }

    public void serverUserConnectionInfo(String username) {
        textAreaServer.append("Пользователь: " + username + " подключи(лся)/лась к серверу\n");
    }

    public void appendMessage(String message) {
        textAreaServer.append(message + "\n");
        writeLog(message);
    }

    public void broadcastMessage(String message) {
        appendMessage(message);
        for (ClientGUI client : clients) {
            client.appendMessage(message);
        }
    }



    public void registerClient(ClientGUI client) {
        if (!clients.contains(client)) {
            clients.add(client);
            sendLogsToClient(client);
        }
    }


    public void addClient(ClientGUI client) {
        clients.add(client);
    }

    private void resetClientPanels() {
        for (ClientGUI client : clients) {
            client.resetPanelTop();
        }
    }

    public void connectUser(String username) {
        if (isServerWorking) {
            serverUserConnectionInfo(username);
        }
    }


    private void writeLog(String message) {
        try (FileWriter fw = new FileWriter(LOG_FILE_PATH, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readLogs() {
        try (FileReader fr = new FileReader(LOG_FILE_PATH);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                textAreaServer.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendLogsToClient(ClientGUI client) {
        try (FileReader fr = new FileReader(LOG_FILE_PATH);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                client.appendMessage(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}