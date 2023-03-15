module com.example.darknotepad {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.darknotepad to javafx.fxml;
    exports com.example.darknotepad;
    exports com.example.darknotepad.controllers;
    opens com.example.darknotepad.controllers to javafx.fxml;
    exports com.example.darknotepad.persistence;
    opens com.example.darknotepad.persistence to javafx.fxml;
}