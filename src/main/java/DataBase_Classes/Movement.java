package DataBase_Classes;

import java.sql.*;
import java.util.ArrayList;

public class Movement {
    private double amount;
    User currentUser;
    private String type;
    private String sender_name;
    private String receiver_name;
    private String reciever_currency;
    private String sender_currency;
    DataBaseConnection dataBaseConnection = new DataBaseConnection();
    Connection connection = dataBaseConnection.getConnection();

    public Movement(double amount, User currentUser, String receiver_name) {
        this.amount = amount;
        this.sender_name = sender_name;
        this.currentUser=currentUser;
        this.type = "Transaction";
    }

    public Movement(String sender_name) {
        this.sender_name = sender_name;
    }

    public void doMovment() throws SQLException {
        Statement statement = connection.createStatement();
        String insertMovment = "INSERT INTO movement (movment_amount, movment_type, sender_name, reciever_name)" +
                "VALUES" +
                "  ('" + amount + "','" + type + "','" + currentUser.getUsername() + "','" + receiver_name + "');";
        statement.executeUpdate(insertMovment);

//        double amountExchanged= api();
        double amountExchanged=1;
        String getOldReceverBalance= "Select balance FROM users WHERE username = '"+ receiver_name + "';";
        ResultSet result=statement.executeQuery(getOldReceverBalance);
        double oldReceiverBalance=0;
        if(result.next()) {
            oldReceiverBalance =result.getDouble("balance");
        }
            double newReceiverBalance= oldReceiverBalance+amountExchanged;
            double newSenderBalance = currentUser.getBalance() - amount;
            String updateSenderBalance = "UPDATE users SET Balance= '" + newSenderBalance
                    + "' WHERE username='" + currentUser.getUsername() +"';";
            String updateReceiverBalance = "UPDATE users SET Balance= '" + newReceiverBalance
                + "' WHERE username='" + receiver_name+"';";
            statement.executeUpdate(updateReceiverBalance);
            statement.executeUpdate(updateSenderBalance);

    }

    public ArrayList<ArrayList<String>> movments() throws SQLException {
        ArrayList<ArrayList<String>> userMovments = null;
        String verifyLogin = "SELECT *  FROM movement WHERE sender_name='" + sender_name + "';";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(verifyLogin);
        while (result.next()){
            ArrayList<String> row=new ArrayList<>();
            row.add(String.valueOf(result.getDouble("movment_amount")));
            row.add(result.getString("type"));
            row.add(result.getString("sender_name"));
            row.add(result.getString("receiver_name"));
            userMovments.add(row);
        }
        return userMovments;
    }



}
