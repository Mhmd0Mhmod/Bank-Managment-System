module org.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.demo1 to javafx.fxml;
    exports org.example.demo1;
}