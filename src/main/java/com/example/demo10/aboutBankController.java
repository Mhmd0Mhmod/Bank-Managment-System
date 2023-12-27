package com.example.demo10;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class aboutBankController {
    public void returnToLogin(ActionEvent event) throws IOException {
        new LoadScene("login.fxml", ((Node) event.getSource()).getScene()).createScene();
    }
}
