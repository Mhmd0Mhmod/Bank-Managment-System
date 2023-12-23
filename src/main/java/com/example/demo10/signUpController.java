package com.example.demo10;

import DataBase_Classes.InsertUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;


public class signUpController {
    ObservableList<String> currencies = FXCollections.observableArrayList("USD", "EGP", "EUR", "KWD");
    @FXML
    private ComboBox<String> currencyComboBox;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private ToggleGroup gender;
    @FXML
    private RadioButton privaryRadioButton;

    public void initialize() {
        currencyComboBox.setItems(currencies);
    }

    public void registerButtonOnAction(ActionEvent event) throws SQLException {
        System.out.println(usernameTextField.getText());
        RadioButton radioButton = (RadioButton) gender.getSelectedToggle();
        InsertUser insertUser = new InsertUser(usernameTextField.getText(), emailTextField.getText(), passwordTextField.getText()
                , currencyComboBox.getValue(), radioButton.getText());
        insertUser.checkValidation();
    }

}
