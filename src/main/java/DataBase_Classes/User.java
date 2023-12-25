package DataBase_Classes;

public class User {
    private String username;
//    private User currentUser;
    private String password;
    private String email;
    private String currency;
    private String gender;
    private String role;
    private double balance;
    private int id;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public User(int id,String username, String email, String password, String currency, String gender, String role,double balance) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.currency = currency;
        this.gender = gender;
        this.role = role;
        this.balance = balance;
    }
    public User(String username, String email, String password, String currency, String gender, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.currency = currency;
        this.gender = gender;
        this.role = role;
        this.balance = 0;
    }
}
