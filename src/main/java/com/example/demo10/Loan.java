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

    private User currentUser;

    public Loan( User currentUser) {
        this.currentUser=currentUser;
    }


    public void requestLoan(double amountRequested,String type) throws SQLException {
        Statement statement = connectionDB.createStatement();
            String loan = "INSERT INTO `loans` (user_id,amount,paid,remaining,typeOfLoan,date_of_approval, date_of_expiry)" +
                    "VALUES" +
                    "('" + currentUser.getId() + "','" + amountRequested + "','" + 0 + "','"
                    + amountRequested + "','" +  type + "','" + LocalDateTime.now() + "','" + LocalDate.now().plusYears(5) + "')";
            int rowsAffected = statement.executeUpdate(loan);
            if(rowsAffected==0) System.out.println("No enough balance");
            else {
                System.out.println("Success");
                 currentUser.setBalance(currentUser.getBalance()+amountRequested);
            }
    }
    public void payForLoan(double amountPaid,int loanID) throws SQLException {
        Statement statement = connectionDB.createStatement();
        String value="SELECT * FROM loans WHERE loan_id='" + loanID + "';";
        ResultSet result = statement.executeQuery(value);
        double remainingOfLoan=0;
        double paidOfLoan=0;
        double amountOfLoan=0;
        if (result.next()){
            amountOfLoan =  result.getDouble("amount");
            remainingOfLoan =  result.getDouble("remaining");
            paidOfLoan =  result.getDouble("paid");
        }

        double balance=currentUser.getBalance();
        System.out.println(balance);
        System.out.println(amountOfLoan);
        if (balance>=amountPaid){
            //The user pays for all the loan
            double pay=Math.min(amountPaid,amountOfLoan);
            double newRemaining=remainingOfLoan-pay;
            double newPaid=paidOfLoan+pay;
            double newBalance=balance-pay;
            currentUser.setBalance(balance-pay);
                String updateRemainingAndPaid="UPDATE loans SET remaining = "+ newRemaining + ",paid="+ newPaid+ "WHERE loan_id=" + loanID + ";";
                String updateBalance="UPDATE users SET balance = "+ newBalance + "WHERE id=" + currentUser.getId();
                statement.executeUpdate(updateRemainingAndPaid);
                statement.executeUpdate(updateBalance);

            }
        else {
            System.out.println("No enough balance");
        }
    }
}
