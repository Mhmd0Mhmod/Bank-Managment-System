package DataBase_Classes;

import DataBase_Classes.DataBaseConnection;
import DataBase_Classes.User;
import com.example.demo10.AlertCreation;

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

    public Loan(User currentUser) {
        this.currentUser = currentUser;
    }


    public boolean requestLoan(double amountRequested, String type) throws SQLException {
        if (amountRequested > currentUser.getBalance() * 10) return false;
        Statement statement = connectionDB.createStatement();
        String loan = "INSERT INTO `loans` (user_id,amount,paid,remaining,typeOfLoan,date_of_approval, date_of_expiry)" +
                "VALUES" +
                "('" + currentUser.getId() + "','" + amountRequested + "','" + 0 + "','"
                + amountRequested + "','" + type + "','" + LocalDateTime.now() + "','" + LocalDate.now().plusYears(5) + "')";
        int rowsAffected = statement.executeUpdate(loan);
        if (rowsAffected == 0) return false;
        else {
            String updateUser = "UPDATE users SET balance =" + (currentUser.getBalance() + amountRequested) + "WHERE id =" + currentUser.getId() + ";";
            new Movement(amountRequested, currentUser).loanRequest();
            statement.executeUpdate(updateUser);
            return true;
        }
    }

    public String payForLoan(double amountPaid, int loanID) throws SQLException {
        Statement statement = connectionDB.createStatement();
        String value = "SELECT * FROM loans WHERE loan_id='" + loanID + "';";
        ResultSet result = statement.executeQuery(value);
        double remainingOfLoan = 0;
        double paidOfLoan = 0;
        double amountOfLoan = 0;
        int user_id = 0;
        if (result.next()) {
            user_id = result.getInt("user_id");
            if (user_id != currentUser.getId()) return "These Loan Not Yours";
            amountOfLoan = result.getDouble("amount");
            remainingOfLoan = result.getDouble("remaining");
            paidOfLoan = result.getDouble("paid");
            double balance = currentUser.getBalance();
            System.out.println(balance);
            System.out.println(amountOfLoan);
            if (balance >= amountPaid) {
                //The user pays for all the loan
                if (remainingOfLoan==0) return "You have already paid for that loan";
                double pay = Math.min(amountPaid, remainingOfLoan);
                double newRemaining = remainingOfLoan - pay;
                double newPaid = paidOfLoan + pay;
                double newBalance = balance - pay;
                currentUser.setBalance(balance - pay);
                String updateRemainingAndPaid = "UPDATE loans SET remaining = " + newRemaining + ",paid=" + newPaid + "WHERE loan_id=" + loanID + ";";
                String updateBalance = "UPDATE users SET balance = " + newBalance + "WHERE id=" + currentUser.getId();
                statement.executeUpdate(updateRemainingAndPaid);
                statement.executeUpdate(updateBalance);
                new Movement(pay, currentUser).payForLoan();
                return "Process DONE! Succesfully";
            } else {
                return "NOT enough Balance";
            }
        } else {
            return "NO Loan With This ID";
        }
    }
}
