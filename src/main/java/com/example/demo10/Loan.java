package com.example.demo10;

import DataBase_Classes.DataBaseConnection;
import DataBase_Classes.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Loan {
    private final DataBaseConnection Connection = new DataBaseConnection();
    private final java.sql.Connection connectionDB = Connection.getConnection();

    private int userId=0;
//    private User currentUser;
    private String username;
    private double amount;
    private String type;
    private int loanID;

    public Loan( String username,int amount, String type) {
        this.username = username;
        this.amount = amount;
        this.type = type;
    }

    public void getUserID(String username) throws SQLException {
        Statement statement = connectionDB.createStatement();
        String useridSQL= "SELECT COUNT(0) AS count , id from users where username='"+ username +"';";
        ResultSet result = statement.executeQuery(useridSQL);
        result.next();
        userId=result.getInt("id");
    }

    public void insertLoan() throws SQLException {
        Statement statement = connectionDB.createStatement();
        getUserID(username);
            String loan = "INSERT INTO `loans` (`user_id`, `amount`, `type`, `date_of_approval`, `date_of_expiry`)" +
                    "VALUES" +
                    "('" + userId + "','" + amount + "','" + type + "','" + LocalDateTime.now() + "','" + LocalDate.now().plusYears(5) + "')";
            int rowsAffected = statement.executeUpdate(loan);
            if(rowsAffected==0) System.out.println("No enough balance");
            else {
                System.out.println("Success");
                // currentUser.setBalance(getBalance()+amount);
            }
    }
    public void payForLoan(double amountPaid) throws SQLException {
        Statement statement = connectionDB.createStatement();
//        double balance=currentUser.getBalance();
          double balance=1;
        if (balance>=amountPaid){
            //The user pays for all the loan
            if (amountPaid>=amount) {
                String delete = "DELETE FROM loans WHERE loan_id= " + loanID + ";";
                statement.executeUpdate(delete);
            }
            //The user pays for a part of the loan
            else {
                double newAmount=amount-amountPaid;
                String update="UPDATE loans SET amount = "+ newAmount + "WHERE loan_id=" + loanID + ";";
                statement.executeUpdate(update);
            }

        }
        else {
            System.out.println("No enough balance");
        }
    }
}
