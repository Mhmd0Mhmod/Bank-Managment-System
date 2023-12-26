package DataBase_Classes;

import java.sql.*;
import java.util.ArrayList;

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

    public Movement(double amount, User currentUser, String receiver_name) {
        this.amount = amount;
        this.sender_name = sender_name;
        this.currentUser = currentUser;
        this.type = "Transaction";
    }

    public Movement(User currentUser) {
        this.currentUser = currentUser;
    }

    public Movement(String type, double amount, String sender_name, String receiver_name) {
        this.amount = amount;
        this.type = type;
        this.sender_name = sender_name;
        this.reciever_name = receiver_name;
    }

    public void deposit(double amount) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String insertMovment = "INSERT INTO movement (movment_amount, movment_type, sender_name, reciever_name)" +
                    "VALUES" +
                    "  ('" + amount + "','" + "Deposit" + "','" + "NONE" + "','" + currentUser.getUsername() + "');";
            double newSenderBalance = currentUser.getBalance() + amount;
            String updateSenderBalance = "UPDATE users SET balance= " + newSenderBalance
                    + " WHERE username = '" + currentUser.getUsername() + "';";
            statement.executeUpdate(insertMovment);
            statement.executeUpdate(updateSenderBalance);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void withdraw(double amount) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            String insertMovment = "INSERT INTO movement (movment_amount, movment_type, sender_name, reciever_name)" +
                    "VALUES" +
                    "  ('" + amount + "','" + "Withdraw" + "','" + currentUser.getUsername() + "','" + "NONE" + "');";
            double newSenderBalance = currentUser.getBalance() - amount;
            String updateSenderBalance = "UPDATE users SET balance= " + newSenderBalance
                    + " WHERE username = '" + currentUser.getUsername() + "';";
            statement.executeUpdate(insertMovment);
            statement.executeUpdate(updateSenderBalance);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void doMovment() throws SQLException {
        Statement statement = connection.createStatement();
        String insertMovment = "INSERT INTO movement (movment_amount, movment_type, sender_name, reciever_name)" +
                "VALUES" +
                "  ('" + amount + "','" + type + "','" + currentUser.getUsername() + "','" + reciever_name + "');";
        statement.executeUpdate(insertMovment);

//        double amountExchanged= api();
        double amountExchanged = 1;
        String getOldReceverBalance = "Select balance FROM users WHERE username = '" + reciever_name + "';";
        ResultSet result = statement.executeQuery(getOldReceverBalance);
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

    }

    public ArrayList<Movement> movments() throws SQLException {
        ArrayList<Movement> userMovments = new ArrayList<>();
        String verifyLogin = "SELECT *  FROM movement WHERE sender_name='" + currentUser.getUsername() + "' OR  reciever_name = '" + currentUser.getUsername() + "' ;";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(verifyLogin);
        while (result.next()) {
            userMovments.add(new Movement(result.getString("movment_type"), result.getDouble("movment_amount"), result.getString("sender_name"), result.getString("reciever_name")));
        }
        return userMovments;
    }

    public double getAmount() {
        return amount;
    }

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
