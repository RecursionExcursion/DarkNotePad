package com.example.darknotepad;

import com.example.darknotepad.persistence.SerializationManager;
import com.example.darknotepad.util.SceneLoader;
import javafx.application.Application;
import javafx.stage.Stage;

public class HelloApplication extends Application {



    @Override
    public void start(Stage stage) {
        SceneLoader.overrideCurrentWindow("main-view.fxml", 640, 400, stage);
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() {
        SerializationManager.INSTANCE.save();
    }
}
