package com.example.darknotepad;

import com.example.darknotepad.persistence.PersistenceManager;
import com.example.darknotepad.persistence.SerSettings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {


    private static Stage pStage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 400);

        SerSettings settings =  PersistenceManager.getInstance().getObject();

        URL cssUrl;
        if (!settings.isDarkMode()) {
            cssUrl = getClass().getResource("/css/light.css");
        } else {
            cssUrl = getClass().getResource("/css/dark.css");
        }

        if (cssUrl == null) {
            System.out.println("Resource not found. Aborting application.");
            System.exit(-1);
        }

        scene.getStylesheets().add(cssUrl.toExternalForm());

        pStage = stage;
        stage.setTitle("DarkPad!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void setStageTitle(String title) {
        pStage.setTitle(title);
    }
}