package com.example.demo10;

import DataBase_Classes.DataBaseConnection;
import DataBase_Classes.Loan;
import DataBase_Classes.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class userDashboardController {
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
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private ComboBox<String> currencyDropList;
    @FXML
    private Label deleteAccountLabel;
    @FXML
    private Label gmailLabel;
    @FXML
    private Label githubLabel;
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
        this.emailTextField.setText(currentUser.getEmail());
        this.passwordPasswordField.setText(currentUser.getPassword());
        this.currencyDropList.setValue(currentUser.getCurrency());
        this.githubLabel.setText(currentUser.getUsername());
        this.gmailLabel.setText(currentUser.getEmail());
    }
    public void deleteAccount(ActionEvent event) throws SQLException, IOException {

    String checkLoan="SELECT COUNT(*) AS COUNT FROM loans WHERE user_id='"+currentUser.getId()+"';";
    String delete="delete from users where id='"+currentUser.getId()+"';";

    Statement statement = connectionDB.createStatement();
    ResultSet result = statement.executeQuery(checkLoan);
    if (result.next()){
        int numOfLoans=result.getInt("COUNT");
        if (numOfLoans>0) deleteAccountLabel.setText("You can't delete your account you still need to pay  " + numOfLoans + " loans");
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


}


