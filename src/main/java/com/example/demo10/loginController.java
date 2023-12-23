package com.example.demo10;

import DataBase_Classes.DataBaseConnection;
import DataBase_Classes.LoginValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;

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

    public void loginButtonOnAction(ActionEvent event) {
        if (passwordField.getText().isBlank() || usernameTextField.getText().isBlank()) {
            loginMessage.setText("Please, Enter both email and password");
        } else {
            validateLogin();
        }
    }

    public void signUpButtonOnAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("signup.fxml"));
        Scene signUpScene = new Scene(fxmlLoader.load(), 591, 362);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Set and show the secondary scene on the current stage
        stage.setScene(signUpScene);
        stage.show();
    }

    public void validateLogin() {
        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();
        LoginValidation loginValidation = new LoginValidation(usernameTextField.getText(), passwordField.getText());
        if (loginValidation.checkLogin()) {
            loginMessage.setText("Login Successful");
        } else {
            loginMessage.setText("Invalid Login. Please try again!");
        }
    }

}