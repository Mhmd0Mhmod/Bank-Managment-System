package DataBase_Classes;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginValidation {
    private DataBaseConnection Connection = new DataBaseConnection();
    private Connection connectionDB = Connection.getConnection();
    private String username;
    private String password;

    public LoginValidation(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public boolean checkLogin() {
        String verifyLogin = "SELECT COUNT(*) AS count FROM users WHERE username='" + username + "' AND hashed_password = '" + password + "';";
        try {
            Statement statement = connectionDB.createStatement();
            ResultSet result = statement.executeQuery(verifyLogin);
            while (result.next()) {
                if (result.getInt("count") == 1) {
                    return true;

                } else {
                    return false;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

}
