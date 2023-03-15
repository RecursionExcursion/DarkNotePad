package com.example.darknotepad.controllers;

import com.example.darknotepad.HelloApplication;
import com.example.darknotepad.persistence.PersistenceManager;
import com.example.darknotepad.persistence.SerSettings;
import com.example.darknotepad.util.FileChooser;
import com.example.darknotepad.util.RedoHandler;
import com.example.darknotepad.util.UndoHandler;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.IndexRange;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    public BorderPane mainPane;

    public TextArea mainTextArea;

    public MenuBar menuBar;

    public MenuItem redoCmd;
    public MenuItem undoCmd;
    public MenuItem cutCmd;
    public MenuItem copyCmd;
    public MenuItem pasteCmd;
    public MenuItem deleteCmd;


    private final UndoHandler undoHandler = new UndoHandler();
    private final RedoHandler redoHandler = new RedoHandler();

    Clipboard clipboard = Clipboard.getSystemClipboard();

    private boolean darkMode = false;
    private boolean textWrap = false;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set Undo start point
        undoHandler.storeState(mainTextArea.getText());

        //Set Accelerators
        undoCmd.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        redoCmd.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
        cutCmd.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
        copyCmd.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        pasteCmd.setAccelerator(new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN));
        deleteCmd.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));


        //Filter key combos
        mainTextArea.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            //Disable windows default Undo
            if (e.getCode() == KeyCode.Z && e.isControlDown()) {
                onUndoClick();
                e.consume();

                //Disable windows default Redo
            } else if (e.getCode() == KeyCode.Y && e.isControlDown()) {
                onRedoClick();
                e.consume();

                //Disable windows default Copy
            } else if (e.getCode() == KeyCode.C && e.isControlDown()) {
                onCopyClick();
                e.consume();

                //Disable windows default Paste
            } else if (e.getCode() == KeyCode.V && e.isControlDown()) {
                onPasteClick();
                e.consume();

                //Disable windows default Paste
            } else if (e.getCode() == KeyCode.X && e.isControlDown()) {
                onCutClick();
                e.consume();

                //Disable windows default Delete
            } else if (e.getCode() == KeyCode.DELETE) {
                onDeleteClick();
                e.consume();
            }
        });
    }

    public void onKeyTyped(KeyEvent keyEvent) {

        char[] chars = keyEvent.getCharacter().toCharArray();
        char aChar = chars[0];
        boolean isValidChar = aChar >= 32 && aChar <= 255;
        if (isValidChar) {
            undoHandler.storeState(mainTextArea.getText());
            if (!redoHandler.isEmpty()) {
                redoHandler.clear();
            }
        }
    }

    public void openClick() {
        List<String> text = FileChooser.open(mainPane);
        if (text != null) {
            StringBuilder sb = new StringBuilder();
            text.forEach(line -> sb.append(line).append("\n"));
            mainTextArea.setText(sb.toString());
        }
    }

    public void saveClick() {
        FileChooser.save(mainPane, mainTextArea.getText());
    }

    public void colorSchemeClick() {
        URL lightCSSScheme = getClass().getResource("/css/light.css");
        URL darkCSSScheme = getClass().getResource("/css/dark.css");

        ObservableList<String> stylesheets = mainPane.getScene().getStylesheets();
        stylesheets.clear();

        SerSettings settings = PersistenceManager.getInstance().getSettings();


        if (darkMode) {
            assert lightCSSScheme != null;
            stylesheets.add(lightCSSScheme.toExternalForm());
            settings.setDarkMode(false);
        } else {
            assert darkCSSScheme != null;
            stylesheets.add(darkCSSScheme.toExternalForm());
            settings.setDarkMode(true);
        }

        PersistenceManager.getInstance().SaveSettings(settings);
        darkMode = !darkMode;
    }

    public void onDateTimeClick() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a MM/dd/yyyy");
        mainTextArea.appendText(formatter.format(LocalDateTime.now()));
    }

    public void onWordWrapClick() {
        mainTextArea.setWrapText(!textWrap);
        textWrap = !textWrap;
    }

    public void onUndoClick() {

        String lastState = undoHandler.getLastState();
        String currentText = mainTextArea.getText();
        if (lastState.equals(currentText)) {
            lastState = undoHandler.getLastState();
        }
        redoHandler.storeState(currentText);
        mainTextArea.clear();
        mainTextArea.appendText(lastState);
    }

    public void onRedoClick() {
        if (!redoHandler.isEmpty()) {
            undoHandler.storeState(mainTextArea.getText());
            mainTextArea.clear();
            mainTextArea.appendText(redoHandler.getLastState());
        }
    }

    public void onCopyClick() {
        String selectedText = mainTextArea.getSelectedText();
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(selectedText);
        clipboard.setContent(clipboardContent);
    }

    public void onCutClick() {
        onCopyClick();
        mainTextArea.deleteText(mainTextArea.getSelection());
        undoHandler.storeState(mainTextArea.getText());
    }

    public void onPasteClick() {
        Object content = clipboard.getContent(DataFormat.PLAIN_TEXT);
        String text = (String) content;
        mainTextArea.insertText(mainTextArea.getCaretPosition(), text);
        undoHandler.storeState(mainTextArea.getText());
    }

    public void onDeleteClick() {
        IndexRange selection = mainTextArea.getSelection();
        int length = selection.getLength();
        if (length == 0) {
            if (selection.getStart() + 1 <= mainTextArea.getLength()) {
                mainTextArea.deleteText(selection.getStart(), selection.getStart() + 1);
            }
        } else {
            mainTextArea.deleteText(selection);
        }
        undoHandler.storeState(mainTextArea.getText());
    }

    public void onNewWindowClick() {

        try{
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
            Stage stage = new Stage();
            stage.setTitle("DarkPad!");
            Scene scene = new Scene(fxmlLoader.load(), 640, 400);
            URL cssUrl = getClass().getResource("/css/light.css");

            if (cssUrl == null) {
                System.out.println("Resource not found. Aborting application.");
                System.exit(-1);
            }

            scene.getStylesheets().add(cssUrl.toExternalForm());

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}