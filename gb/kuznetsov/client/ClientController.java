package ru.gb.kuznetsov.client;

import ru.gb.kuznetsov.server.ServerWindowController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClientController {

    private final ClientView clientView;
    private final ServerWindowController server;
    private boolean loginTrigger;
    private String name;

    public ClientController(ClientView clientView, ServerWindowController server) {
        this.clientView = clientView;
        this.server = server;
    }

    private void appendToClientTextArea(String text) {
        clientView.showMessage(text);
    }

    protected boolean connectToServer(String name) {
        this.name = name;
        if (server.connectUser(this, name)) {
            appendToClientTextArea("Вы успешно подключились!\n");
            loginTrigger = true;

            String log = server.getServerTextArea();
//            String log = server.getServerTextAreaToServer();
            if (log != null) {
                appendToClientTextArea(log);

            }
            return true;
        } else {
            appendToClientTextArea("Подключение не удалось");
            return false;
        }
    }

    public void userDisconnectedByServer() {
        if (loginTrigger) {
            loginTrigger = false;
            clientView.disconnectedByServer();
            appendToClientTextArea("Вы были отключены от сервера!");
        }
    }

    public void disconnectFromServer(String name) {
        if (loginTrigger) {
            server.disconnectUser(this);
            loginTrigger = false;
            appendToClientTextArea("Вы отключены от сервера.");
        } else {
            appendToClientTextArea("Нет подключения к серверу.");
        }
    }

    public void message(String text) {
        if (loginTrigger) {
            if (!text.equals("")) {
                LocalDateTime localDateTime = LocalDateTime.now();
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d-M-yyyy");
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("H:mm:ss");

                String dataNow = localDateTime.format(dateFormatter);
                String hourNow = localDateTime.format(timeFormatter);
                String resDateNow = "дата: " + dataNow + " время: " + hourNow;

                server.message(resDateNow + " " + name + ": " + text);
            }
        } else {
            appendToClientTextArea("Нет подключения к серверу");
        }
    }

    public void answerFromServer(String text) {
        appendToClientTextArea(text);
    }


    public String getNameFomUser() {
        return clientView.getUserName();
    }

}
