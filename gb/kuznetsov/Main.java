package ru.gb.kuznetsov;

import ru.gb.kuznetsov.DataBase.DataBase;
import ru.gb.kuznetsov.client.ClientController;
import ru.gb.kuznetsov.client.ClientGUI;
import ru.gb.kuznetsov.server.ServerWindowController;
import ru.gb.kuznetsov.server.ServerWindowGui;

public class Main {
    public static void main(String[] args) {


        DataBase dataBase = new DataBase();

        ServerWindowGui serverWindowGUI = new ServerWindowGui();
        ServerWindowController serverWindowController = new ServerWindowController(serverWindowGUI, dataBase);
        serverWindowGUI.setServerWindowController(serverWindowController);


        ClientGUI clientGUI1 = new ClientGUI();
        ClientController clientController1 = new ClientController(clientGUI1, serverWindowController);
        clientGUI1.setClientController(clientController1);

        ClientGUI clientGUI2 = new ClientGUI();
        ClientController clientController2 = new ClientController(clientGUI2, serverWindowController);
        clientGUI2.setClientController(clientController2);


    }


}