package controllers;

import client.Client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.io.IOException;

import java.util.Collections;


public class MainController {


    @FXML
    private ListView<String> ListView;

    @FXML
    private ListView<String> ListViewSecond;

    @FXML
    private Button send;

    @FXML
    void sendFiles(String[] file) throws IOException, InterruptedException {
        Client.sendMessage();
        for (int i = 0; i < file.length; i++) {
            if (file[i].equals(client.Client.getFile())) {
                file[i] = client.Client.getFile();;
            }
        }
        Collections.addAll(ListViewSecond.getItems(), file);

    }

    public void appendFile(String[] file) {
        for (int i = 0; i < file.length; i++) {
            if (file[i].equals(client.Client.getFile())) {
                file[i] = client.Client.getFile();
            }
        }
        Collections.addAll(ListView.getItems(), file);

    }





}

