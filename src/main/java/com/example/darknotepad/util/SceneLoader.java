package com.example.darknotepad.util;

import com.example.darknotepad.HelloApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneLoader {

    public static void createNewWindow(String viewString, int width, int height){
        try {
            Scene scene = getScene(viewString, width, height);
            Stage stage = new Stage();
            setStage(scene,stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void overrideCurrentWindow(String viewString, int width, int height, Stage stage) {
        try {
            Scene scene = getScene(viewString, width, height);
            setStage(scene,stage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Scene getScene(String viewString, int width, int height) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(viewString));
        Scene scene = new Scene(fxmlLoader.load(), width, height);

        //Optional Css Styling
        scene.getStylesheets().add(CssManager.INSTANCE.getCssUrl());

        return scene;
    }

    private static void setStage(Scene scene, Stage stage) {
        stage.setTitle("DarkPad!");
        stage.setScene(scene);
        stage.show();
    }
}
