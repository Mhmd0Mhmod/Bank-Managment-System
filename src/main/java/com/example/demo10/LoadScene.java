package com.example.demo10;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LoadScene {
    private String resource;
    private ActionEvent event;
    LoadScene(String resource, ActionEvent event){
        this.resource=resource;
        this.event=event;
    }
    public void createScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(resource));
        Scene signUpScene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Set and show the secondary scene on the current stage
        stage.setScene(signUpScene);
        stage.show();
    }

}
