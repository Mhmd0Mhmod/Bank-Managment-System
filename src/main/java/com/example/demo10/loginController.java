package com.example.demo10;

import DataBase_Classes.LoginValidation;
import DataBase_Classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
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





    public void loginButtonOnAction(ActionEvent e) throws IOException {
        if (passwordField.getText().isBlank() || usernameTextField.getText().isBlank()) {
            loginMessage.setText("Please, Enter both email and password");
        } else {
            validateLogin(e);
        }
    }

    public void signUpButtonOnAction(ActionEvent event) throws IOException {
        new LoadScene("signup.fxml", ((Node) event.getSource()).getScene()).createScene();
    }



    public void aboutTheBankButtonOnAction(ActionEvent event) throws IOException {
        new LoadScene("aboutBank.fxml", ((Node) event.getSource()).getScene()).createScene();
    }

    public void validateLogin(ActionEvent event) throws IOException {
        LoginValidation loginValidation = new LoginValidation(usernameTextField.getText(), passwordField.getText());
        User user = loginValidation.checkLogin();
        if (user != null) {
            new LoadScene("dashboard.fxml",((Node) event.getSource()).getScene(),user).createScene();
        } else {
            loginMessage.setText("Invalid Data. Please try again!");

        }
    }
    @FXML
    private Button exitButton;
    @FXML
    public void handleMouseEnter() {
        exitButton.setStyle("-fx-background-color: RED;");
    }
    @FXML
    public void handleMouseExit() {
        exitButton.setStyle("-fx-background-color: transparent;"); // Reset to default or another color
    }
    @FXML
    public void cancelButtonOnAction() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }


}