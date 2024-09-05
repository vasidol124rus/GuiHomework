package ru.gb.kuznetsov.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientGUI extends JFrame implements ClientView {


    private static final int OFFSET_STEP = 20;
    private static final int WINDOW_HEIGHT = 330;
    private static final int WINDOW_WIDTH = 500;
    private static final int POST_X = 100;
    private static final int POST_Y = 100;
    private static final String[] USERS = {
            new ClientInfo("Ivan_Ivanovich").getName(),
            new ClientInfo("Petr_Petrovich").getName(),
            new ClientInfo("Vasilisa_Vasilisovna").getName(),
            new ClientInfo("Zaxar_Zaxarovich").getName(),
            new ClientInfo("Nicka_Nickolayevna").getName()
    };
    private static int count = 0;
    private ClientController clientController;
    private JTextArea сlientTextArea;
    private JTextField tfIPAddress, tfPort, tfMessage;
    private JPasswordField password;
    private JButton btnLogin, btnSend;
    private JPanel headerPanel;
    private JComboBox comboBox = null;

    public ClientGUI() {

        int xOffset = POST_X + count * OFFSET_STEP;
        int yOffset = POST_Y + count * OFFSET_STEP;
        count++;
        setLocation(xOffset, yOffset);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("Chat client");
        createPanel();
        setVisible(true);

    }

    public JComboBox getComboBox() {
        return comboBox;
    }

    public void setClientController(ClientController clientController) {
        this.clientController = clientController;
    }

    private void createPanel() {
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createLog());
        add(createFooter(), BorderLayout.SOUTH);
    }

    public void connectToServer() {
        if (clientController.connectToServer(comboBox.getSelectedItem().toString())) {
            headerPanel.setVisible(false);
        }
    }


    private Component createHeaderPanel() {
        headerPanel = new JPanel(new GridLayout(2, 3));
        tfIPAddress = new JTextField("127.0.0.1");
        tfPort = new JTextField("8888");
        comboBox = new JComboBox<>(USERS);
        password = new JPasswordField("12345");
        ImageIcon login = new ImageIcon("src/main/java/org/example/DZ2/Clients/Png/login.png");
        btnLogin = new JButton(login);
        btnLogin = new JButton("login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connectToServer();
            }
        });

        headerPanel.add(tfIPAddress);
        headerPanel.add(tfPort);
        headerPanel.add(new JPanel());
        headerPanel.add(comboBox);
        headerPanel.add(password);
        headerPanel.add(btnLogin);

        return headerPanel;
    }

    private void message() {
        clientController.message(tfMessage.getText());
        tfMessage.setText("");
    }

    private Component createLog() {
        сlientTextArea = new JTextArea();
        Color backGroundColor = new Color(2, 5, 85);
        Color fontColor = new Color(2, 155, 15);
        Font font = new Font("Serif", Font.BOLD, 15);
        сlientTextArea.setBackground(backGroundColor);
        сlientTextArea.setForeground(fontColor);
        сlientTextArea.setFont(font);
        сlientTextArea.setEditable(false);
        return new JScrollPane(сlientTextArea);
    }

    private Component createFooter() {
        JPanel panel = new JPanel(new BorderLayout());
        tfMessage = new JTextField();
        tfMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    System.out.println();
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
        panel.add(tfMessage);
        panel.add(btnSend, BorderLayout.EAST);
        return panel;
    }


    @Override
    public void showMessage(String message) {
        сlientTextArea.append(message + "\n");
    }

    @Override
    public void disconnectedByServer() {
        hideHeaderPanel(true);
    }

    @Override
    public void userDisconnected() {
        if (clientController != null) {
            clientController.disconnectFromServer(getUserName());
            clientController.userDisconnectedByServer();
        }
    }


    private void hideHeaderPanel(boolean hide) {
        headerPanel.setVisible(hide);
    }

    @Override
    public String getUserName() {
        return comboBox.getSelectedItem().toString();
    }


    @Override
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            this.userDisconnected();
        }
    }


}