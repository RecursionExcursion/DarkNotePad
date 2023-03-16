package com.example.darknotepad.util;

import com.example.darknotepad.persistence.SerializableSettings;
import com.example.darknotepad.persistence.SerializationManager;
import javafx.scene.layout.BorderPane;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class FileChooser {

    private static final javafx.stage.FileChooser fileChooser;

    private static final SerializationManager SERIALIZATION_MANAGER = SerializationManager.INSTANCE;
    private static final SerializableSettings settings = SERIALIZATION_MANAGER.getSettings();

    static {
        fileChooser = new javafx.stage.FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new javafx.stage.FileChooser.ExtensionFilter("Text", "*.txt")
        );
    }

    public static List<String> open(BorderPane pane) {

        setRecentFilePath();

        fileChooser.setTitle("Open file");
        File file = fileChooser.showOpenDialog(pane.getScene().getWindow());

        if (file == null) {
            return null;
        }
        List<String> strings;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            strings = br.lines().toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        saveRecentFilePath(file);
        return strings;
    }

    public static void save(BorderPane pane, String text) {

        fileChooser.setTitle("Save file");

        setRecentFilePath();

        File file = fileChooser.showSaveDialog(pane.getScene().getWindow());

        if (file != null) {
            try (BufferedWriter bw = Files.newBufferedWriter(file.toPath(), StandardCharsets.UTF_8)) {
                bw.write(text);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            saveRecentFilePath(file);
        }
    }

    private static void setRecentFilePath() {
        String recentFileDir = settings.getRecentFileDir();

        if (recentFileDir != null) {
            File recentDir = new File(recentFileDir);
            fileChooser.setInitialDirectory(recentDir);
        }
    }

    private static void saveRecentFilePath(File file) {
        settings.setRecentFileDir(file.getParent());
    }
}
