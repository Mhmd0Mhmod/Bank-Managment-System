package com.example.demo10;

import DataBase_Classes.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class LoadScene {
    private String resource;
    private Scene scene;
    private User user;

    LoadScene(String resource, Scene scene) {
        this.resource = resource;
        this.scene = scene;
    }

    LoadScene(String resource, Scene scene, User user) {
        this.resource = resource;
        this.scene = scene;
        this.user = user;
    }

    public void createScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(resource));
        Scene signUpScene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) scene.getWindow();
        if (resource.equals("dashboard.fxml")) {
            userDashboardController dh = fxmlLoader.getController();
            dh.setCurrentUser(user);
            dh.setWelcomeText();
            try {
                dh.showMoments();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        // Set and show the secondary scene on the current stage
        stage.setScene(signUpScene);
        stage.show();
    }

}