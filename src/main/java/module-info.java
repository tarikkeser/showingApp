module com.example.endassignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;


    opens com.example.endassignment to javafx.fxml;
    exports com.example.endassignment;
    exports com.example.endassignment.Model;
    opens com.example.endassignment.Model to javafx.fxml;
    exports com.example.endassignment.Controller;
    opens com.example.endassignment.Controller to javafx.fxml;
}