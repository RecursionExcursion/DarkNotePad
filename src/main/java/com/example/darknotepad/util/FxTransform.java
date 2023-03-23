package com.example.darknotepad.util;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class FxTransform {

    public static Stage eventToStage(ActionEvent event) {
        MenuItem menuItem =  (MenuItem) event.getSource();
//        return (Stage) node.getScene().getWindow();
        return null;

    }
}
