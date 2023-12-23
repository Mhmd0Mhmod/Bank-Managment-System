module com.example.demo10 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.google.gson;
    requires org.controlsfx.controls;
    requires java.sql;

    opens com.example.demo10 to javafx.fxml;
    exports com.example.demo10;
    exports DataBase_Classes;
    opens DataBase_Classes to javafx.fxml;
}