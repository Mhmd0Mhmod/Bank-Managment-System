package com.example.demo10;

import DataBase_Classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;

public class userDashboardController {
    private User currentUser;
    @FXML
    private Text welcomeText;
    @FXML
    private Pane MainPane;

    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField password;
    @FXML
    private TextField email;
    @FXML
    private ComboBox<String> currencyDropList;
    @FXML
    private Label gmailText;
    @FXML
    private Label githubText;
    @FXML
    private Pane layerOne;
    @FXML
    private Pane layerTwo;
    @FXML
    private Pane layerThree;
    @FXML
    private Pane layerFour;
    @FXML
    private Button logOut;
    @FXML
    private Button loanButton;
    public void loanButtonOnAction(ActionEvent event) throws SQLException {
        Loan loan=new Loan(currentUser);
//        loan.requestLoan(5000,"Personal");
        loan.payForLoan(250,2);
    }
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;

    }

    public void setWelcomeText() {
        welcomeText.setText("Welcome " + currentUser.getUsername()+" ,");
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
        setUserData();
    }
    public void doLogout(ActionEvent e) throws IOException {
        new LoadScene("login.fxml",((Node) e.getSource()).getScene()).createScene();
    }
    public void addOneOnAction(ActionEvent event) throws IOException {
        transation(event);
    }
    public void setUserData(){
        this.usernameTextField.setText(currentUser.getUsername());
        System.out.println(currentUser.getEmail());
        this.email.setText(currentUser.getEmail());
        this.password.setText(currentUser.getPassword());
        this.currencyDropList.setValue(currentUser.getCurrency());
        gmailText.setText(currentUser.getEmail());
        githubText.setText(currentUser.getUsername());

    }
}
