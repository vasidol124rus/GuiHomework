package ru.gb.kuznetsov;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ServerWindow extends JFrame {
    private static final int POS_X = 500;
    private static final int POS_Y = 550;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private static final String LOG_PATH = "src/log.txt";

    private static boolean isServerWorking;

    private final JButton btnStart = new JButton("Start");
    private final JButton btnStop = new JButton("Stop");
    private final JTextArea log = new JTextArea();
    private JScrollPane logJScrollPane;

    private Date date = new Date();

    List<ClientGUI> clientGUIList;

    public ServerWindow() {
        clientGUIList = new ArrayList<>();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true);

        createPanel();

        setVisible(true);
    }

    public boolean connectUser(ClientGUI clientGUI) {
        if (!isServerWorking){
            return false;
        }
        clientGUIList.add(clientGUI);
        return true;
    }

    public String getLog() {
        return readLog();
    }

    public void disconnectUser(ClientGUI clientGUI) {
        clientGUIList.remove(clientGUI);
        if (clientGUIList != null) {
            clientGUI.disconnectFromServer();
        }
    }

    public void message(String message) {
        if (!isServerWorking){
            return;
        }
        message += "";
        appendLog(message);
        answerAll(message);
        saveInLog(message);
    }

    private void appendLog(String message) {
        log.append(date + ": " + message + "\n");
    }

    private void answerAll(String message) {
        for (ClientGUI clientGUI : clientGUIList) {
            clientGUI.answer(message);
        }
    }

    private void saveInLog(String message) {
        try (FileWriter writer = new FileWriter(LOG_PATH, true)) {
            writer.write(String.valueOf(date));
            writer.write(message);
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readLog() {
        StringBuilder sb = new StringBuilder();
        try (FileReader reader = new FileReader(LOG_PATH)) {
            int count;
            while ((count = reader.read()) != -1) {
                sb.append((char) count);
            }
            sb.delete(sb.length() - 1, sb.length());
            return sb.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void createPanel() {
//        log = new JTextArea();
        add(log);
        add(createButtons(), BorderLayout.SOUTH);
    }

    private Component createButtons() {
        JPanel panelButton = new JPanel(new GridLayout(1, 2));
        panelButton.add(btnStart);
        panelButton.add(btnStop);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isServerWorking){
                    appendLog("Сервер уже был запущен!");
                }else {
                    isServerWorking = true;
                    appendLog("Сервер запущен!!!");
                }
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isServerWorking){
                    appendLog("Сервер уже был остановлен!");
                }else {
                    isServerWorking = false;
                    while (!clientGUIList.isEmpty()) {
                        disconnectUser(clientGUIList.get(clientGUIList.size() - 1));
                    }
                    appendLog("Сервер остановлен!!!");
                }
            }
        });
        panelButton.add(btnStart);
        panelButton.add(btnStop);
        return panelButton;
    }
}