package com.example.demo10;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;



public class signUpController {
    ObservableList<String> currencies= FXCollections.observableArrayList("USD","EGP","EUR","KWD");
    @FXML
    private ComboBox<String> currencyComboBox;
    @FXML
    public void initialize(){
        currencyComboBox.setItems(currencies);

    }
}
