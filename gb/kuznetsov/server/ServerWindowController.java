package ru.gb.kuznetsov.server;

import ru.gb.kuznetsov.DataBase.DataBaseView;
import ru.gb.kuznetsov.client.ClientController;

import java.util.ArrayList;
import java.util.List;

public class ServerWindowController {

    DataBaseView dataBaseView;

    ServerWindowView serverWindowView;

    List<ClientController> clientControllerList;


    boolean isServerWorking;

    public ServerWindowController(ServerWindowView serverWindowView, DataBaseView dataBaseView) {
        this.serverWindowView = serverWindowView;
        clientControllerList = new ArrayList<>();
        this.dataBaseView = dataBaseView;
    }


    public void serverUserConnectionInfo(String username) {
        serverWindowView.userConnectionInfo(username);

    }

    public void serverUserDisconnectInfo(String username) {
        serverWindowView.userDisconnectInfo(username);

    }

    public boolean connectUser(ClientController clientController, String name) {
        if (!isServerWorking) {
            return false;
        }
        serverUserConnectionInfo(name);
        clientControllerList.add(clientController);
        return true;
    }


    public void disconnectUser(ClientController clientController) {
        clientControllerList.remove(clientController);
        if (clientController != null) {
            serverUserDisconnectInfo(clientController.getNameFomUser());
            clientController.userDisconnectedByServer();
//            appendToServerTextArea("Пользователь " + clientController.getNameFomUser() + " отключился.");
        }
    }


    private void appendToServerTextArea(String text) {
        serverWindowView.showMessage(text);
    }

    private void answerAll(String text) {
        for (ClientController clientController : clientControllerList) {
            clientController.answerFromServer(text);
        }
    }

    public void message(String s) {
        if (!isServerWorking) {
            return;
        }
        s += "";
        appendToServerTextArea(s);
        answerAll(s);
        writeLog(s);
    }


    private void writeLog(String txt) {
        dataBaseView.writeLog(txt);
    }

    private String sendLogsToServer() {
        return dataBaseView.sendLogsToServer();
    }

    public String getServerTextAreaToServer() {
        return sendLogsToServer();
    }

    private String sendLogsToClient() {
        return dataBaseView.sendLogsToClient();
    }

    public String getServerTextArea() {
        return sendLogsToClient();
    }

}