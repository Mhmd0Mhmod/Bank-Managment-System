package com.example.demo10;

import DataBase_Classes.DataBaseConnection;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

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

    public void confirmation(String query) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle(title);
        confirmAlert.setHeaderText(headerText);
        confirmAlert.setContentText(contentText);
        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    Statement statement2 = connectionDB.createStatement();
                    statement2.executeUpdate(query);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void error() {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(title);
        errorAlert.setHeaderText(headerText);
        errorAlert.setContentText(contentText);
        errorAlert.showAndWait();
    }
}
