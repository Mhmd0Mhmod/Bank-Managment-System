package com.example.demo10;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoadScene {
    private String resource;
    private Scene scene;
    LoadScene(String resource, Scene scene){
        this.resource=resource;
        this.scene=scene;
    }
    public void createScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(resource));
        Scene signUpScene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) scene.getWindow();
        // Set and show the secondary scene on the current stage
        stage.setScene(signUpScene);
        stage.show();
    }

}
