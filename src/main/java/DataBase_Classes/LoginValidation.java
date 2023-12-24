package DataBase_Classes;


import com.example.demo10.HelloApplication;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginValidation {
    private DataBaseConnection Connection = new DataBaseConnection();
    private Connection connectionDB = Connection.getConnection();
    private String username;
    private String password;
    private String email;
    private String currency;
    private String gender;
    private String role;

    public LoginValidation(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User checkLogin() {
        String verifyLogin = "SELECT *  FROM users WHERE username='" + username + "' AND hashed_password = '" + password + "';";
        try {
            Statement statement = connectionDB.createStatement();
            ResultSet result = statement.executeQuery(verifyLogin);
            if (result.next()) {
                if (result.getString("username").equals(username) && result.getString("hashed_password").equals(password)) {
                    this.email = result.getString("email");
                    this.currency = result.getString("currency");
                    this.gender = result.getString("gender");
                    this.role = result.getString("role");
                    return new User(username, email, password, currency, gender, role);
                } else {
                    return null;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

}
