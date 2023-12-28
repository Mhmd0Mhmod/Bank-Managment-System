package DataBase_Classes;

import com.example.demo10.userDashboardController;

import java.io.IOException;
import java.sql.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;

public class Movement {
    private double amount;
    User currentUser;
    private String type;
    private String sender_name;

    private String reciever_name;
    private String reciever_currency;
    private String sender_currency;
    DataBaseConnection dataBaseConnection = new DataBaseConnection();
    Connection connection = dataBaseConnection.getConnection();
    public String formatCurrencyWithCode(double amount, String currencyCode) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        Currency currency = Currency.getInstance(currencyCode);
        numberFormat.setCurrency(currency);
        return numberFormat.format(amount);
    }
    public String formattedAmount;

    public Movement(User currentUser, double amount, String receiver_name) {
        this.amount = amount;
        this.currentUser = currentUser;
        this.reciever_name = receiver_name;
        this.type = "Transaction";
        this.formattedAmount=formatCurrencyWithCode(amount,currentUser.getCurrency());
    }

    public Movement(double amount, User currentUser) {
        this.amount = amount;
        this.currentUser = currentUser;
        this.formattedAmount=formatCurrencyWithCode(amount,currentUser.getCurrency());
    }

    public Movement(User currentUser) {
        this.currentUser = currentUser;
        this.formattedAmount=formatCurrencyWithCode(amount,currentUser.getCurrency());
    }

    public Movement(String type, double amount, String sender_name, String receiver_name) {
        this.amount = amount;
        this.type = type;
        this.sender_name = sender_name;
        this.reciever_name = receiver_name;
    }
    public Movement(String type, String amount, String sender_name, String receiver_name) {
        this.formattedAmount = amount;
        this.type = type;
        this.sender_name = sender_name;
        this.reciever_name = receiver_name;
    }

    public Double transferMoney() throws SQLException, IOException {
        Statement statement = connection.createStatement();
        String getCurrencySQL = "SELECT currency FROM users WHERE username='" + reciever_name + "';";
        ResultSet result = statement.executeQuery(getCurrencySQL);
        String receiverCurrency = "USD";
        if (result.next()) receiverCurrency = result.getString("currency");
        result.close();
        currencyChangeAPI api = new currencyChangeAPI();
        Double amountExchanged = api.convertCurrency(currentUser.getCurrency(), receiverCurrency, amount);
        String getOldReceverBalance = "Select balance FROM users WHERE username = '" + reciever_name + "';";
        result = statement.executeQuery(getOldReceverBalance);
        double oldReceiverBalance = 0;
        if (result.next()) {
            oldReceiverBalance = result.getDouble("balance");
        }
        double newReceiverBalance = oldReceiverBalance + amountExchanged;
        double newSenderBalance = currentUser.getBalance() - amount;
        String updateSenderBalance = "UPDATE users SET Balance= '" + newSenderBalance
                + "' WHERE username='" + currentUser.getUsername() + "';";
        String updateReceiverBalance = "UPDATE users SET Balance= '" + newReceiverBalance
                + "' WHERE username='" + reciever_name + "';";
        statement.executeUpdate(updateReceiverBalance);
        statement.executeUpdate(updateSenderBalance);
        String move = "INSERT INTO movement (movment_amount, movment_type, sender_name, reciever_name)" +
                "VALUES" + "  ('" + formattedAmount + "','" + type + "','" + currentUser.getUsername() + "','" + reciever_name + "');";
        statement.executeUpdate(move);
        return amountExchanged;
    }

    public void withdraw(double amount) {
        Statement statement = null;
        formattedAmount=formatCurrencyWithCode(amount,currentUser.getCurrency());
        try {
            statement = connection.createStatement();
            String insertMovment = "INSERT INTO movement (movment_amount, movment_type, sender_name, reciever_name)" +
                    "VALUES" +
                    "  ('" + formattedAmount + "','" + "Withdraw" + "','" + currentUser.getUsername() + "','" + "NONE" + "');";
            double newSenderBalance = currentUser.getBalance() - amount;
            String updateSenderBalance = "UPDATE users SET balance= " + newSenderBalance
                    + " WHERE username = '" + currentUser.getUsername() + "';";
            statement.executeUpdate(updateSenderBalance);
            // put it in the table
            statement.executeUpdate(insertMovment);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deposit(double amount) {
        formattedAmount=formatCurrencyWithCode(amount,currentUser.getCurrency());
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String insertMovment = "INSERT INTO movement (movment_amount, movment_type, sender_name, reciever_name)" +
                    "VALUES" +
                    "  ('" + formattedAmount + "','" + "Deposit" + "','" + "NONE" + "','" + currentUser.getUsername() + "');";
            double newSenderBalance = currentUser.getBalance() + amount;
            String updateSenderBalance = "UPDATE users SET balance= " + newSenderBalance
                    + " WHERE username = '" + currentUser.getUsername() + "';";

            statement.executeUpdate(insertMovment);
            statement.executeUpdate(updateSenderBalance);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ArrayList<Movement> movments() throws SQLException {
        ArrayList<Movement> userMovments = new ArrayList<>();
        String verifyLogin = "SELECT *  FROM movement WHERE sender_name='" + currentUser.getUsername() + "' OR  reciever_name = '" + currentUser.getUsername() + "' ;";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(verifyLogin);
        while (result.next()) {
            userMovments.add(new Movement(result.getString("movment_type"), result.getString("movment_amount"), result.getString("sender_name"), result.getString("reciever_name")));
        }
        return userMovments;
    }

    public void loanRequest() throws SQLException {
        this.type = "Loan Request";
        Statement statement = connection.createStatement();
        String insertMovment = "INSERT INTO movement (movment_amount, movment_type, sender_name, reciever_name)" +
                "VALUES" +
                "  ('" + formattedAmount + "','" + type + "','" + "Bank" + "','" + currentUser.getUsername() + "');";
        statement.executeUpdate(insertMovment);
    }

    public void payForLoan() throws SQLException {
        String formattedAmount=formatCurrencyWithCode(amount,currentUser.getCurrency());
        this.type = "Loan Pay";
        Statement statement = connection.createStatement();
        String insertMovment = "INSERT INTO movement (movment_amount, movment_type, sender_name, reciever_name)" +
                "VALUES" +
                "  ('" + formattedAmount + "','" + type + "','" + currentUser.getUsername() + "','" + "Bank" + "');";
        statement.executeUpdate(insertMovment);
    }

    public String getAmount() {return formattedAmount;   }

    public String getType() {
        return type;
    }

    public String getSender_name() {
        return sender_name;
    }

    public String getReciever_name() {
        return reciever_name;
    }


}
