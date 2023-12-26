package DataBase_Classes;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.demo10.signUpController;

public class InsertUser {
    @FXML
    private Label uniqueEmailLabel;

    private DataBaseConnection Connection = new DataBaseConnection();
    private java.sql.Connection connectionDB = Connection.getConnection();
    private String username, email, password, currency, gender;
    private boolean checkUsername, checkEmail;

    public InsertUser(String username, String email, String password, String currency, String gender) throws SQLException {
        this.username = username;
        this.email = email;
        this.password = password;
        this.currency = currency;
        this.gender = gender;
        this.checkUsername = checkValdation("username");
        this.checkEmail = checkValdation("email");
    }

    public User checkValidation() throws SQLException {
        if (checkUsername && checkEmail) {
            Statement statement = connectionDB.createStatement();
            String insertUser = "INSERT INTO users (username, email, hashed_password, currency, gender,role)" +
                    "VALUES" +
                    "  ('" + username + "','" + email + "','" + password + "','" + currency + "','" + gender + "'," + "'user');";
            statement.executeUpdate(insertUser);
            return new User(username, email, password, currency, gender, "user");
        }
        return null;
    }

    public boolean checkValdation(String valueToCheck) throws SQLException {
        Statement statement = connectionDB.createStatement();
        String verify = null;
        if (valueToCheck == "email") {
            verify = "SELECT COUNT(*) AS count FROM users WHERE " + valueToCheck + "='" + email + "';";
        } else if (valueToCheck == "username") {
            verify = "SELECT COUNT(*) AS count FROM users WHERE " + valueToCheck + "='" + username + "';";
        }
        ResultSet result = statement.executeQuery(verify);
        result.next();
        if (result.getInt("count") == 1) {
            return false;
        } else {
            return true;
        }
    }

}
