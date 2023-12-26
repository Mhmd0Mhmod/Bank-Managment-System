package DataBase_Classes;

import java.io.IOException;
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

    public Movement( User currentUser, double amount,String receiver_name) {
        this.amount = amount;
        this.currentUser=currentUser;
        this.receiver_name=receiver_name;
        this.type = "Transaction";
    }


    public void doMovment() throws SQLException {
        Statement statement = connection.createStatement();
        String insertMovment = "INSERT INTO movement (movment_amount, movment_type, sender_name, reciever_name)" +
                "VALUES" +
                "  ('" + amount + "','" + type + "','" + currentUser.getUsername() + "','" + receiver_name + "');";
        statement.executeUpdate(insertMovment);
    }
    public void transferMoney() throws SQLException, IOException {
        Statement statement = connection.createStatement();
        String getCurrencySQL="SELECT currency FROM users WHERE username='"+receiver_name+"';";
        ResultSet result=statement.executeQuery(getCurrencySQL);
        String receiverCurrency="USD";
        if(result.next())  receiverCurrency=result.getString("currency");
        // closing the query to execute another one
        result.close();
        currencyChangeAPI api=new currencyChangeAPI();
        System.out.println(currentUser.getCurrency());
        Double amountExchanged=api.convertCurrency(currentUser.getCurrency(),receiverCurrency,amount);
        String getOldReceverBalance= "Select balance FROM users WHERE username = '"+ receiver_name + "';";
        result=statement.executeQuery(getOldReceverBalance);
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

//        System.out.println(amountChanged);
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
