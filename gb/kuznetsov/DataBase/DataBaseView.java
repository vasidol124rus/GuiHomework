package ru.gb.kuznetsov.DataBase;

public interface DataBaseView {

    String showMessageDataBase(String message);

    void writeLog(String txt);

    String sendLogsToServer();

    String sendLogsToClient();
}
