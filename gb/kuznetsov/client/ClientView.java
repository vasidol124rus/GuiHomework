package ru.gb.kuznetsov.client;

public interface ClientView {
    /**
     * Метод для отображения сообщения в GUI
     * @param message текст сообщения
     */
    void showMessage(String message);

    /**
     * Метод отключения от сервера со стороны сервера
     */
    void disconnectedByServer();

    /*
     *  //TODO - доработать метод отключения по нажатию нп крестик окна пользователя ClientGUI + решено
     */
    void userDisconnected();

    String getUserName();
}
