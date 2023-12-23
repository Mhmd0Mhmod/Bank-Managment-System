package DataBase_Classes;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsertUser {
    private DataBaseConnection Connection = new DataBaseConnection();
    private java.sql.Connection connectionDB = Connection.getConnection();
    private String username, email, password, currency, gender;

    public InsertUser(String username, String email, String password, String currency, String gender) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.currency = currency;
        this.gender = gender;
    }

    public boolean checkUsername() throws SQLException {
        Statement statement = connectionDB.createStatement();
        String verifyUsername = "SELECT COUNT(*) AS count FROM users WHERE username='" + username + "';";
        ResultSet result = statement.executeQuery(verifyUsername);
        while (result.next()) {
            if (result.getInt("count") == 1) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean checkEmail() throws SQLException {
        Statement statement = connectionDB.createStatement();
        String verifyEmail = "SELECT COUNT(*) AS count FROM users WHERE email='" + email + "';";
        ResultSet result = statement.executeQuery(verifyEmail);
        while (result.next()) {
            if (result.getInt("count") == 1) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    //    public boolean checkPrivacy (){
//
//    }
    public void checkValidation() throws SQLException {
        if (checkUsername() && checkEmail()) {
            Statement statement = connectionDB.createStatement();
            String insertUser = "INSERT INTO users (username, email, hashed_password, currency, gender)" +
                    "VALUES" +
                    "  ('" + username + "','" + email + "','" + password + "','" + currency + "','" + gender + "');";
            ResultSet result = statement.executeQuery(insertUser);
        }
    }

}
