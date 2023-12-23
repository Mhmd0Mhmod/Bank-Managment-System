package DataBase_Classes;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {
    public Connection databaselink;
    public Connection getConnection(){
        String databaseName = "bankist";
        String databaseUser="root";
        String databasePassword="";
        String url="jdbc:mysql://localhost/" + databaseName;
        try{
        Class.forName("com.mysql.cj.jdbc.Driver");
        databaselink =DriverManager.getConnection(url,databaseUser,databasePassword);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return databaselink;
    }
}
