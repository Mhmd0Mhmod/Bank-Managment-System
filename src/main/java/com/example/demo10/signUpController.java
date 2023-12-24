package com.example.demo10;

import DataBase_Classes.InsertUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;



public class signUpController {
    ObservableList<String> currencies= FXCollections.observableArrayList("USD","EGP","EUR","KWD");
    @FXML
    private ComboBox<String> currencyComboBox;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordTextField;
    @FXML
    private RadioButton maleRadioButton;
    @FXML
    private RadioButton femaleRadioButton;
    private ToggleGroup gender;
    @FXML
    private RadioButton privaryRadioButton;
    @FXML
    private Button registerButton;
    public void initialize(){
        currencyComboBox.setItems(currencies);
    }
    public void registerButtonOnAction(ActionEvent event){
//        InsertUser insertUser = new InsertUser(usernameTextField.getText(),emailTextField.getText(),passwordTextField.getText()
//        ,currencyComboBox.getValue(),);
        System.out.println(gender.getSelectedToggle());
    }

}
