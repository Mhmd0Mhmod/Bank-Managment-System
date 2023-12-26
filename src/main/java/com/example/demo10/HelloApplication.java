package com.example.demo10;

import DataBase_Classes.User;
import DataBase_Classes.currencyChangeAPI;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import com.google.gson.JsonParser;
import javafx.stage.StageStyle;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("splashScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
//        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) throws IOException {
        launch();
        currencyChangeAPI api=new currencyChangeAPI();
//        System.out.println(api.sendHttpGETRequest("USD","EGP",50));
    }
}