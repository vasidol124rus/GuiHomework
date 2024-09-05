package ru.gb.kuznetsov.DataBase;

import java.io.*;

public class DataBase implements DataBaseView {


    private static final String LOG_FILE_PATH = "src/ru/gb/kuznetsov/DataBase/log.txt";

    @Override
    public void writeLog(String txt) {
        try (FileWriter fw = new FileWriter(LOG_FILE_PATH, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(txt);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String sendLogsToServer() {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader fr = new FileReader(LOG_FILE_PATH);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line + "\n");
                showMessageDataBase(line);

            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String sendLogsToClient() {
        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader fr = new FileReader(LOG_FILE_PATH);
             BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public String showMessageDataBase(String message) {
        return message;
    }
}
