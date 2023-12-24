package com.example.demo10;

import DataBase_Classes.LoginValidation;
import DataBase_Classes.User;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class loginController {


    @FXML
    private Button cancelButton;
    @FXML
    private Label loginMessage;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private Button signUpButton;

    public void cancelButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void loginButtonOnAction(ActionEvent event) throws IOException {
        if (passwordField.getText().isBlank() || usernameTextField.getText().isBlank()) {
            loginMessage.setText("Please, Enter both email and password");
        } else {
            validateLogin(event);
        }
    }

    public void signUpButtonOnAction(ActionEvent event) throws IOException {
        new LoadScene("signup.fxml",event).createScene();
    }

    public void validateLogin(ActionEvent event) throws IOException {
        LoginValidation loginValidation = new LoginValidation(usernameTextField.getText(), passwordField.getText());
        User user = loginValidation.checkLogin();
        if (user!=null) {
            loginMessage.setText("Login Successful");
            System.out.println(((Node)event.getSource()).getScene());
//            LoadScene loadScene=new LoadScene("dashboard.fxml",event);
//            try {
//                loadScene.createScene();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
        } else {
            loginMessage.setText("Invalid Login. Please try again!");
        }
    }

}