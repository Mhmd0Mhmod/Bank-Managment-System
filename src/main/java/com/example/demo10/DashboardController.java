package com.example.demo10;

import DataBase_Classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class DashboardController {
    private User currentUser;
    @FXML
    private Text welcomeText;
    @FXML
    private Pane MainPane;

    @FXML
    private Pane layerOne;
    @FXML
    private Pane layerTwo;
    @FXML
    private Pane layerThree;
    @FXML
    private Pane layerFour;
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setWelcomeText() {
        welcomeText.setText("Welcome " + currentUser.getUsername());
    }
    public void dashboard(ActionEvent event){
        layerOne.toFront();
    }
    public void transation(ActionEvent event){
        layerTwo.toFront();
    }
    public void changeCurrency(ActionEvent e){
        layerThree.toFront();
    }
    public  void accountData(ActionEvent e){
        layerFour.toFront();
    }
}
