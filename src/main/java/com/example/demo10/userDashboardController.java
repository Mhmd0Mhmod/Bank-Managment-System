package com.example.demo10;

import DataBase_Classes.Loan;
import DataBase_Classes.Movement;
import DataBase_Classes.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class userDashboardController implements Initializable {
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
    @FXML
    private Button logOut;


    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void setWelcomeText() {
        welcomeText.setText("Welcome " + currentUser.getUsername() + " ,");
    }

    public void dashboard(ActionEvent event) throws SQLException {
        layerOne.toFront();
        movementTable.refresh();
    }

    public void transation(ActionEvent event) {
        layerTwo.toFront();
    }

    public void changeCurrency(ActionEvent e) {
        layerThree.toFront();
    }

    public void accountData(ActionEvent e) {
        layerFour.toFront();
    }

    public void doLogout(ActionEvent e) throws IOException {
        new LoadScene("login.fxml", ((Node) e.getSource()).getScene()).createScene();
    }

    public void addOneOnAction(ActionEvent event) throws IOException {
        transation(event);
    }


    // DashBoard UI
    @FXML
    private TableView<Movement> movementTable;
    @FXML
    private TableColumn<Movement, String> type;
    @FXML
    private TableColumn<Movement, Double> amount;
    @FXML
    private TableColumn<Movement, String> sender_name;
    @FXML
    private TableColumn<Movement, String> reciever_name;

    public void initializeTable() {
        type.setCellValueFactory(new PropertyValueFactory<Movement, String>("type"));
        amount.setCellValueFactory(new PropertyValueFactory<Movement, Double>("amount"));
        sender_name.setCellValueFactory(new PropertyValueFactory<Movement, String>("sender_name"));
        reciever_name.setCellValueFactory(new PropertyValueFactory<Movement, String>("reciever_name"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeTable();
    }

    public void showMoments() throws SQLException {
        ArrayList<Movement> movments = new Movement(currentUser).movments();
        movementTable.getItems().addAll(movments);
    }


    //Request Loan
    @FXML
    private TextField loanRequestAmount;
    @FXML
    private TextField loanType;
    @FXML
    private Text notEnoughBalance;
    @FXML
    private Button loanRequest;
    public void RequestLoan(ActionEvent event) throws SQLException {
        if (!loanRequestAmount.getText().isBlank() && !loanType.getText().isBlank()) {
            Loan loan = new Loan(currentUser);
            if (loan.requestLoan(Double.parseDouble(loanRequestAmount.getText()), loanType.getText())) {
                currentUser.refresh();
            } else {
                notEnoughBalance.setText("NOT Enough Balance");
            }
        } else {
            notEnoughBalance.setText("Please Filed Requirments");
        }
    }

    // PayForLoan
    @FXML
    private Text noID;
    @FXML
    private TextField loanApplyID;
    @FXML
    private TextField loanApplyAmount;

    public void payforLoan(ActionEvent event) throws SQLException {
        if (!loanApplyID.getText().isBlank() && !loanApplyAmount.getText().isBlank())
            noID.setText(new Loan(currentUser).payForLoan(Double.parseDouble(loanApplyAmount.getText()), Integer.parseInt(loanApplyID.getText())));
        else
            noID.setText("Please Fill Required Filed");
    }

    //Deposit
    @FXML
    private TextField depositAmount;

    public void deposit(ActionEvent event) {
        if (!depositAmount.getText().isBlank()) {
            Movement movement = new Movement(currentUser);
            movement.deposit(Double.parseDouble(depositAmount.getText()));
            currentUser.refresh();
            depositAmount.setText("");
        }
    }

    //Withdraw
    @FXML
    private TextField withdrawAmount;
    @FXML
    private Text checkBalance;

    public void withdraw(ActionEvent event) {
        if (!withdrawAmount.getText().isBlank()) {
            double withdrawAmountVal = Double.parseDouble(withdrawAmount.getText());
            System.out.println(currentUser.getBalance() + " " + withdrawAmountVal);
            if (currentUser.getBalance() >= withdrawAmountVal) {
                Movement move = new Movement(currentUser);
                move.withdraw(withdrawAmountVal);
                currentUser.refresh();
            } else {
                checkBalance.setText("NO Enough Balance");
            }
            withdrawAmount.setText("");
        }
    }
}
