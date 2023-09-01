module com.example.cs255coursework {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.cs255coursework to javafx.fxml;
    exports com.example.cs255coursework;
}