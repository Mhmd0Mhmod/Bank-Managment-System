package com.example.demo10;

import DataBase_Classes.DataBaseConnection;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

public class AlertCreation {

    private DataBaseConnection Connection = new DataBaseConnection();
    private java.sql.Connection connectionDB = Connection.getConnection();
    private String title = null;
    private String headerText = null;
    private String contentText = null;

    public AlertCreation(String title, String headerText, String contentText) {
        this.title = title;
        this.headerText = headerText;
        this.contentText = contentText;
    }

    public boolean confirmation(String query, ActionEvent event) {
        boolean okayButton=false;
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle(title);
        confirmAlert.setHeaderText(headerText);
        confirmAlert.setContentText(contentText);
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    Statement statement2 = connectionDB.createStatement();
                    statement2.executeUpdate(query);
                    new LoadScene("login.fxml", ((Node) event.getSource()).getScene()).createScene();
                } catch (SQLException | IOException e) {
                    throw new RuntimeException(e);
                }
            }


        });
        return okayButton;

    }

    public void information(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
    public void error() {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(headerText);
        errorAlert.setContentText(contentText);
        errorAlert.showAndWait();
    }
}