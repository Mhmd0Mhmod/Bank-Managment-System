package com.example.demo10;

import DataBase_Classes.*;
import javafx.beans.binding.Bindings;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;
import java.util.ResourceBundle;

public class userDashboardController implements Initializable{
    private User currentUser;
    @FXML
    private Text welcomeText;
    @FXML
    private Pane MainPane;
    private DataBaseConnection Connection = new DataBaseConnection();
    private java.sql.Connection connectionDB = Connection.getConnection();
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
    public  String formatCurrencyWithCode(double amount, String currencyCode) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        Currency currency = Currency.getInstance(currencyCode);
        numberFormat.setCurrency(currency);
        return numberFormat.format(amount);
    }
    public void setBalance(){
        balanceLabel.setText(formatCurrencyWithCode(currentUser.getBalance() , currentUser.getCurrency()));
    }
    @FXML
    private Label balanceLabel;
    public void dashboard(ActionEvent event){
        layerOne.toFront();
        balanceLabel.setText(formatCurrencyWithCode(currentUser.getBalance() , currentUser.getCurrency()));

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
    @FXML
    private Label gmailLabel;
    @FXML
    private Label githubLabel;
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private ComboBox<String> currencyDropList;
    public void setUserData(){
        this.usernameTextField.setText(currentUser.getUsername());
        this.emailTextField.setText(currentUser.getEmail());
        this.passwordPasswordField.setText(currentUser.getPassword());
        this.currencyDropList.setValue(currentUser.getCurrency());
        this.githubLabel.setText(currentUser.getUsername());
        this.gmailLabel.setText(currentUser.getEmail());
    }
    // Dashboard GUI Table
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

    @FXML
    private Label deleteAccountLabel;
    public void deleteAccount(ActionEvent event) throws SQLException, IOException {

    String checkLoan="SELECT COUNT(*) AS COUNT FROM loans WHERE user_id='"+currentUser.getId()+"' AND remaining!='"+ 0+ "';";
    String delete="delete from users where id='"+currentUser.getId()+"';";

    Statement statement = connectionDB.createStatement();
    ResultSet result = statement.executeQuery(checkLoan);
    if (result.next()){
        int numOfLoans=result.getInt("COUNT");
        if (numOfLoans>0) deleteAccountLabel.setText("You can't delete your account you still need to pay " + numOfLoans + " loans");
        else {

            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmation Dialog");
            confirmAlert.setHeaderText("Confirmation Needed");
            confirmAlert.setContentText("Are you sure to delete your account?");
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Statement statement2 = null;
                    try {
                        statement2 = connectionDB.createStatement();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        statement2.executeUpdate(delete);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            new LoadScene("login.fxml", ((Node) event.getSource()).getScene()).createScene();

        }
        }
    }
    @FXML
    private Button updateButton;
    public void updatePassword() throws SQLException {
    if(passwordPasswordField.getText().equals(currentUser.getPassword())){
        deleteAccountLabel.setText("You didn't change your password");
    }
    else {
        deleteAccountLabel.setText("Your password has been changed successfully");
        String update="update users set hashed_password='"+passwordPasswordField.getText()+"' WHERE id='"+currentUser.getId()+"';";
        Statement statement = connectionDB.createStatement();
        statement.executeUpdate(update);
        currentUser.refresh();
    }
    }
    @FXML
    private TextField receiverUsername;
    @FXML
    private TextField transferAmount;
    public void transferMoney() throws IOException, SQLException {
        Movement movment=new Movement(currentUser,Double.parseDouble(transferAmount.getText()),receiverUsername.getText());
        movment.transferMoney();
    }

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




}


